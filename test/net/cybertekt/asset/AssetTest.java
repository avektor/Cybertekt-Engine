 package net.cybertekt.asset;

import java.util.Random;
import net.cybertekt.asset.image.ImageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andrew Vektor
 */
public class AssetTest {

    public static final Logger log = LoggerFactory.getLogger(AssetTest.class);

    public static final String[] paths = {"Textures/PNG/RGBA082.png", "Textures/PNG/LUM8.png", "Textures/PNG/LUMA8.png", "Textures/PNG/RGB08.png", "Textures/PNG/RGBA16.png", "Textures/PNG/IDX8.png"};

    public static void main(final String[] args) {
        AssetTest app = new AssetTest();
        app.init();
        app.start();
    }

    public void init() {
        //AssetManager.registerLoader(new ImageLoader(), AssetType.getType("PNG"));
        AssetManager.registerLoader(ImageLoader.class, AssetType.getType("PNG"));

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
        log.info("Application Started - Asset Count is {} - Total Available Processors is {}", AssetManager.getLoaded(), Runtime.getRuntime().availableProcessors());
        long time = System.nanoTime();
        
        // Remove the fallback asset and a runtime exception will occur if you attempt to retrieve a non-existent or invalid PNG asset such as "Textures/PNG/Bad.png" or "Textures/PNG/DoesNotExist.png".
        AssetManager.setFallback(AssetManager.get("Textures/PNG/Grayscale.png"), AssetType.getType("PNG"));
        
        AssetManager.load("Textures/PNG/Grayscale.png");
        AssetManager.load("Textures/PNG/IDX8.png");
        AssetManager.load("Textures/PNG/LUM8.png");
        AssetManager.load("Textures/PNG/LUMA8.png");
        AssetManager.load("Textures/PNG/RGB08.png");
        AssetManager.load("Textures/PNG/RGB16.png");
        AssetManager.load("Textures/PNG/RGBA08.png");
        AssetManager.load("Textures/PNG/RGBA082.png");
        AssetManager.load("Textures/PNG/RGBA16.png");
        AssetManager.load("Textures/PNG/Bad.png");
        AssetManager.load("Textures/PNG/DoesNotExist.png");
        AssetManager.load("Textures/PNG/DoesNotExist2.png");
        
        //No Exception Thrown Here If A Fallback Asset Has Been Registered For PNG Asset Types.
        AssetManager.get("Textures/PNG/Bad.png");
        //AssetManager.get("Textures/PNG/DoesNotExist.png");
        //AssetManager.get("Textures/PNG/DoesNotExist2.png");
        
        long loops = 0;
        log.info("Now Loading ... {}/{}/{} - {}%", AssetManager.getLoaded(), AssetManager.getFailed(), AssetManager.getRequested(), String.format("%.0f", AssetManager.getProgress() * 100f));
        while (AssetManager.isLoading()) {
            log.info("Now Loading ... {}/{}/{} - {}%", AssetManager.getLoaded(), AssetManager.getFailed(), AssetManager.getRequested(), String.format("%.0f", AssetManager.getProgress() * 100f));
            loops++;
        }
        log.info("Loading Done - {}/{}/{} - {}% ({} Threads - {} Loops)", AssetManager.getLoaded(), AssetManager.getFailed(), AssetManager.getRequested(), String.format("%.0f", AssetManager.getProgress() * 100f), AssetManager.getPoolSize(), loops);
        log.info("Loaded {} of {} assets in {}ms using {} thread(s) - {} Cached - (Active Threads: {})", AssetManager.getLoaded(), AssetManager.getRequested(), Math.round((float) (System.nanoTime() - time) / 1000000L), AssetManager.getPoolSize(), AssetManager.getCacheSize(), Thread.activeCount());
        
        //AssetManager.cachedAssets.keySet().forEach((a) -> {
        //    log.info("Cached Asset Key - {}", a.getName());
        //});
    }

    private String getRandomImagePath() {
        return paths[new Random().nextInt(5)];
    }
}
