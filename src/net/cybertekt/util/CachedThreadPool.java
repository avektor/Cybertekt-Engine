package net.cybertekt.util;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dal0119
 */
public class CachedThreadPool<T extends Callable> {

    /**
     * SLF4J class logger for debugging.
     */
    public static final Logger log = LoggerFactory.getLogger(CachedThreadPool.class);

    private int coreThreadCount;

    private int maxThreadCount;

    private int spawnRatio;

    private long timeoutTime;

    private TimeUnit timeoutUnit;

    private final LinkedBlockingQueue<T> taskQue;

    private final ThreadFactory threadFactory;

    private final AtomicInteger totalThreads;

    private final AtomicInteger idleThreads;

    private final AtomicInteger taskCount;

    private boolean shutdown;

    public CachedThreadPool() {
        this(0, 25, 2, Integer.MAX_VALUE, 1L, TimeUnit.SECONDS, new DefaultThreadFactory());
    }

    public CachedThreadPool(final int coreThreads, final int maxThreads, final int spawnRatio, final int maxQueSize) {
        this(coreThreads, maxThreads, spawnRatio, maxQueSize, new DefaultThreadFactory());
    }

    public CachedThreadPool(final int coreThreads, final int maxThreads, final int spawnRatio, final int maxQueSize, final ThreadFactory threadFactory) {
        this(coreThreads, maxThreads, spawnRatio, maxQueSize, 1L, TimeUnit.SECONDS, threadFactory);
    }

    public CachedThreadPool(final int coreThreads, final int maxThreads, final int rate, final int maxQueSize, final long timeout, final TimeUnit timeUnit, final ThreadFactory factory) {
        coreThreadCount = coreThreads;
        maxThreadCount = maxThreads;
        spawnRatio = rate;
        timeoutTime = timeout;
        timeoutUnit = timeUnit;
        taskQue = new LinkedBlockingQueue<>(maxQueSize);
        threadFactory = factory;
        totalThreads = new AtomicInteger();
        idleThreads = new AtomicInteger();
        taskCount = new AtomicInteger();
        initCoreThreads();
    }

    public final void submit(final T... tasks) {
        for (final T task : tasks) {
            taskQue.add(task);
        }
        taskCount.addAndGet(tasks.length);
        while (taskCount.get() >= spawnRatio && totalThreads.get() < maxThreadCount) {
            Thread t = threadFactory.newThread(new Worker(false));
            threadCreated(t);
            t.start();
            taskCount.set(taskCount.get() - spawnRatio);
        }
    }

    protected void taskCompleted(final T task) {
    }

    protected void taskFailed(final T task, final Exception e) {
    }

    protected void threadCreated(final Thread t) {

    }

    protected void threadDestroyed(final Thread t, final Exception reason) {

    }

    public final int totalThreads() {
        return totalThreads.get();
    }

    public final int activeThreads() {
        return totalThreads() - idleThreads();
    }

    public final int idleThreads() {
        return idleThreads.get();
    }

    private void initCoreThreads() {
        for (int i = 0; i < coreThreadCount; i++) {
            Thread t = threadFactory.newThread(new Worker(true));
            t.start();
        }
        totalThreads.set(coreThreadCount);
    }

    private final class Worker implements Runnable {

        private final boolean core;

        private int completed;

        public Worker(final boolean core) {
            this.core = core;
        }

        @Override
        public void run() {
            while (!shutdown) {
                try {
                    boolean wasEmpty = taskQue.isEmpty();
                    if (wasEmpty) {
                        //onThreadSleep(Thread.currentThread());
                        idleThreads.incrementAndGet();
                    }
                    T task = core ? taskQue.take() : taskQue.poll(timeoutTime, timeoutUnit);
                    if (task == null) {
                        idleThreads.decrementAndGet();
                        throw new InterruptedException("Thread Timed Out.");
                    }
                    if (wasEmpty) {
                        //onThreadWake(Thread.currentThread());
                        idleThreads.decrementAndGet();
                    }
                    try {
                        task.call();
                        completed++;
                        taskCompleted(task);
                    } catch (final Exception e) {
                        completed++;
                        taskFailed(task, e);
                    }
                } catch (final Exception e) {
                    totalThreads.decrementAndGet();

                    if (completed == 0) {
                        log.info("Thread {} destroyed without completing any tasks.");
                    }
                    
                    threadDestroyed(Thread.currentThread(), e);
                    return;
                }
            }
            if (completed == 0) {
                log.info("Thread {} destroyed without completing any tasks.");
            }
            totalThreads.decrementAndGet();
            threadDestroyed(Thread.currentThread(), null);
        }
    }

    private static final class DefaultThreadFactory implements ThreadFactory {

        @Override
        public final Thread newThread(final Runnable task) {
            return new Thread(task);
        }
    }
}
