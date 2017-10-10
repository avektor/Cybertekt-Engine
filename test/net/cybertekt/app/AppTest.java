package net.cybertekt.app;

import net.cybertekt.asset.AssetCache;

/**
 *
 * @author dal0119
 */
public class AppTest extends Application {

    private AssetCache cache;
    
    public AppTest() {
        super("Application Unit Test");
    }

    public static void main(final String[] args) {
        AppTest app = new AppTest();
        
        /* Set Application Settings */
        
        
        app.initialize();
    }

    @Override
    public void init() {
        /* Load Required Assets */
        //cache.get()
        //cache.get(Assets.Textures.Grayscale);
        //cache.get(Assets.Textures.IDX8);
        //cache.get(Assets.Textures.LUM8);
        //cache.get(Assets.Textures.RGB16);
        //cache.get(Images.Grayscale);
    }

    @Override
    public void update(final float tpf) {

    }
}
