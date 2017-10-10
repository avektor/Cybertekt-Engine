package net.cybertekt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dynamic Thread Pool - (C) Cybertekt Software
 *
 * Custom
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class DynamicThreadPool<T extends Callable> {

    /**
     * SLF4J class logger for debugging.
     */
    public static final Logger log = LoggerFactory.getLogger(DynamicThreadPool.class);

    private int core;

    private int maxThreads;

    private int tasksPerThread;

    private volatile boolean shutdown;

    private volatile boolean paused;

    private final LinkedBlockingQueue<T> taskQue;

    private final ThreadFactory threadFactory;

    private final List<Thread> threads;

    private final AtomicInteger totalThreads;

    private final AtomicInteger idleThreads;

    public DynamicThreadPool() {
        this(0, Runtime.getRuntime().availableProcessors(), 2, new DefaultThreadFactory());
    }

    public DynamicThreadPool(final int coreThreads, final int maxThreads, final int tasksPerThread, final ThreadFactory factory) {
        this.core = coreThreads;
        this.maxThreads = maxThreads;
        this.tasksPerThread = tasksPerThread;
        this.threadFactory = factory;
        this.taskQue = new LinkedBlockingQueue();
        threads = new ArrayList<>();
        totalThreads = new AtomicInteger(core);
        idleThreads = new AtomicInteger();
        initCoreThreads();
    }

    public final void submit(final T task) {
        synchronized (taskQue) {
            taskQue.add(task);
            taskQue.notify();
            log.info("Task Submitted. Que Size is {}", taskQue.size());
            while (!taskQue.isEmpty() && (taskQue.size() - idleThreads.get()) % tasksPerThread == 0) {
                Thread t = threadFactory.newThread(new Worker(totalThreads.getAndIncrement(), true, taskQue.poll()));
                threads.add(t);
                t.start();
            }
        }
    }

    protected void onTaskComplete(final int id, final T task) {
        log.info("Worked thread {} completed task {}", id, task);
    }

    protected void onTaskFail(final T task, final Exception e) {
        log.warn("Task [{}] failed: {}", task, e.getLocalizedMessage());
    }

    public final int activeThreads() {
        return totalThreads.get();
    }

    public final void pause() {
        paused = !paused;
        synchronized (taskQue) {
            taskQue.notify();
        }
    }

    private void initCoreThreads() {
        for (int i = 0; i < core; i++) {
            Thread t = threadFactory.newThread(new Worker(i, false));
            threads.add(t);
            t.start();
        }
    }

    private void updatePool() {
        while (!taskQue.isEmpty() && (taskQue.size() - idleThreads.get()) % tasksPerThread == 0) {
            Thread t = threadFactory.newThread(new Worker(totalThreads.getAndIncrement(), true, taskQue.poll()));
            threads.add(t);
            t.start();
        }
    }

    private final class Worker implements Runnable {

        private final int id;

        private final boolean expires;

        private T currentTask;

        public Worker(final int id, final boolean expires) {
            this(id, expires, null);
        }

        public Worker(final int id, final boolean expires, final T firstTask) {
            this.id = id;
            this.expires = expires;
            this.currentTask = firstTask;
        }

        @Override
        public final void run() {
            while (!shutdown) {
                synchronized (taskQue) {
                    if (!paused) {
                        /* Poll for task if worker does not already have one */
                        if (currentTask == null) {
                        }

                        if (currentTask != null) {
                            try {
                                currentTask.call();
                                onTaskComplete(id, currentTask);
                            } catch (final Exception e) {
                                onTaskFail(currentTask, e);
                            }
                            currentTask = null;
                        } else if (!expires) {
                            try {
                                log.info("Worker Thread {} is now waiting for work", id);
                                idleThreads.incrementAndGet();
                                taskQue.wait();
                                idleThreads.decrementAndGet();
                            } catch (final InterruptedException e) {
                                /* TODO - HANDLE EXCEPTION */
                                return;
                            }
                        } else {
                            /* Allow thread to close gracefully */
                            totalThreads.decrementAndGet();
                            taskQue.notify();
                            log.info("Worker Thread {} destroyed!", id);
                            return;
                        }
                    } else {
                        log.info("Worker thread {} paused!", id);
                        idleThreads.incrementAndGet();
                        while (paused) {
                            try {
                                taskQue.wait();
                            } catch (final InterruptedException e) {

                            }
                        }
                        log.info("Worker thread {} unpaused.", id);
                        idleThreads.decrementAndGet();
                    }
                }
            }
            totalThreads.decrementAndGet();
            log.info("Worker Thread {} has ended.", id);
        }
    }

    private static final class DefaultThreadFactory implements ThreadFactory {

        @Override
        public final Thread newThread(final Runnable task) {
            Thread t = new Thread(task);
            return t;
        }
    }

}
