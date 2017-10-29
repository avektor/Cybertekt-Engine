package net.cybertekt.display;

import org.joml.Vector2f;

/**
 * Display Settings - (C) Cybertekt Software
 *
 * Defines initialization settings to be used in conjuction with
 * {@link Display#create(net.cybertekt.display.WindowSettings)} for creating
 * windowed {@link Display displays}. These settings are only used during the
 * initialization/creation of windowed displays. Any changes made to these
 * settings will have no effect on windows that have already been created and
 * initialized using these settings.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class WindowSettings extends DisplaySettings {

    /**
     * Specifies the title of the display window.
     */
    private String title;

    /**
     * Specifies the width and height of the display window specified in screen
     * coordinates.
     */
    private final Vector2f size = new Vector2f();

    /**
     * Indicates where the window should be positioned on initialization.
     */
    private final Vector2f location = new Vector2f();

    /**
     * Determines if the user should be permitted to resize the window. Enabled
     * by default.
     */
    private boolean resizable = true;

    /**
     * Determines if the window should be decorated with the standard title bar
     * on initialization. Enabled by default.
     */
    private boolean decorated = true;

    /**
     * Determines if the window should be maximized on initialization.
     */
    private boolean maximized = false;

    /**
     * Defines default settings for the initialization and construction of a
     * windowed {@link Display display}.
     */
    public WindowSettings() {
        this("Untitled Window");
    }

    /**
     * Defines settings for the initialization and construction of windowed
     * {@link Display displays}.
     *
     * @param title the title of the display window.
     */
    public WindowSettings(final String title) {
        this(title, new Vector2f(800f, 600f));
    }

    /**
     * Defines settings for the initialization and construction of windowed
     * {@link Display displays}.
     *
     * @param title the title of the display window.
     * @param size the width and height of the display window in pixels.
     */
    public WindowSettings(final String title, final Vector2f size) {
        this(title, size, new Vector2f(Display.getPrimaryDisplayDevice().getResolution().x() / 2f, Display.getPrimaryDisplayDevice().getResolution().y() / 2f).sub(size.x() / 2f, size.y() / 2f));
    }

    /**
     * Defines settings for the initialization and construction of windowed
     * {@link Display displays}.
     *
     * @param title the title of the display window.
     * @param size the width and height of the display window in pixels.
     * @param location the initial location of the display window.
     */
    public WindowSettings(final String title, final Vector2f size, final Vector2f location) {
        super(Display.getPrimaryDisplayDevice());
        this.title = title;
        this.size.set(size);
        this.location.set(location);
    }

    /**
     * Returns the title of the display window.
     *
     * @return the title of the display window.
     */
    public final String getTitle() {
        return title;
    }

    /**
     * Sets the size of the display window in pixels.
     *
     * @param width the width of the display window in pixels.
     * @param height the height of the display window in pixels.
     * @return these settings for the purpose of call chaining.
     */
    public final WindowSettings setSize(final int width, final int height) {
        this.size.set(width, height);
        return this;
    }

    /**
     * Returns the width of the display window in pixels.
     *
     * @return the width of the display window in pixels.
     */
    public final int getWidth() {
        return (int) size.x();
    }

    /**
     * Returns the height of the display window in pixels.
     *
     * @return the height of the display window in pixels.
     */
    public final int getHeight() {
        return (int) size.y();
    }

    /**
     * Sets the location of the {@link Display display} window.
     *
     * @param xLoc the upper left corner x-axis position of the window..
     * @param yLoc the upper left corner y-axis position of the window.
     * @return
     */
    public final WindowSettings setLocation(final int xLoc, final int yLoc) {
        this.location.set(xLoc, yLoc);
        return this;
    }

    /**
     * Returns the upper left corner x-axis position of the window.
     *
     * @return the upper left corner x-axis position of the display window.
     */
    public final int getX() {
        return (int) location.x();
    }

    /**
     * Returns the upper left corner y-axis position of the window.
     *
     * @return the upper left corner y-axis position of the window.
     */
    public final int getY() {
        return (int) location.y();
    }

    /**
     * Indicates if the user should be allowed to resize the display window.
     *
     * @param resizable true to make the window resizable, false otherwise.
     * @return
     */
    public final WindowSettings setResizable(final boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    /**
     * Indicates if the display window should be resizable by the user.
     *
     * @return true if the display window can be resized by the user, false
     * otherwise.
     */
    public final boolean isResizable() {
        return resizable;
    }

    /**
     * Determines if the display window should have a title bar.
     *
     * @param decorated true to make make the window decorated, false otherwise.
     * @return
     */
    public final WindowSettings setDecorated(final boolean decorated) {
        this.decorated = decorated;
        return this;
    }

    /**
     * Indicates if the display window title bar should be shown.
     *
     * @return true if the display window title should be visible, false
     * otherwise.
     */
    public final boolean isDecorated() {
        return decorated;
    }

    /**
     * Indicates if the display window should be maximized.
     *
     * @param maximized true to make the display window maximized, false
     * otherwise.
     * @return these settings for the purpose of call chaining.
     */
    public final WindowSettings setMaximized(final boolean maximized) {
        this.maximized = maximized;
        return this;
    }

    /**
     * Indicates if the display window should be maximized.
     *
     * @return true if the display window should be maximized, false otherwise.
     */
    public final boolean isMaximized() {
        return maximized;
    }

}
