package net.cybertekt.display;

import net.cybertekt.math.Vec2f;

/**
 * Display Settings - (C) Cybertekt Software
 *
 * Defines initialization settings to be used in conjuction with
 * {@link Display#create(net.cybertekt.display.WindowSettings)} for creating
 * windowed {@link Display displays}. These settings are only used during the
 * initialization/creation of windowed displays. Any changes made to these
 * settings will have no effect on windows that have already been initialized.
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
    private final Vec2f size = new Vec2f();

    /**
     * Indicates where the window should be positioned on initialization.
     */
    private final Vec2f location = new Vec2f();

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
    
    public WindowSettings() {
        this("Window");
    }
    
    public WindowSettings(final String title) {
        this(title, new Vec2f(800f, 600f));
    }
    
    /**
     * Display settings for the initialization and construction of windowed
     * {@link Display displays}.
     *
     * @param title the title of the display window.
     * @param size the width and height of the display window in pixels.
     */
    public WindowSettings(final String title, final Vec2f size) {
        this(title, size, Display.getPrimaryDisplayDevice().getResolution());
    }

    public WindowSettings(final String title, final Vec2f size, final Vec2f location) {
        super(Display.getPrimaryDisplayDevice());
        this.title = title;
        this.size.set(size);
        this.location.set(location);
    }

    public final String getTitle() {
        return title;
    }

    public final WindowSettings setSize(final int width, final int height) {
        this.size.set(width, height);
        return this;
    }

    public final int getWidth() {
        return (int) size.getX();
    }

    public final int getHeight() {
        return (int) size.getY();
    }

    /**
     * Indicates the upper left corner position of the display window.
     *
     * @param xLoc the upper left corner x-axis position of the window..
     * @param yLoc the upper left corner y-axis position of the window.
     * @return
     */
    public final WindowSettings setLocation(final int xLoc, final int yLoc) {
        this.location.set(xLoc, yLoc);
        return this;
    }

    public final int getX() {
        return (int) location.getX();
    }

    public final int getY() {
        return (int) location.getY();
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

    public final boolean isDecorated() {
        return decorated;
    }

    /**
     * Indicates if the display window should be maximized.
     *
     * @param maximized true to make the display window maximized, false
     * otherwise.
     * @return
     */
    public final WindowSettings setMaximized(final boolean maximized) {
        this.maximized = maximized;
        return this;
    }

    public final boolean isMaximized() {
        return maximized;
    }

}
