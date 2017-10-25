package net.cybertekt.display;

import net.cybertekt.display.input.Input;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import net.cybertekt.display.input.InputMapping;
import net.cybertekt.exception.InitializationException;
import net.cybertekt.math.Vec2f;
import net.cybertekt.render.OGLRenderer;
import net.cybertekt.render.Renderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_AUTO_ICONIFY;
import static org.lwjgl.glfw.GLFW.GLFW_CONNECTED;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FLOATING;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.GLFW_ICONIFIED;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVersionString;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwIconifyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwMaximizeWindow;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwRestoreWindow;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMonitorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import net.cybertekt.display.input.InputListener;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPhysicalSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.system.MemoryUtil;

/**
 * Display - (C) Cybertekt Software
 *
 * <p>
 * Defines a single windowed or fullscreen GLFW display. Display objects cannot
 * be instantiated directly and instead must be created using the static utility
 * methods provided by this class. Each display has its own rendering context
 * controlled by the {@link Renderer renderer} which can be defined using the
 * {@link Display#setRenderer(net.cybertekt.render.Renderer) method}. Displays
 * <b>must only be created within the main application thread</b>. Attempting to
 * create a display outside of the main application thread will cause unexpected
 * results.
 * </p>
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Display {

    /**
     * Internal SLF4J class logger for debugging.
     */
    public static final Logger LOG = LoggerFactory.getLogger(Display.class);

    /**
     * Callback for receiving errors from GLFW.
     */
    private static final GLFWErrorCallback ERROR_CALLBACK;

    /**
     * Callback for receiving device connection events from GLFW.
     */
    private static final GLFWMonitorCallback DEVICE_CALLBACK;

    /**
     * Stores each active {@link Display display} created by the application
     * using the unique identifier assigned to the display by GLFW.
     */
    private static final Map<Long, Display> DISPLAYS = new ConcurrentHashMap<>();

    /**
     * Stores the active display {@link DisplayDevice devices} by their GLFW
     * device ID.
     */
    private static final Map<Long, DisplayDevice> DEVICES = new ConcurrentHashMap<>();

    /**
     * Static initialization block that initializes GLFW and set the error and
     * monitor callbacks.
     */
    static {
        /* Initialize GLFW */
        if (!glfwInit()) {
            throw new InitializationException("Unable to initialize GLFW.");
        } else {
            LOG.info("GLFW {} Initialized!", glfwGetVersionString());
        }

        /* Set GLFW Error Callback */
        glfwSetErrorCallback(ERROR_CALLBACK = new ErrorCallback());

        /* Set GLFW Device Callback */
        glfwSetMonitorCallback(DEVICE_CALLBACK = new DeviceCallback());
    }

    /**
     * Creates an initializes a new {@link Display display} using the
     * {@link DisplaySettings settings} provided. <b>Do not attempt to create a
     * {@link Display display} from outside of the main application thread.</b>
     *
     * @param settings the display settings to use for creating and initializing
     * the display.
     * @return the newly constructed display.
     */
    public static final Display create(final DisplaySettings settings) {
        if (settings instanceof ScreenSettings) {
            return create((ScreenSettings) settings);
        } else if (settings instanceof WindowSettings) {
            return create((WindowSettings) settings);
        } else {
            return create(new WindowSettings());
        }
    }

    /**
     * Creates and initializes a new fullscreen {@link Display display} using
     * the {@link ScreenSettings settings} provided. <b>Do not attempt to create
     * a {@link Display display} from outside of the main application
     * thread.</b>
     *
     * @param settings the fullscreen display settings to use for creating and
     * initializing the display.
     * @return the new fullscreen display.
     */
    public static final Display create(final ScreenSettings settings) {

        /* Not Yet Implemented */
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, settings.isVisible() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_FLOATING, settings.isFloating() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_FOCUSED, settings.isFocused() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_AUTO_ICONIFY, settings.isAutoIconify() ? GL_TRUE : GL_FALSE);
        return null;
    }

    /**
     * Creates and initializes a new windowed {@link Display display} using the
     * {@link WindowSettings settings} provided. <b>Do not attempt to create a
     * {@link Display display} from outside of the main application thread.</b>
     *
     * @param settings the window initialization settings.
     * @return the {@link Display display} created with the provided window
     * initialization settings.
     */
    public static final Display create(WindowSettings settings) {
        /* Set GLFW Window Hints Using The Provided Settings */
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, settings.isVisible() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, settings.isResizable() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_DECORATED, settings.isDecorated() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_FLOATING, settings.isFloating() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_FOCUSED, settings.isFocused() ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, settings.isMaximized() ? GL_TRUE : GL_FALSE);

        /* Create the Display */
        Display display = new Display(glfwCreateWindow(settings.getWidth(), settings.getHeight(), settings.getTitle(), NULL, NULL));

        if (display.ID != 0) {

            /* Set initial window position */
            glfwSetWindowPos(display.ID, settings.getX(), settings.getY());
            
            /* Set display window title (if not null) */
            if (settings.getTitle() != null) {
                glfwSetWindowTitle(display.ID, settings.getTitle());
            }
            
            /* Attach Display Callbacks */
            glfwSetWindowCloseCallback(display.ID, CALLBACK_CLOSE);
            glfwSetWindowPosCallback(display.ID, CALLBACK_POSITION);
            glfwSetWindowIconifyCallback(display.ID, CALLBACK_ICONIFY);
            glfwSetWindowFocusCallback(display.ID, CALLBACK_FOCUS);
            glfwSetFramebufferSizeCallback(display.ID, CALLBACK_RESIZE);

            /* Attach Input Callbacks */
            glfwSetKeyCallback(display.ID, CALLBACK_KEY);
            glfwSetMouseButtonCallback(display.ID, CALLBACK_MOUSE_BUTTON);
            glfwSetScrollCallback(display.ID, CALLBACK_MOUSE_SCROLL);
            glfwSetCursorPosCallback(display.ID, CALLBACK_CURSOR);
            glfwSetCursorEnterCallback(display.ID, CALLBACK_CURSOR_ENTER);
            glfwSetCharCallback(display.ID, CALLBACK_KEY_CHAR);
            glfwMakeContextCurrent(display.ID);
            GL.createCapabilities();

            display.vsync = settings.isVerticalSync();

            /* Add and return the newly created display */
            DISPLAYS.put(display.ID, display);

            return display;
        } else {
            throw new InitializationException("Could not create new window: " + settings.getTitle());
        }
    }

    /**
     * Destroys the target {@link Display display}. <b>Do not attempt to destroy
     * a {@link Display display} from outside of the main application
     * thread.</b>
     *
     * @param toDestroy the {@link Display display} to destroy.
     */
    public static final void destroy(final Display toDestroy) {
        if (DISPLAYS.containsKey(toDestroy.getId())) {
            if (toDestroy.hasRenderer()) {
                toDestroy.renderer.destroy();
            }
            DISPLAYS.remove(toDestroy.getId());
            glfwDestroyWindow(toDestroy.getId());
        }
    }

    /**
     * Input Polling.
     *
     * @param tpf
     */
    public static final void poll(final float tpf) {
        glfwPollEvents();

        DISPLAYS.values().forEach((display) -> {
            display.onPoll(tpf);
        });
    }

    public static final void render() {
        DISPLAYS.values().stream().forEach((display) -> {
            render(display);
        });
    }

    public static final void render(final Display display) {
        /* Skip execution if renderer is null or display is paused */
        if (display.renderer != null && !display.paused) {

            /* Make this display the current context */
            glfwMakeContextCurrent(display.ID);

            if (!display.initialized) {
                /* Enable/disable vertical sync for target display.*/
                glfwSwapInterval(display.vsync ? 1 : 0);

                /* Set the initialization flag */
                display.initialized = true;
            }

            /* Render the next frame */
            display.renderer.render();

            /* Swap the frame buffers */
            glfwSwapBuffers(display.ID);
        }
    }

    /**
     * Terminates GLFW by destroying all active displays and closing all event
     * callbacks. This should <b>only be called when the application is
     * closing!</b> Calling this method will destroy ALL displays.
     */
    public static final void terminate() {
        /* Destroy any remaining Displays */
        DISPLAYS.values().stream().forEach((d) -> {
            destroy(d);
        });

        /* Close Callbacks */
        CALLBACK_CLOSE.close();
        CALLBACK_POSITION.close();
        CALLBACK_RESIZE.close();
        CALLBACK_ICONIFY.close();
        CALLBACK_FOCUS.close();
        CALLBACK_KEY.close();
        CALLBACK_KEY_CHAR.close();
        CALLBACK_MOUSE_SCROLL.close();
        CALLBACK_MOUSE_BUTTON.close();
        CALLBACK_CURSOR.close();
        CALLBACK_CURSOR_ENTER.close();

        /* Terminate GLFW */
        glfwTerminate();

        /* Close device and error callbacks */
        DEVICE_CALLBACK.close();
        ERROR_CALLBACK.close();
    }

    /**
     * Returns the application's primary {@link DisplayDevice display device}.
     *
     * @return the primary {@link DisplayDevice display device}.
     */
    public static final DisplayDevice getPrimaryDisplayDevice() {
        return DEVICES.get(glfwGetPrimaryMonitor());
    }

    /**
     * Returns the {@link DisplayDevice device} associated with the provided
     * device ID, or null if no device exists with the ID specified.
     *
     * @param id the GLFW display ID of the {@link DisplayDevice device} to
     * retrieve.
     * @return the {@link DisplayDevice device} associated with the provided ID,
     * or false if no device exists with the specified ID.
     */
    public static final DisplayDevice getDisplayDevice(final long id) {
        return DEVICES.get(id);
    }

    /**
     * Returns the collection of {@link DisplayDevice display devices} detected
     * when the users system.
     *
     * @return the collection of {@link DisplayDevice devices}.
     */
    public static final Collection<DisplayDevice> getDisplayDevices() {
        return DEVICES.values();
    }

    /**
     * Returns the collection of active {@link Display display} objects.
     *
     * @return the collection of active {@link Display displays} objects.
     */
    public static final Collection<Display> getActiveDisplays() {
        return DISPLAYS.values();
    }

    /**
     * Indicates if the internal collection of active {@link Display displays}
     * is empty.
     *
     * @return true if the internal collection of active
     * {@link Display displays} is empty, false otherwise.
     */
    public static final boolean hasActiveDisplays() {
        return !DISPLAYS.isEmpty();
    }

    /**
     * GLFW generated pointer assigned to the display upon construction.
     */
    private final long ID;

    /**
     *
     */
    private final List<Input> INPUT = new ArrayList();

    private final IntBuffer WIDTH = MemoryUtil.memAllocInt(1);

    private final IntBuffer HEIGHT = MemoryUtil.memAllocInt(1);

    /**
     * Stores all {@link DisplayListener display listeners} attached to the
     * display.
     */
    private final List<DisplayListener> DISPLAY_LISTENERS = new ArrayList<>();

    /**
     * Stores all {@link InputListener input listeners} attached to the display.
     */
    private final List<InputListener> INPUT_LISTENERS = new ArrayList<>();

    /**
     * Stores the {@link InputMapping input mappings} attached to the display.
     */
    private final Map<String, InputMapping> INPUT_MAPPINGS = new HashMap<>();

    /**
     * The {@link Renderer renderer} responsible for drawing the display
     * content.
     */
    private Renderer renderer = new OGLRenderer();

    /**
     * Indicates if rendering when this display should be paused.
     */
    private boolean paused;

    /**
     * Indicates if rendering when this display has been initialized.
     */
    private boolean initialized;

    /**
     * Indicates if vertical sync is enabled for this display.
     */
    private boolean vsync;

    /**
     * Constructs a new display with a unique identifier assigned by GLFW.
     * Displays cannot be constructed directly by users, they can only be
     * created using the static {@link Display#create()} method.
     *
     * @param displayId the GLFW display identifier.
     */
    private Display(final long displayId) {
        ID = displayId;
    }

    public final void move(final int xPos, final int yPos) {
        glfwSetWindowPos(ID, xPos, yPos);
    }

    public final void resize(final int width, final int height) {
        glfwSetWindowSize(ID, width, height);
    }

    public final void rename(final String newName) {
        glfwSetWindowTitle(ID, newName);
    }

    public final void hide(final boolean hide) {
        if (hide) {
            glfwShowWindow(ID);
        } else {
            glfwHideWindow(ID);
        }
    }

    /**
     * Iconifies (minimizes) the display.
     */
    public final void iconify() {
        glfwIconifyWindow(ID);
    }

    /**
     * Restores the size and position of an iconified display.
     */
    public final void restore() {
        glfwRestoreWindow(ID);
    }

    /**
     * Maximizes the display so that it takes up all available screen space on a
     * device.
     */
    public final void maximize() {
        glfwMaximizeWindow(ID);
    }

    /**
     * Pauses the display renderer.
     */
    public final void pause() {
        if (!paused) {
            paused = !paused;
        }
    }

    /**
     * Resumes the display renderer.
     */
    public final void resume() {
        if (paused) {
            paused = !paused;
        }
    }

    /**
     * Updates the state of the four input modifiers (ALT, CTRL, SHIFT, SUPER)
     * within the {@link #INPUT_MAP input map} and then sends the updated map to
     * all {@link InputMapping input mappings} attached to the display for
     * polling. Any mapping that is activated by the current state of the input
     * map are sent to the {@link InputListener input listeners} attached to the
     * display.
     *
     * @param tpf
     */
    private void onPoll(final float tpf) {
        /**
         * Trigger any input mappings attached to the display.
         */
        INPUT_MAPPINGS.entrySet().stream().filter((e) -> (e.getValue().poll(INPUT))).forEach((e) -> {
            INPUT_LISTENERS.stream().forEach((l) -> {
                l.onInput(this, e.getKey(), tpf);
            });
        });

        //INPUT_MAP.put(Input.Mouse.ScrollUp, Input.State.Released);
        //INPUT_MAP.put(Input.Mouse.ScrollDown, Input.State.Released);
    }

    /**
     * Receives display movement events and forwards them to all
     * {@link DisplayListener display listeners} attached to the display.
     *
     * @param xPos the new x-axis position of the display.
     * @param yPos the new y-axis position of the display.
     */
    private void onMove(final int xPos, final int yPos) {
        if (!DISPLAY_LISTENERS.isEmpty()) {
            DISPLAY_LISTENERS.stream().forEach((listener) -> {
                listener.onMove(this, xPos, yPos);
            });
        }
    }

    /**
     * Receives display resize events from GLFW and forwards them to the
     * {@link DisplayListener#onResize(Display, int, int) onResize()} method of
     * all {@link DisplayListener listeners} attached to the display that
     * triggered the event.
     *
     * @param width the new width of the display in pixels.
     * @param height the new height of the display in pixels.
     */
    private void onResize(final int width, final int height) {
        /**
         * Update the OpenGL View Port.
         */
        glViewport(0, 0, width, height);

        /**
         * Makes a render call if the display is not iconified. This is done to
         * prevent artifacting during resize operations.
         */
        if (!isIconified()) {
            Display.render(this);
        }

        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onResize(this, width, height);
        });
    }

    /**
     * Receives display focus events from GLFW and forwards them to the
     * {@link DisplayListener#onFocus(Display, boolean) onFocus()} method of all
     * {@link DisplayListener listeners} attached to the display that triggered
     * the event.
     *
     * @param focus true if the display has gained input focus or false if the
     * display has lost input focus.
     */
    protected final void onFocus(final boolean focus) {
        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onFocus(this, focus);
        });
    }

    /**
     * Receives display iconify events from GLFW and forwards them to the
     * {@link DisplayListener#onIconify(Display, boolean) onIconify()} method of
     * all {@link DisplayListener listeners} attached to the display that
     * triggered the event.
     *
     * @param iconify true if the display was iconified (minimized) or false if
     * the display was restored from an iconified state.
     */
    private void onIconify(final boolean iconify) {
        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onIconify(this, iconify);
        });
    }

    /**
     * Receives display close events from GLFW and forwards them to the
     * {@link DisplayListener#onClose(Display) onClose()} method of all
     * {@link DisplayListener listeners} attached to the display that triggered
     * the event.
     */
    private void onClose() {
        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onClose(this);
        });
    }

    /**
     * Receives cursor entrance/exit events from GLFW and forwards them to the
     * {@link DisplayListener#onMouseEnter(Display, boolean) onMouseEnter()}
     * method of all {@link DisplayListener listeners} attached to the display
     * that triggered the event.
     *
     * @param entered true if the mouse cursor has entered the display or false
     * if the cursor has exited the display.
     */
    private void onMouseEnter(final boolean entered) {
        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onMouseEnter(this, entered);
        });
    }

    /**
     * Receives mouse scroll events from GLFW and forwards them to the
     * {@link DisplayListener#onMouseScroll(Display, int) onMouseScroll()}
     * method of all {@link DisplayListener listeners} attached to the display
     * that triggered the event.
     *
     * @param amount the scroll intensity. Positive numbers indicate an upwards
     * scroll while negative numbers indicate a downwards scroll.
     */
    private void onMouseScroll(final int amount) {
        /* Update Input Map */
        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onMouseScroll(this, amount);
        });
    }

    /**
     * Receives mouse movement events from GLFW and forwards them to the
     * {@link DisplayListener#onMouseMove(Display, int, int) onMouseMove()}
     * method of all {@link DisplayListener listeners} attached to the display
     * that triggered the event.
     *
     * @param xPos the new x-axis position of the cursor within the display.
     * @param yPos the new y-axis position of the cursor within the display.
     */
    private void onMouseMove(final int xPos, final int yPos) {
        DISPLAY_LISTENERS.stream().forEach((listener) -> {
            listener.onMouseMove(this, xPos, yPos);
        });
    }

    /**
     * Updates the {@link #INPUT input map}.
     *
     * @param input
     * @param event
     */
    public final void onInput(final Input input, final Input.State event, final int mods) {

        Input.Mod m = getModifier(input);
        if (m != null) {
            if (!INPUT.contains(m)) {
                if (event == Input.State.Pressed) {
                    INPUT.add(m);
                }
            } else {
                if (event == Input.State.Released) {
                    INPUT.remove(m);
                }
            }
        } else {
            if (!INPUT.contains(input)) {
                if (event == Input.State.Pressed) {
                    INPUT.add(input);
                }
            } else {
                if (event == Input.State.Released) {
                    INPUT.remove(input);
                }
            }
        }
    }

    private void onChar(final char key) {
        //log.info("Character Pressed: " + key);
    }

    private Input.Mod getModifier(final Input input) {
        if (input == Input.Key.AltLeft || input == Input.Key.AltRight) {
            return Input.Mod.Alt;
        }
        if (input == Input.Key.CtrlLeft || input == Input.Key.CtrlRight) {
            return Input.Mod.Ctrl;
        }
        if (input == Input.Key.ShiftLeft || input == Input.Key.ShiftRight) {
            return Input.Mod.Shift;
        }
        if (input == Input.Key.SuperLeft || input == Input.Key.SuperRight) {
            return Input.Mod.Super;
        }
        return null;
    }

    /**
     * Sets the current {@link Renderer renderer} to use for rendering the
     * content of this display and returns a handle to this display for the
     * purpose of call chaining.
     *
     * @param toSet the {@link Renderer renderer} to use for rendering the
     * content of this display, or null to disable rendering for this display.
     * @return a handle to this display for the purpose of call chaining.
     */
    public final Display setRenderer(final Renderer toSet) {
        renderer = toSet;
        return this;
    }

    /**
     * Returns true if the display has a renderer set.
     *
     * @return true if the display has a renderer, false otherwise.
     */
    public final boolean hasRenderer() {
        return renderer != null;
    }

    /**
     * Returns the unique identifier assigned to the display by GLFW during
     * construction.
     *
     * @return the unique identifier of the display.
     */
    public final long getId() {
        return ID;
    }

    /**
     * Returns the current size of the display.
     *
     * @return
     */
    public final Vec2f getSize() {
        glfwGetWindowSize(ID, WIDTH, HEIGHT);
        return new Vec2f(WIDTH.get(0), HEIGHT.get(0));
    }

    public final Vec2f getResolution() {
        glfwGetFramebufferSize(ID, WIDTH, HEIGHT);
        return new Vec2f(WIDTH.get(0), HEIGHT.get(0));
    }

    /**
     * Indicates if this display is currently visible.
     *
     * @return true if this display is visible, or false if it is hidden.
     */
    public final boolean isVisible() {
        return glfwGetWindowAttrib(ID, GLFW_VISIBLE) == GLFW_TRUE;
    }

    /**
     * Indicates if this display is decorated.
     *
     * @return true if this display is decorated, false otherwise.
     */
    public final boolean isDecorated() {
        return glfwGetWindowAttrib(ID, GLFW_DECORATED) == GLFW_TRUE;
    }

    /**
     * Indicates if this display is currently iconified (minimized).
     *
     * @return true if this display is iconified, false otherwise.
     */
    public final boolean isIconified() {
        return glfwGetWindowAttrib(ID, GLFW_ICONIFIED) == GLFW_TRUE;
    }

    /**
     * Indicates if this display is currently maximized.
     *
     * @return true if this display is maximized, false otherwise.
     */
    public final boolean isMaximized() {
        return glfwGetWindowAttrib(ID, GLFW_MAXIMIZED) == GLFW_TRUE;
    }

    /**
     * Indicates if this display currently has glfwMap focus.
     *
     * @return true if this display has glfwMap focus, false otherwise.
     */
    public final boolean isFocused() {
        return glfwGetWindowAttrib(ID, GLFW_FOCUSED) == GLFW_TRUE;
    }

    /**
     * Indicates if the display is resizable.
     *
     * @return true if this display is resizable, false otherwise.
     */
    public final boolean isResizable() {
        return glfwGetWindowAttrib(ID, GLFW_RESIZABLE) == GLFW_TRUE;
    }

    /**
     * Returns the list of all {@link DisplayListener listeners} registered with
     * this display.
     *
     * @return the list of {@link DisplayListener listeners} registered with
     * this display.
     */
    public final List<DisplayListener> getDisplayListeners() {
        return DISPLAY_LISTENERS;
    }

    /**
     * Registers a {@link DisplayListener listener} to receive events from this
     * display.
     *
     * @param toAdd the {@link DisplayListener listener} to register with this
     * display.
     */
    public final void addDisplayListener(final DisplayListener toAdd) {
        if (!DISPLAY_LISTENERS.contains(toAdd)) {
            DISPLAY_LISTENERS.add(toAdd);
        }
    }

    /**
     * Removes a previously registered {@link DisplayListener listener} from
     * this display. This method does nothing if the provided
     * {@link DisplayListener listener} is not already registered to this
     * display.
     *
     * @param toRemove the previously registered
     * {@link DisplayListener listener} to remove.
     */
    public final void removeDisplayListener(final DisplayListener toRemove) {
        if (DISPLAY_LISTENERS.contains(toRemove)) {
            DISPLAY_LISTENERS.remove(toRemove);
        }
    }

    /**
     * Removes all registered {@link DisplayListeners listeners} from this
     * display.
     */
    public final void clearDisplayListeners() {
        DISPLAY_LISTENERS.clear();
    }

    /**
     * Adds an {@link InputListener input listener} which will receive input
     * events from this display.
     *
     * @param toAdd the {@link InputListener input listener} to add to this
     * display.
     */
    public final void addInputListener(final InputListener toAdd) {
        INPUT_LISTENERS.add(toAdd);
    }

    /**
     * Attaches an {@link InputListener input listener} to this display which
     * will receive input events based on the {@link InputMapping mappings} Adds
     * an {@link InputListener input listener} which will receive input events
     * from this display.
     *
     * @param toRemove the {@link InputListener input listener} to add to this
     * display.
     */
    public final void removeInputListener(final InputListener toRemove) {
        INPUT_LISTENERS.remove(toRemove);
    }

    public final void clearInputListeners() {
        INPUT_LISTENERS.clear();
    }

    public final void addInputMapping(final String name, final InputMapping mapping) {
        INPUT_MAPPINGS.put(name, mapping);
    }

    public final void removeInputMapping(final String name) {
        INPUT_MAPPINGS.remove(name);
    }

    public final void clearInputMappings() {
        INPUT_MAPPINGS.clear();
    }

    /**
     * Receives and logs GLFW errors.
     */
    private static final class ErrorCallback extends GLFWErrorCallback {

        /**
         * Logs the error name and description.
         *
         * @param error the GLFW error code.
         * @param description the GLFW error description pointer.
         */
        @Override
        public final void invoke(final int error, final long description) {
            throw new InitializationException("GLFW Exception - " + getError(error) + GLFWErrorCallback.getDescription(description));
        }

        /**
         * Returns the human-readable name of a GLFW error code.
         *
         * @param errCode the GLFW error code for which to retrieve the name.
         * @return the human-readable name of the provided GLFW error code.
         */
        private String getError(final int errCode) {
            for (final Field f : GLFW.class.getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    try {
                        if (f.getInt(null) == errCode) {
                            return f.getName();
                        }
                    } catch (final IllegalAccessException e) {
                        /* Reflection Exception Ignored */
                    }
                }
            }
            return "Unknown Error";
        }
    }

    /**
     * Handles adding and removing display devices. Called whenever a display
     * device is attached or removed.
     */
    private static final class DeviceCallback extends GLFWMonitorCallback {

        /**
         * Populates the initial list of display devices upon construction.
         */
        public DeviceCallback() {
            refreshDevices();
        }

        /**
         * Called whenever a display device is connected or disconnected.
         *
         * @param id the GLFW display ID of the device that triggered the event.
         * @param event the GLFW device callback event.
         */
        @Override
        public final void invoke(final long id, final int event) {
            refreshDevices();
            if (event == GLFW_CONNECTED) {
                LOG.info("Display device [{}] has been connected", DEVICES.get(id).getName());
            } else {
                LOG.info("Display device {} has been disconnected.", DEVICES.get(id).getName());
            }
        }

        /**
         * Updates the list of active {@link DisplayDevice devices}.
         */
        private void refreshDevices() {
            DEVICES.clear();

            PointerBuffer deviceIds = glfwGetMonitors();

            IntBuffer x = BufferUtils.createIntBuffer(1);
            IntBuffer y = BufferUtils.createIntBuffer(1);

            while (deviceIds.hasRemaining()) {
                /* Set Device ID */
                long id = deviceIds.get();

                /* Set Device Display Size */
                x.rewind();
                y.rewind();
                glfwGetMonitorPhysicalSize(id, x, y);
                Vec2f size = new Vec2f(x.get(), y.get());

                /* Get Device Position */
                x.rewind();
                y.rewind();
                glfwGetMonitorPos(id, x, y);
                Vec2f position = new Vec2f(x.get(), y.get());

                /* Get Supported Display Modes */
                GLFWVidMode.Buffer modes = glfwGetVideoModes(id);
                List<DisplayMode> supportedDisplayModes = new ArrayList<>();
                while (modes.hasRemaining()) {
                    supportedDisplayModes.add(new DisplayMode(modes.get()));
                }

                DEVICES.put(id, new DisplayDevice(id, glfwGetMonitorName(id), size, position, new DisplayMode(glfwGetVideoMode(id)), supportedDisplayModes));
            }
        }
    };

    /**
     * Receives frame buffer resize events from GLFW and forwards these requests
     * to all {@link DisplayListener listeners} attached to the target
     * {@link Display display}.
     */
    private static final GLFWFramebufferSizeCallback CALLBACK_RESIZE = new GLFWFramebufferSizeCallback() {
        @Override
        public final void invoke(final long display, final int width, final int height) {
            DISPLAYS.get(display).onResize(width, height);
        }

    };

    /**
     * Receives display close requests from GLFW and forwards these requests to
     * all {@link DisplayListener listeners} attached to the target
     * {@link Display display}.
     */
    private static final GLFWWindowCloseCallback CALLBACK_CLOSE = new GLFWWindowCloseCallback() {
        @Override
        public final void invoke(final long display) {
            DISPLAYS.get(display).onClose();
        }
    };

    /**
     * Receives display position changes from GLFW and forwards these changes to
     * all {@link DisplayListener listeners} attached to the target
     * {@link Display display}.
     */
    private static final GLFWWindowPosCallback CALLBACK_POSITION = new GLFWWindowPosCallback() {
        @Override
        public final void invoke(final long display, final int xPos, final int yPos) {
            DISPLAYS.get(display).onMove(xPos, yPos);
        }
    };

    /**
     * Receives minimization/maximization (iconify) events from GLFW and
     * forwards these events to the {@link Display display} that triggered them.
     */
    private static final GLFWWindowIconifyCallback CALLBACK_ICONIFY = new GLFWWindowIconifyCallback() {
        @Override
        public final void invoke(final long display, final boolean iconify) {
            DISPLAYS.get(display).onIconify(iconify);
        }
    };

    /**
     * Receives glfwMap focus requests from GLFW and forwards these requests to
     * all {@link DisplayListener#onFocus() listeners} attached to the target
     * {@link Display display}.
     */
    private static final GLFWWindowFocusCallback CALLBACK_FOCUS = new GLFWWindowFocusCallback() {
        @Override
        public final void invoke(final long display, final boolean focused) {
            DISPLAYS.get(display).onFocus(focused);
        }
    };

    /**
     * Receives events triggered by {@link org.lwjgl.glfw GLFW} that correspond
     * to key presses. These events are then forwarded to the
     * {@link Display display} that triggered the event.
     */
    private static final GLFWKeyCallback CALLBACK_KEY = new GLFWKeyCallback() {
        @Override
        public void invoke(long id, int key, int scancode, int action, int mods) {
            DISPLAYS.get(id).onInput(KEY_MAP.get(key), STATE_MAP.get(action), mods);
        }
    };

    /**
     * Receives events triggered by {@link org.lwjgl.glfw GLFW} that correspond
     * to key character presses. These characters are then forwarded to the
     * onChar() method of the focused display.
     */
    private static final GLFWCharCallback CALLBACK_KEY_CHAR = new GLFWCharCallback() {
        @Override
        public void invoke(long display, int character) {
            DISPLAYS.get(display).onChar((char) character);
        }
    };

    /**
     * Receives events triggered by {@link org.lwjgl.glfw GLFW} when the mouse
     * cursor enters or exits a {@link Display display}. These events are then
     * forwarded to the {@link Display display} that triggered the event.
     */
    private static final GLFWCursorEnterCallback CALLBACK_CURSOR_ENTER = new GLFWCursorEnterCallback() {
        @Override
        public void invoke(final long id, final boolean entered) {
            DISPLAYS.get(id).onMouseEnter(entered);
        }
    };

    /**
     * Receives and forwards mouse movement events from GLFW to the appropriate
     * display callback.
     */
    private static final GLFWCursorPosCallback CALLBACK_CURSOR = new GLFWCursorPosCallback() {
        @Override
        public void invoke(final long id, final double xPos, final double yPos) {
            DISPLAYS.get(id).onMouseMove((int) xPos, (int) yPos);
        }
    };

    /**
     * Receives and processes mouse button events from GLFW and forwards these
     * events to all {@link DisplayListener listeners} attached to the target
     * {@link Display display}
     */
    private static final GLFWMouseButtonCallback CALLBACK_MOUSE_BUTTON = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(final long id, final int button, final int action, final int mods) {
            DISPLAYS.get(id).onInput(MOUSE_BUTTON_MAP.get(button), STATE_MAP.get(action), mods);
        }
    };

    /**
     * Receives mouse wheel scroll events from {@link org.lwjgl.glfw.GLFW GLFW}
     * and passes those events on to the {@link Display#onMouseScroll()} method
     * of the display in focus.
     */
    private static final GLFWScrollCallback CALLBACK_MOUSE_SCROLL = new GLFWScrollCallback() {

        /**
         * Receives mouse wheel scroll events from
         * {@link org.lwjgl.glfw.GLFW GLFW} and passes those events on to the
         * {@link Display#onMouseScroll()} method of the display in focus.
         *
         * @param id the GLFW identifier of the display in focus.
         * @param xOffset horizontal scroll offset (usually zero, this parameter
         * is intentionally ignored).
         * @param yOffset vertical scroll wheel offset.
         */
        @Override
        public void invoke(final long id, final double xOffset, final double yOffset) {
            DISPLAYS.get(id).onMouseScroll((int) yOffset);
        }
    };

    /**
     * Stores the engine {@link Input.State input state} along with its
     * associated GLFW input state.
     */
    private static final Map<Integer, Input.State> STATE_MAP = new TreeMap() {
        {
            put(GLFW.GLFW_PRESS, Input.State.Pressed);
            put(GLFW.GLFW_REPEAT, Input.State.Held);
            put(GLFW.GLFW_RELEASE, Input.State.Released);
        }
    };

    /**
     * Stores each {@link Input.Mouse mouse button input} along with its
     * associated GLFW mouse button input.
     */
    private static final Map<Integer, Input.Mouse> MOUSE_BUTTON_MAP = new TreeMap() {
        {
            put(GLFW.GLFW_MOUSE_BUTTON_LEFT, Input.Mouse.Left);
            put(GLFW.GLFW_MOUSE_BUTTON_RIGHT, Input.Mouse.Right);
            put(GLFW.GLFW_MOUSE_BUTTON_MIDDLE, Input.Mouse.Middle);
            put(GLFW.GLFW_MOUSE_BUTTON_4, Input.Mouse.Forward);
            put(GLFW.GLFW_MOUSE_BUTTON_5, Input.Mouse.Back);
            put(GLFW.GLFW_MOUSE_BUTTON_6, Input.Mouse.Six);
            put(GLFW.GLFW_MOUSE_BUTTON_7, Input.Mouse.Seven);
            put(GLFW.GLFW_MOUSE_BUTTON_8, Input.Mouse.Eight);
        }
    };

    /**
     * Stores each {@link Input.Key key input} along with its associated GLFW
     * key input.
     */
    private static final Map<Integer, Input> KEY_MAP = new TreeMap() {
        {
            put(GLFW.GLFW_KEY_UNKNOWN, Input.Key.Unknown);

            put(GLFW.GLFW_KEY_A, Input.Key.A);
            put(GLFW.GLFW_KEY_B, Input.Key.B);
            put(GLFW.GLFW_KEY_C, Input.Key.C);
            put(GLFW.GLFW_KEY_D, Input.Key.D);
            put(GLFW.GLFW_KEY_E, Input.Key.E);
            put(GLFW.GLFW_KEY_F, Input.Key.F);
            put(GLFW.GLFW_KEY_G, Input.Key.G);
            put(GLFW.GLFW_KEY_H, Input.Key.H);
            put(GLFW.GLFW_KEY_I, Input.Key.I);
            put(GLFW.GLFW_KEY_J, Input.Key.J);
            put(GLFW.GLFW_KEY_K, Input.Key.K);
            put(GLFW.GLFW_KEY_L, Input.Key.L);
            put(GLFW.GLFW_KEY_M, Input.Key.M);
            put(GLFW.GLFW_KEY_N, Input.Key.N);
            put(GLFW.GLFW_KEY_O, Input.Key.O);
            put(GLFW.GLFW_KEY_P, Input.Key.P);
            put(GLFW.GLFW_KEY_Q, Input.Key.Q);
            put(GLFW.GLFW_KEY_R, Input.Key.R);
            put(GLFW.GLFW_KEY_S, Input.Key.S);
            put(GLFW.GLFW_KEY_T, Input.Key.T);
            put(GLFW.GLFW_KEY_U, Input.Key.U);
            put(GLFW.GLFW_KEY_V, Input.Key.V);
            put(GLFW.GLFW_KEY_W, Input.Key.W);
            put(GLFW.GLFW_KEY_X, Input.Key.X);
            put(GLFW.GLFW_KEY_Y, Input.Key.Y);
            put(GLFW.GLFW_KEY_Z, Input.Key.Z);

            put(GLFW.GLFW_KEY_PERIOD, Input.Key.Period);
            put(GLFW.GLFW_KEY_COMMA, Input.Key.Comma);
            put(GLFW.GLFW_KEY_APOSTROPHE, Input.Key.Apostrophe);
            put(GLFW.GLFW_KEY_SEMICOLON, Input.Key.Semicolon);
            put(GLFW.GLFW_KEY_GRAVE_ACCENT, Input.Key.Accent);
            put(GLFW.GLFW_KEY_SLASH, Input.Key.Slash);
            put(GLFW.GLFW_KEY_BACKSLASH, Input.Key.Backslash);
            put(GLFW.GLFW_KEY_LEFT_BRACKET, Input.Key.BracketLeft);
            put(GLFW.GLFW_KEY_RIGHT_BRACKET, Input.Key.BracketRight);

            put(GLFW.GLFW_KEY_SPACE, Input.Key.Space);
            put(GLFW.GLFW_KEY_TAB, Input.Key.Tab);
            put(GLFW.GLFW_KEY_BACKSPACE, Input.Key.Backspace);

            put(GLFW.GLFW_KEY_LEFT_ALT, Input.Key.AltLeft);
            put(GLFW.GLFW_KEY_RIGHT_ALT, Input.Key.AltRight);
            put(GLFW.GLFW_KEY_LEFT_CONTROL, Input.Key.CtrlLeft);
            put(GLFW.GLFW_KEY_RIGHT_CONTROL, Input.Key.CtrlRight);
            put(GLFW.GLFW_KEY_LEFT_SHIFT, Input.Key.ShiftLeft);
            put(GLFW.GLFW_KEY_RIGHT_SHIFT, Input.Key.ShiftRight);
            put(GLFW.GLFW_KEY_LEFT_SUPER, Input.Key.SuperLeft);
            put(GLFW.GLFW_KEY_RIGHT_SUPER, Input.Key.SuperRight);

            put(GLFW.GLFW_KEY_ENTER, Input.Key.Enter);
            put(GLFW.GLFW_KEY_ESCAPE, Input.Key.Escape);
            put(GLFW.GLFW_KEY_INSERT, Input.Key.Insert);
            put(GLFW.GLFW_KEY_DELETE, Input.Key.Delete);
            put(GLFW.GLFW_KEY_HOME, Input.Key.Home);
            put(GLFW.GLFW_KEY_END, Input.Key.End);
            put(GLFW.GLFW_KEY_LAST, Input.Key.Last);
            put(GLFW.GLFW_KEY_MENU, Input.Key.Menu);
            put(GLFW.GLFW_KEY_PAUSE, Input.Key.Pause);
            put(GLFW.GLFW_KEY_PRINT_SCREEN, Input.Key.Print);
            put(GLFW.GLFW_KEY_PAGE_UP, Input.Key.PageUp);
            put(GLFW.GLFW_KEY_PAGE_DOWN, Input.Key.PageDown);
            put(GLFW.GLFW_KEY_CAPS_LOCK, Input.Key.CapsLock);
            put(GLFW.GLFW_KEY_SCROLL_LOCK, Input.Key.ScrollLock);

            put(GLFW.GLFW_KEY_UP, Input.Key.Up);
            put(GLFW.GLFW_KEY_DOWN, Input.Key.Down);
            put(GLFW.GLFW_KEY_LEFT, Input.Key.Left);
            put(GLFW.GLFW_KEY_RIGHT, Input.Key.Right);

            put(GLFW.GLFW_KEY_F1, Input.Key.F1);
            put(GLFW.GLFW_KEY_F2, Input.Key.F2);
            put(GLFW.GLFW_KEY_F3, Input.Key.F3);
            put(GLFW.GLFW_KEY_F4, Input.Key.F4);
            put(GLFW.GLFW_KEY_F5, Input.Key.F5);
            put(GLFW.GLFW_KEY_F6, Input.Key.F6);
            put(GLFW.GLFW_KEY_F7, Input.Key.F7);
            put(GLFW.GLFW_KEY_F8, Input.Key.F8);
            put(GLFW.GLFW_KEY_F9, Input.Key.F9);
            put(GLFW.GLFW_KEY_F10, Input.Key.F10);
            put(GLFW.GLFW_KEY_F11, Input.Key.F11);
            put(GLFW.GLFW_KEY_F12, Input.Key.F12);
            put(GLFW.GLFW_KEY_F13, Input.Key.F13);
            put(GLFW.GLFW_KEY_F14, Input.Key.F14);
            put(GLFW.GLFW_KEY_F15, Input.Key.F15);
            put(GLFW.GLFW_KEY_F16, Input.Key.F16);
            put(GLFW.GLFW_KEY_F17, Input.Key.F17);
            put(GLFW.GLFW_KEY_F18, Input.Key.F18);
            put(GLFW.GLFW_KEY_F19, Input.Key.F19);
            put(GLFW.GLFW_KEY_F20, Input.Key.F20);
            put(GLFW.GLFW_KEY_F21, Input.Key.F21);
            put(GLFW.GLFW_KEY_F22, Input.Key.F22);
            put(GLFW.GLFW_KEY_F23, Input.Key.F23);
            put(GLFW.GLFW_KEY_F24, Input.Key.F24);
            put(GLFW.GLFW_KEY_F25, Input.Key.F25);

            put(GLFW.GLFW_KEY_0, Input.Key.Zero);
            put(GLFW.GLFW_KEY_1, Input.Key.One);
            put(GLFW.GLFW_KEY_2, Input.Key.Two);
            put(GLFW.GLFW_KEY_3, Input.Key.Three);
            put(GLFW.GLFW_KEY_4, Input.Key.Four);
            put(GLFW.GLFW_KEY_5, Input.Key.Five);
            put(GLFW.GLFW_KEY_6, Input.Key.Six);
            put(GLFW.GLFW_KEY_7, Input.Key.Seven);
            put(GLFW.GLFW_KEY_8, Input.Key.Eight);
            put(GLFW.GLFW_KEY_9, Input.Key.Nine);

            put(GLFW.GLFW_KEY_KP_0, Input.Numpad.Zero);
            put(GLFW.GLFW_KEY_KP_1, Input.Numpad.One);
            put(GLFW.GLFW_KEY_KP_2, Input.Numpad.Two);
            put(GLFW.GLFW_KEY_KP_3, Input.Numpad.Three);
            put(GLFW.GLFW_KEY_KP_4, Input.Numpad.Four);
            put(GLFW.GLFW_KEY_KP_5, Input.Numpad.Five);
            put(GLFW.GLFW_KEY_KP_6, Input.Numpad.Six);
            put(GLFW.GLFW_KEY_KP_7, Input.Numpad.Seven);
            put(GLFW.GLFW_KEY_KP_8, Input.Numpad.Eight);
            put(GLFW.GLFW_KEY_KP_9, Input.Numpad.Nine);

            put(GLFW.GLFW_KEY_KP_ADD, Input.Numpad.Add);
            put(GLFW.GLFW_KEY_KP_SUBTRACT, Input.Numpad.Subtract);
            put(GLFW.GLFW_KEY_KP_MULTIPLY, Input.Numpad.Multiply);
            put(GLFW.GLFW_KEY_KP_DIVIDE, Input.Numpad.Divide);

            put(GLFW.GLFW_KEY_KP_ENTER, Input.Numpad.Enter);
            put(GLFW.GLFW_KEY_KP_DECIMAL, Input.Numpad.Decimal);
            put(GLFW.GLFW_KEY_KP_EQUAL, Input.Numpad.Equal);
            put(GLFW.GLFW_KEY_NUM_LOCK, Input.Numpad.Lock);
        }
    };
}
