package net.cybertekt.display;

import net.cybertekt.app.Application;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputAction;
import net.cybertekt.math.Vec2f;
import net.cybertekt.render.OGLRenderer;
import net.cybertekt.display.input.InputListener;
import net.cybertekt.display.input.InputMapping;
import net.cybertekt.display.input.InputSequence;

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
        //display.addInputMapping("exit", new InputMapping(Input.Key.Escape).when(Input.State.Pressed));
        display.addInputMapping("exit", new InputAction(Input.State.Pressed, Input.Key.Escape));
        
        display.addInputMapping("[Copy]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.C));
        display.addInputMapping("[Paste]", new InputSequence(Input.State.Pressed, Input.Mod.Ctrl, Input.Key.V));
        
        display.addInputMapping("[Walking]", new InputSequence(Input.State.Held, Input.Key.W));
        display.addInputMapping("[Running]", new InputAction(Input.State.Held, Input.Mod.Shift, Input.Key.W));
        
        //display.addInputMapping("[Copy]", new InputSequence(Input.Key.CtrlLeft, Input.Key.C));
        
        //display.addInputMapping("[X] Pressed", new InputMapping(Input.Key.X, Input.State.Pressed, Input.Mode.Trigger));
        //display.addInputMapping("[X] Released", new InputMapping(Input.Key.X, Input.State.Released, Input.Mode.Trigger));
        //display.addInputMapping("[X]", new InputMapping(true, Input.Key.X).when(Input.State.Pressed));
        //display.addInputMapping("[Ctrl + Shift + X]", new InputMapping(Input.Mod.Ctrl, Input.Mod.Shift, Input.Key.X).when(Input.State.Held));
        
        //display.addInputMapping("[Ctrl + C]", new InputMapping(Input.Mod.Ctrl, Input.Key.C).when(Input.State.Pressed));
        //display.addInputMapping("[Ctrl + Alt + C", new InputMapping(Input.Mod.Ctrl, Input.Mod.Alt, Input.Key.C).when(Input.State.Pressed));
        
        //display.addInputMapping("[B]", new InputMapping(Input.Key.B).when(Input.State.Pressed, Input.State.Held, Input.State.Released));
        
        //display.addInputMapping("[Scroll Up]", new InputMapping(Input.Mouse.ScrollUp).when(Input.State.Pressed));
        //display.addInputMapping("[Scroll Down]", new InputMapping(Input.Mouse.ScrollDown).when(Input.State.Pressed));
        
        //display.addInputMapping("[Shift + Scroll Up]", new InputMapping(Input.Mod.Shift, Input.Mouse.ScrollUp).when(Input.State.Pressed));
        //display.addInputMapping("[Shift + Scroll Down]", new InputMapping(Input.Mod.Shift, Input.Mouse.ScrollDown).when(Input.State.Pressed));
        
        //display.addInputMapping("[Left Alt]", new InputMapping(Input.Key.AltLeft).when(Input.State.Pressed));
        
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
        if (mapping.equals("exit")) {
            stop();
        } else {
            log.info("{}", mapping);
        }
    }
}
