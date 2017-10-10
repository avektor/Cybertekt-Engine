package net.cybertekt.asset;

import java.util.Random;
import net.cybertekt.asset.image.Image;
import net.cybertekt.asset.image.ImageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dal0119
 */
public class PNGInlineSpeedTest {

    public static final Logger log = LoggerFactory.getLogger(PNGInlineSpeedTest.class);

    public static final String[] paths = {"Textures/PNG/RGBA082.png", "Textures/PNG/LUM8.png", "Textures/PNG/LUMA8.png", "Textures/PNG/RGB08.png", "Textures/PNG/RGBA16.png", "Textures/PNG/IDX8.png"};

    public static void main(final String[] args) {
        PNGInlineSpeedTest app = new PNGInlineSpeedTest();
        app.init();
    }

    public void init() {
        AssetManager.registerLoader(new ImageLoader(), AssetType.getType("PNG"));
        AssetManager.registerLoader(ImageLoader.class, AssetType.getType("PNG"));

        long time = System.nanoTime();
        //ImageLoader l = new ImageLoader();
        //l.loadInline(AssetKey.getKey("Textures/PNG/Grayscale.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/Grayscale.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/IDX8.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/IDX8.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/LUM8.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/LUM8.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/LUMA8.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/LUMA8.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/RGB08.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/RGB08.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/RGB16.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/RGB16.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/RGBA08.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/RGBA08.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/RGBA082.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/RGBA082.png")));
        //l.loadInline(AssetKey.getKey("Textures/PNG/RGBA16.png"), AssetManager.stream(AssetKey.getKey("Textures/PNG/RGBA16.png")));
        
        
        
        Image i = AssetManager.get(Image.class, AssetKey.getKey("Textures/PNG/Grayscale.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/IDX8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/LUM8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/LUMA8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGB08.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGB16.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA08.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA082.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA16.png"));
        
        long after = System.nanoTime();
        log.info("PNG Loaded in {}ns - {}ms", after - time, (after - time) / 1000000);
        //long nanoTime = System.nanoTime();
        //Image pngFallback = AssetManager.get("Textures/PNG/Grayscale.png", Image.class);
        //AssetManager.setFallback(pngFallback, pngFallback.getKey().getType());
        //Image img = AssetManager.get("Textures/PNG/Bad0.png", Image.class);
        //Image img2 = AssetManager.get("Textures/PNG/Grayscale.png", Image.class);
        //Image img3 = AssetManager.get("Textures/PNG/RGBA16.png", Image.class);
        //Image img4 = AssetManager.get("Textures/PNG/LUM8.png", Image.class);
        //float time = (System.nanoTime() - nanoTime) / 1000000f;
        //log.info("Loaded [{},{}] image in [{}ms] [{}ms]", img.getWidth(), img.getHeight(), time, elapsed);
        //log.info("Loaded [{}] asset(s) in [{}ms]", AssetManager.getAssetCount(), time);
        //log.info("Pending tasks: [{}]", AssetManager.getTaskCount());
        //log.info("Image Sizes: [{}x{}], [{}x{}], [{}x{}], [{}x{}]", img.getWidth(), img.getHeight(), img2.getWidth(), img2.getHeight(), img3.getWidth(), img3.getHeight(), img4.getWidth(), img4.getHeight());
        //log.info("PNG assets loaded: [{}]", AssetManager.getAssetCount(AssetType.getType("png")));
        //AssetManager.terminate(); Need to re-evaluate this!
    }

    public void start() {
        //log.info("Starting Application - Asset Count is {} - Total Available Processors is {}", AssetManager.getLoaded(), Runtime.getRuntime().availableProcessors());
        long time = System.nanoTime();

        //AssetManager.load(AssetKey.getKey("Textures/PNG/Grayscale.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/IDX8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/LUM8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/LUMA8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGB08.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGB16.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA08.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA082.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA16.png"));

        //AssetManager.abort(true);
        //while (AssetManager.isLoading()) {
        //log.info("Now Loading ... {}/{} - {}%", AssetManager.getLoaded(), AssetManager.getSubmitted(), AssetManager.getProgress());
        //log.info("Loading Assets ... {}%", AssetManager.getProgress());
        //}
        //log.info("Loaded {} of {} assets in {}ms using {} thread(s) - {} Cached", AssetManager.getLoaded(), AssetManager.getSubmitted(), (float) (System.nanoTime() - time) / 1000000L, AssetManager.getPoolSize(), AssetManager.getCacheSize());
        //log.info("Loading Done - {}/{} - {}%", AssetManager.getLoaded(), AssetManager.getSubmitted(), AssetManager.getProgress());
         //AssetManager.reset();
         //AssetManager.load(AssetKey.getKey("Textures/PNG/Grayscale.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/IDX8.png"));
        //AssetManager.load(AssetKey.getKey("Textures/PNG/LUM8.png"));
        //while (AssetManager.isLoading()) {
            //log.info("Now Loading ... {}/{} - {}%", AssetManager.getLoaded(), AssetManager.getRequested(), String.format("%.1f", AssetManager.getProgress() * 100f));
        //}
        
        //log.info("Loading Done - {}/{} - {}% ({} Threads)", AssetManager.getLoaded(), AssetManager.getRequested(), AssetManager.getProgress(), AssetManager.getPoolSize());

        //Thread t1 = new Thread(new WorkerThread(AssetKey.getKey(paths[0])));
        //Thread t2 = new Thread(new WorkerThread(AssetKey.getKey(paths[1])));
        //Thread t3 = new Thread(new WorkerThread(AssetKey.getKey(paths[2])));
        //Thread t4 = new Thread(new WorkerThread(AssetKey.getKey(paths[3])));
        //Thread t5 = new Thread(new WorkerThread(AssetKey.getKey(paths[4])));
        //t1.start();
        //t2.start();
        //t3.start();
        //t4.start();
        //t5.start();
        //while (t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive() || t5.isAlive()) {
        //log.info("Loading [{}%]", AssetManager.getLoadingStatus());
        //}
        //log.info("Loaded {} unique assets in {}ms using {} threads", AssetManager.getAssetCount(), (float) (System.nanoTime() - time) / 1000000L, Thread.activeCount());
        //log.info("Loaded {} of {} assets in {}ms using {} thread(s) - {} Cached - (Active Threads: {})", AssetManager.getLoaded(), AssetManager.getRequested(), (float) (System.nanoTime() - time) / 1000000L, AssetManager.getPoolSize(), AssetManager.getCacheSize(), Thread.activeCount());
    }

    private String getRandomImagePath() {
        return paths[new Random().nextInt(5)];
    }

    public final class WorkerThread implements Runnable {

        private final AssetKey key;

        public WorkerThread(final AssetKey keyToUse) {
            key = keyToUse;
        }

        @Override
        public void run() {
            //Will this retrieve a cached asset? Need to test this.
            //long time = System.nanoTime();
            //log.info("Thread [{}] now loading [{}] - Asset Count is {}",Thread.currentThread().getName(), key.getPath(), AssetManager.getAssetCount());
            //Image img = AssetManager.get(key, Image.class);

            //AssetManager.load(AssetKey.getKey("Textures/PNG/RGB16.png"));
            //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA08.png"));
            //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA082.png"));
            //AssetManager.load(AssetKey.getKey("Textures/PNG/RGBA16.png"));

            //log.info("Thread [{}] loaded [{}] in {}ms - Asset Count is {}", Thread.currentThread().getName(), img.getKey().getPath(), (System.nanoTime() - time) / 10000, AssetManager.getLoaded());
        }
    }
}
