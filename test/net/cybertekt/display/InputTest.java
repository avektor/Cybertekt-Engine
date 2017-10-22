package net.cybertekt.display;

import net.cybertekt.app.Application;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputAction;
import net.cybertekt.math.Vec2f;
import net.cybertekt.render.OGLRenderer;
import net.cybertekt.display.input.InputListener;
import net.cybertekt.display.input.InputMapping;

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
        /* Create a display */
        display = Display.create(new WindowSettings("Input Test Display Window", new Vec2f(800, 600)));
        display.setRenderer(new OGLRenderer());
        
        /* Create and register an action mapping in order to exit the application when the escape key is pressed */
        display.addInputMapping("exit", new InputAction(Input.Key.Escape));
        
        //display.addInputMapping("[X] Pressed", new InputMapping(Input.Key.X, Input.State.Pressed, Input.Mode.Trigger));
        //display.addInputMapping("[X] Released", new InputMapping(Input.Key.X, Input.State.Released, Input.Mode.Trigger));
        
        display.addInputMapping("[A]", new InputAction(Input.Key.A).when(Input.State.Pressed, Input.State.Released));
        display.addInputMapping("[Ctrl + Shift + A]", new InputAction(Input.Mod.Ctrl, Input.Mod.Shift, Input.Key.X).when(Input.State.Held, Input.State.Released));
        
        
        display.addInputMapping("[Walking]", new InputAction(Input.Key.W).when(Input.State.Held));
        display.addInputMapping("[Running]", new InputAction(Input.Mod.Shift, Input.Key.W).when(Input.State.Held));
        
        display.addInputMapping("[Scroll]", new InputAction(Input.Mouse.Scroll));
        
        //display.addInputMapping("[Shift + A] Pressed (Analog)", new InputMapping(new Input[] { Input.Mod.Shift, Input.Key.A }, Input.State.Pressed));

            //display.addInputMapping("[Shift + X] Pressed", new InputMapping(new Input[] { Input.Mod.Shift, Input.Key.X }, Input.State.Pressed));
        //display.addInputMapping("[Shift + X] Released", new InputMapping(new Input[] { Input.Mod.Shift, Input.Key.X }, Input.State.Released));
        
        //display.addInputMapping("[Shift + Left Click] Pressed", new InputMapping(new Input[] { Input.Mod.Shift, Input.Mouse.Left }, Input.State.Pressed));
        
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
    public final void onInput(final Display display, final String mapping, final Input.State event, final float tpf) {
        log.info("{} - {}", mapping, event);
        if (mapping.equals("exit")) {
            stop();
        }
    }
}
