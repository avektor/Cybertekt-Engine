 package net.cybertekt.app;

import net.cybertekt.asset.AssetManager;
import net.cybertekt.asset.AssetType;
import net.cybertekt.asset.image.ImageLoader;
import net.cybertekt.asset.shader.ShaderLoader;
import net.cybertekt.display.Display;
import net.cybertekt.display.DisplayListener;
import net.cybertekt.display.DisplaySettings;
import net.cybertekt.display.WindowSettings;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputAction;
import net.cybertekt.display.input.InputListener;
import net.cybertekt.render.OGLRenderer;

/**
 * Basic Application - (C) Cybertekt Software
 *
 * @author Andrew Vektor
 * @version 1.0.0
 * @since 1.0.0
 */
public class BasicApplication extends Application implements DisplayListener, InputListener {

    protected DisplaySettings settings;

    public BasicApplication() {
        this("Basic Application");
    }

    public BasicApplication(final String name) {
        this(name, new WindowSettings(name));
    }

    public BasicApplication(final DisplaySettings settings) {
        this("Basic Application", settings);
    }

    public BasicApplication(final String name, final DisplaySettings settings) {
        super(name);
        this.settings = settings;
    }

    @Override
    public final void initialize() {
        /* Register Basic Asset Loaders */
        AssetManager.registerLoader(ImageLoader.class, AssetType.getType("PNG"), AssetType.getType("JPG"));
        AssetManager.registerLoader(ShaderLoader.class, AssetType.getType("VERT"), AssetType.getType("FRAG"));

        /* Initialize Display */
        display = Display.create(settings).setRenderer(new OGLRenderer());
        display.addDisplayListener(this);

        /* Initialize Basic Input */
        //display.addInputMapping("exit", new InputMapping(Input.Key.Escape).when(Input.State.Pressed));
        display.addInputListener(this);

        super.initialize();
    }

    @Override
    public void init() {
        /* No Implementation */
    }

    @Override
    public void update(final float tpf) {
        /* No Implementation */
    }

    @Override
    public void onClose(final Display display) {
        log.info("Display [{}] destroyed", display.getId());
        if (display == this.display) {
            stop();
        } else {
            Display.destroy(display);
        }
    }

    @Override
    public void onResize(final Display display, final int width, final int height) {
        //log.info("Display [{}] resized to ({} x {})", display.getId(), width, height);
    }

    @Override
    public void onMove(final Display display, final int xPos, final int yPos) {
        //log.info("Displayed [{}] moved to ({}, {})", display.getId(), xPos, yPos);
    }

    @Override
    public void onFocus(final Display display, final boolean focused) {
        if (focused) {
            log.info("Display [{}] gained input focus.", display.getId());
        } else {
            log.info("Display [{}] lost input focus.", display.getId());
        }
    }

    @Override
    public void onIconify(final Display display, final boolean iconified) {
        if (display.equals(this.display)) {
            if (iconified) {
                log.info("Display [{}] has been minimized.", display.getId());
            } else {
                log.info("Display [{}] has been restored.", display.getId());
            }
        }
    }

    @Override
    public void onMouseEnter(final Display display, final boolean entered) {
        if (entered) {
            log.info("Mouse entered display [{}].", display.getId());
        } else {
            log.info("Mouse exited display [{}].", display.getId());
        }
    }
    
    @Override
    public void onMouseScroll(final Display display, final int amount) {
        log.info("Mouse Scrolled [{}] on display [{}].", amount, display.getId());
    }
    
    @Override
    public void onMouseMove(final Display display, final int xPos, final int yPos) {
        //log.info("Mouse cursor moved to [{}, {}] on display [{}].", xPos, yPos, display.getId());
    }

    @Override
    public void onInput(final Display display, final String mapping, final float tpf) {
        if (mapping.equals("exit")) {
            log.info("Terminating Application");
            stop();
        }
    }

}
