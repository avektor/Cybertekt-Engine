package net.cybertekt.display;

/**
 * Display Settings - (C) Cybertekt Software
 *
 * Defines basic initialization settings for use in conjuction with
 * {@link Display#create(net.cybertekt.display.DisplaySettings)} for the
 * creation and initialization of {@link Display displays}. These settings are
 * only used during initialization, changes made to these settings after the
 * display has been initialized will have no effect.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class DisplaySettings {

    /**
     * Specifies the {@link DisplayDevice device} on which to create the
     * {@link Display display}.
     */
    private DisplayDevice device;

    /**
     * Specifies whether the display should be visible upon initialization.
     * Enabled by default.
     */
    private boolean visible = true;

    /**
     * Indicates if the display should gain input focus upon initialization.
     * Enabled by default.
     */
    private boolean focused = true;

    /**
     * Indicates if the display should float above other windows even when input
     * focus is lost. Disabled by default.
     */
    private boolean floating = false;

    /**
     * Indicates if the display should be limited to rendering at the same rate
     * as the {@link DisplayDevice device} refresh rate.
     */
    private boolean vsync = false;

    /**
     * Constructs default display settings for use in conjunction with
     * {@link Display#create()}.
     *
     * @param targetDevice the {@link DisplayDevice device} on which to create
     * the display.
     */
    public DisplaySettings(final DisplayDevice targetDevice) {
        device = targetDevice;
    }

    /**
     * Constructs display settings for use in conjunction with
     * {@link Display#create()}.
     *
     * @param targetDevice the target {@link DisplayDevice device} on which to
     * create the display.
     * @param visible if the display should be visible after initialization.
     * @param focused if the display should be focused after initialization.
     * @param floating to make the display float above other displays even when
     * input focus is lost.
     */
    public DisplaySettings(final DisplayDevice targetDevice, final boolean visible, final boolean focused, final boolean floating) {
        device = targetDevice;
        this.visible = visible;
        this.focused = focused;
        this.floating = floating;
    }

    /**
     * Indicates if the {@link Display display} should be made visible on
     * initialization.
     *
     * @return true of the display should be visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the visibility indicator to specify if the {@link Display display}
     * should be made visible on initialization.
     *
     * @param visible true to make the display visible on initialization.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Indicates if the {@link Display display} should gain focus on
     * initialization.
     *
     * @return true to make the display gain input focus on initialization.
     */
    public boolean isFocused() {
        return focused;
    }

    /**
     * Set the display focus indicator to specify if the {@link Display display}
     * should gain focus on initialization.
     *
     * @param focused true to give input focus to this display on
     * initialization.
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    /**
     * Indicates if the {@link Display display} should float above other windows
     * even when input focus is lost.
     *
     * @return true if the display should float above other windows, false
     * otherwise.
     */
    public boolean isFloating() {
        return floating;
    }

    /**
     * Set the {@link Display display} float indicator which specifies if the
     * display should float above other windows even when input focus is lost.
     *
     * @param floating
     */
    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    /**
     * Returns the {@link DisplayDevice device} on which to create the
     * {@link Display display}.
     *
     * @return the target {@link DisplayDevice device}.
     */
    public DisplayDevice getDisplayDevice() {
        return device;
    }

    /**
     * Sets the {@link DisplayDevice device} on which to create the
     * {@link Display display}.
     *
     * @param targetDevice the target {@link DisplayDevice device}.
     */
    public void setDisplayDevice(final DisplayDevice targetDevice) {
        device = targetDevice;
    }

    /**
     * Enables or disables vertical synchronization should be enabled for the
     * {@link Display display} which forces the {@link Display display} to
     * render at the same rate as the {@link DisplayDevice device} refresh rate.
     *
     * @param vsync true to enable vertical synchronization for the display,
     * false otherwise.
     */
    public void setVerticalSync(final boolean vsync) {
        this.vsync = vsync;
    }

    /**
     * Indicates if vertical synchronization should be enabled for the
     * {@link Display display} which forces the {@link Display display} to
     * render at the same rate as the {@link DisplayDevice device} refresh rate.
     *
     * @return true if vertical synchronization should be enabled for the
     * {@link Display display}.
     */
    public boolean isVerticalSync() {
        return vsync;
    }
}
