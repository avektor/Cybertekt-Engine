package net.cybertekt.asset;

import net.cybertekt.app.BasicApplication;
import net.cybertekt.asset.image.Image;
import net.cybertekt.asset.shader.OGLShader;
import net.cybertekt.display.Display;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputAction;
import net.cybertekt.exception.OGLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image Test - (C) Cybertekt Software
 *
 * Demonstrates and tests the capabilities of the engine to load
 * {@link Image image assets} with supported formats.
 *
 * @author Vektor
 */
public class ImageTest extends BasicApplication {

    /**
     * Static SLF4J class logger for debugging.
     */
    public static final Logger log = LoggerFactory.getLogger(ImageTest.class);

    private Image[] PNGs;

    public static void main(final String[] args) {
        ImageTest app = new ImageTest();
        app.initialize();
    }

    public ImageTest() {
        super("Image Test");
    }

    @Override
    public void init() {
        display.addInputMapping("print", new InputAction(Input.Key.P).when(Input.State.Pressed));

        //AssetManager.reset();
        long time = System.nanoTime();

        /* STILL BROKEN IF YOU UNCOMMENT - THERE IS A HOLE SOMEWHERE. */
        AssetManager.load("Textures/PNG/Grayscale.png");
        AssetManager.load("Textures/PNG/IDX8.png");
        AssetManager.load("Textures/PNG/LUM8.png");
        AssetManager.load("Textures/PNG/RGB08.png");
        AssetManager.load("Textures/PNG/Bad.png");

        AssetManager.load("Shaders/Solid/solid.vert");
        AssetManager.load("Shaders/Solid/solid.frag");

        PNGs = AssetManager.get(Image.class,
                "Textures/PNG/Grayscale.png", "Textures/PNG/IDX8.png");

        try {
            AssetManager.get(OGLShader.class, "Shaders/Solid/solid.vert").compile();
        } catch (final OGLException e) {
            log.error("Unable to compile shader: {}", e.getLocalizedMessage());
        }
        long after = System.nanoTime();
        //AssetManager.getLoaded();
        //log.info("Loaded {} image(s) in {}ms - Total Assets Loaded: {}", PNGs.length, (after - time) / 1000000, AssetManager.getLoaded());
    }

    @Override
    public void onInput(final Display display, final String mapping, final Input.State event, final float tpf) {
        super.onInput(display, mapping, event, tpf);
        if (mapping.equals("print")) {
            log.info("\n\tAsset Manager Stats\n\tLoaded: {}\n\tRequested: {}\n\tFailed: {}\n\tCache Size: {}\n\tImages Cached: {}",
                    AssetManager.getLoaded(), AssetManager.getRequested(), AssetManager.getFailed(), AssetManager.getCacheSize(),
                    AssetManager.getCacheSize(AssetType.getType("PNG")));
        }
    }
}
