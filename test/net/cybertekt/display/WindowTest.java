package net.cybertekt.display;

import net.cybertekt.app.Application;
import net.cybertekt.app.BasicApplication;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputMapping;
import net.cybertekt.render.OGLRenderer;

/**
 * Display System Unit Test - (C) Cybertekt Software
 *
 * Demonstrates the capabilities of the display system.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class WindowTest extends BasicApplication {

    public WindowTest() {
        super("Window Unit Test");
    }

    /**
     * Application entry point. Creates and initializes the
     * {@link Application application} super class.
     *
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        WindowTest app = new WindowTest();
        app.initialize();
    }
    
    @Override
    public final void init() {
        display.addInputMapping("print", new InputMapping(Input.Key.P).when(Input.State.Pressed));
        
        
        Display D2 = Display.create(settings).setRenderer(new OGLRenderer());
        D2.addDisplayListener(this);
    }

    @Override
    public final void update(final float tpf) {
    }
}
