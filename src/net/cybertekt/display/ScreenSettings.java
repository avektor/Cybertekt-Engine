package net.cybertekt.display;

/**
 * Screen Settings - (C) Cybertekt Software
 *
 * Defines initialization settings to be used in conjuction with
 * {@link Display#create(net.cybertekt.display.DisplaySettings)} for the
 * creation and initialization of fullscreen {@link Display displays}.
 *
 * @author Andrew Vektor
 * @version 1.0.0
 * @since 1.0.0
 */
public class ScreenSettings extends DisplaySettings {

    private DisplayMode mode;

    /**
     * Determines if the {@link Display display} should automatically iconify
     * and restore the previous video mode when input focus is lost. Enabled by
     * default.
     */
    private boolean autoIconify = true;

    public ScreenSettings(final DisplayDevice device) {
        this(device, device.getDisplayMode());
    }

    public ScreenSettings(final DisplayDevice device, final DisplayMode mode) {
        super(device);
        this.mode = mode;
    }

    public final boolean isAutoIconify() {
        return autoIconify;
    }
}
