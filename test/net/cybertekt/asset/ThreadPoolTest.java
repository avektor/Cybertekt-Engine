package net.cybertekt.asset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.cybertekt.asset.AssetLoader.AssetTask;
import net.cybertekt.asset.image.ImageLoader;
import net.cybertekt.util.CachedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dal0119
 */
public class ThreadPoolTest {

    public static final Logger log = LoggerFactory.getLogger(ThreadPoolTest.class);

    public final Map<Thread, Long> activeTracker = new HashMap<>();

    public final Map<Thread, Long> idleTimeTracker = new HashMap<>();
    
    private int threadsCreated = 0;

    private final Map<Thread, Integer> tasksCompleted = new HashMap<>();
    
    public static void main(final String[] args) {
        ThreadPoolTest app = new ThreadPoolTest();
        app.start();
    }

    public final void start() {
        AssetKey grayscale = AssetKey.getKey("Textures/PNG/Grayscale.png");
        AssetKey IDX8 = AssetKey.getKey("Textures/PNG/IDX8.png");
        AssetKey LUM8 = AssetKey.getKey("Textures/PNG/LUM8.png");
        AssetKey LUMA8 = AssetKey.getKey("Textures/PNG/LUMA8.png");
        AssetKey RGB08 = AssetKey.getKey("Textures/PNG/RGB08.png");
        AssetKey RGB16 = AssetKey.getKey("Textures/PNG/RGB16.png");
        AssetKey RGBA08 = AssetKey.getKey("Textures/PNG/RGBA08.png");
        AssetKey RGBA082 = AssetKey.getKey("Textures/PNG/RGBA082.png");
        AssetKey RGBA16 = AssetKey.getKey("Textures/PNG/RGBA16.png");

        Random rng = new Random();
        ImageLoader loader = new ImageLoader();
        ThreadPool threadPool = new ThreadPool();
        AssetKey[] keys = new AssetKey[]{grayscale, IDX8, LUM8, LUMA8, RGB08, RGB16, RGBA08, RGBA082, RGBA16};

        int iterations = 10;
        AssetTask[] tasks = new AssetTask[iterations];
        long time = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            AssetKey k = keys[rng.nextInt(keys.length)];
            tasks[i] = loader.newTask(keys[rng.nextInt(keys.length)], AssetManager.stream(k));
            //AssetKey k = keys[rng.nextInt(keys.length)];
            threadPool.submit(loader.newTask(k, AssetManager.stream(k)));
        }
        threadPool.submit(tasks);
        while (threadPool.activeThreads() > 0) {
            log.info("Active Threads [{}]", threadPool.activeThreads());
        }
        float elapsed = (System.nanoTime() - time) / 1000000L;
        log.info("Loaded 500 images in {}ms using {} threads.", elapsed, threadsCreated);
        /*
        threadPool.submit(loader.newTask(grayscale, null));
        threadPool.submit(loader.newTask(IDX8, AssetManager.stream(IDX8)));
        threadPool.submit(loader.newTask(LUM8, AssetManager.stream(LUM8)));
        threadPool.submit(loader.newTask(LUMA8, AssetManager.stream(LUMA8)));
        threadPool.submit(loader.newTask(RGB08, AssetManager.stream(RGB08)));

        log.info("Thread Stats (Living/Active/Idle) {}/{}/{}", threadPool.livingThreads(), threadPool.activeThreads(), threadPool.idleThreads());

        threadPool.submit(loader.newTask(RGB16, AssetManager.stream(RGB16)));
        threadPool.submit(loader.newTask(RGBA08, AssetManager.stream(RGBA08)));
        threadPool.submit(loader.newTask(RGBA082, AssetManager.stream(RGBA082)));
        threadPool.submit(loader.newTask(RGBA16, AssetManager.stream(RGBA16)));

        log.info("Thread Stats (Living/Active/Idle) {}/{}/{}", threadPool.livingThreads(), threadPool.activeThreads(), threadPool.idleThreads());
        */
    }

    private class ThreadPool extends CachedThreadPool<AssetTask> {

        @Override
        protected void taskCompleted(final AssetTask task) {
            //log.info("Image [{}] has been loaded successfully!", task.key);
        }

        @Override
        protected void taskFailed(final AssetTask task, final Exception e) {
            //log.info("Task [{}] Failed: {} - {}", task.key, e.getClass().getSimpleName(), e.getLocalizedMessage());
        }

        @Override
        protected void threadCreated(final Thread t) {
            //log.info("Thread [{}] Created!", t.getName());
            threadsCreated++;
        }

        @Override
        protected void threadDestroyed(final Thread t, final Exception reason) {
            if (reason == null) {
                //log.info("Thread [{}] Destroyed!", t.getName());
            } else {
                //log.info("Thread [{}] Destroyed! - {}: {}", t.getName(), reason.getClass().getSimpleName(), reason.getLocalizedMessage());
            }
        }
    }
}
