package net.cybertekt.display;

import net.cybertekt.app.Application;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputAction;
import net.cybertekt.render.OGLRenderer;
import net.cybertekt.display.input.InputListener;
import net.cybertekt.display.input.InputSequence;
import org.joml.Vector2f;

/**
 * Input Unit Test - (C) Cybertekt Software

 Demonstrates the capabilities of the Cybertekt Engine {@link Input} System.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Vektor
 */
public class InputTest extends Application implements InputListener {

    public InputTest() {
        super("Input Unit Test");
    }
    
    /**
     * Application Entry Point - Main Thread.
     *
     * @param args JVM command line arguments .
     */
    public static void main(final String[] args) {
        /* Create and initialize the application */
        InputTest app = new InputTest();
        app.initialize();
    }

    /**
     * Application Initialization.
     */
    @Override
    public final void init() {
        /* Define Display Settings */
        DisplaySettings settings = new WindowSettings("Input Test - Cybertekt Software", new Vector2f(800, 600));
        settings.setVerticalSync(true);
        
        /* Create The Display Window */
        display = Display.create(settings);
        display.setRenderer(new OGLRenderer());
        
        /* Create and register an action mapping in order to exit the application when the escape key is pressed */
        //display.addInputMapping("exit", new InputMapping(Input.Key.Escape).when(Input.State.Pressed));
        display.addInputMapping("exit", new InputAction(Input.State.Pressed, Input.Key.Escape));
        
        display.addInputMapping("[Copy]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.C));
        display.addInputMapping("[Paste]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.V));
        display.addInputMapping("[Undo]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.Z));
        display.addInputMapping("[Redo]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.Y));     
        
        display.addInputMapping("[Walking]", new InputSequence(Input.State.Held, Input.Key.W));
        display.addInputMapping("[Running]", new InputAction(Input.State.Held, Input.Mod.Shift, Input.Key.W));
        
        display.addInputMapping("[Ctrl + Alt + C]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Mod.Alt, Input.Key.C));
        
        display.addInputMapping("info", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.I));
        
        display.addInputListener(this);
    }

    /**
     * Application Update Loop.
     *
     * @param tpf the time per frame.
     */
    @Override
    public final void update(final float tpf) {
        /* Not Implemented */
    }
    
    @Override
    public final void onInput(final Display display, final String mapping, final float tpf) {
        switch (mapping) {
            case "exit":
                stop();
                break;
            case "info":
                log.info("{} - {} - {}", display.getSize(), display.getResolution(), display.getAspectRatio());
                break;
            default:
                log.info("{}", mapping);
                break;
        }
    }
}
