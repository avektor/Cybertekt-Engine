package net.cybertekt.app;

import net.cybertekt.display.Display;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application - (C) Cybertekt Software
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public abstract class Application {

    /**
     * Internal SLF4J Application logger for debugging.
     */
    protected static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * Stores the name of the application.
     */
    private final String name;

    
    protected Display display;

    /**
     * The maximum number of times to update the application state each second.
     * Any value of zero or less will force the application to update at an
     * unbounded rate. Defaults to 60 updates per second.
     */
    private float ups = 60;

    /**
     * The maximum number of frames to render each second. Any value of zero or
     * less will force the application to render at an unbounded rate. Defaults
     * to 60 frames per second.
     */
    private float fps = 60;

    /**
     * Indicates if the application loop should terminate.
     */
    private boolean stop = false;

    static {
        /* Uncomment to enable LWJGL debugging */
        //System.setProperty("org.lwjgl.util.Debug", "true");
        
        log.info("Application Started - Using LWJGL Version {}", org.lwjgl.Version.getVersion());
    }

    public Application(final String name) {
        this.name = name;
    }

    public void initialize() {
        long beforeInit = System.nanoTime();
        init();
        log.info("Initialization Completed in {}ms", (System.nanoTime() - beforeInit) / 1000000);
        loop();
        
    }

    /**
     * Application Main Loop.
     *
     * [Placeholder for time-step explanation]
     *
     *
     * The loop exits and the {@link Display} system is terminated when the
     * {@link Application#stop()}
     */
    public void loop() {
        double time = glfwGetTime();
        float tpf;
        
        while (!stop) {
            
            /* Calculate time since last frame */
            tpf = (float) (glfwGetTime() - time);

            /* Set the frame start time */
            time = (float) glfwGetTime();

            /* Poll */ 
            Display.poll(tpf);

            /* Update */
            update(tpf);
            
            /* Render */
            Display.render();
        }

        /* Terminate the display system */
        Display.terminate();
        log.info("Application Terminated Successfully!");
    }

    /**
     * Forces the application to close.
     */
    public void stop() {
        stop = true;
    }
    
    /**
     * Application Initialization.
     */
    public abstract void init();

    /**
     * Application Update.
     *
     * @param tpf the amount of time elapsed since the last loop.
     */
    public abstract void update(final float tpf);

}
