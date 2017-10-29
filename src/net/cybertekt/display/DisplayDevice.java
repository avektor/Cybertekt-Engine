package net.cybertekt.display;

import java.util.List;
import org.joml.Vector2f;

/**
 * Display Device - (C) Cybertekt Software.
 *
 * Immutable class that stores the physical properties, current settings, and
 * supported settings of a display device.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class DisplayDevice {

    /**
     * GLFW Pointer that references the physical display device.
     */
    private final long id;

    /**
     * Name of the physical device.
     */
    private final String name;

    /**
     * Estimated physical size of the display device in millimeters.
     */
    private final Vector2f size = new Vector2f();

    /**
     * Virtual position of the display device in screen coordinates.
     */
    private final Vector2f position = new Vector2f();

    /**
     * Current {@link DisplayMode display settings} for the device.
     */
    private final DisplayMode settings;

    /**
     * List of alternative {@link DisplayMode display modes} supported by the
     * device.
     */
    private final List<DisplayMode> supportedSettings;

    /**
     * Constructs a new display device tied to the specified device ID. Display
     * devices can only be constructed by classes that are part of the
     * {@link net.cybertekt.core.display Display} package.
     *
     * @param id the GLFW Pointer ID that identifies the physical device.
     * @param name the name of the physical display device.
     * @param size the estimated physical size of the display device in
     * millimeters.
     * @param position the virtual position of the display device in screen
     * coordinates.
     * @param mode indicates the device's current display mode.
     * @param supportedDisplayModes the list of alternative
     * {@link DisplayMode display modes} supported by the device.
     */
    public DisplayDevice(final long id, final String name, final Vector2f size, final Vector2f position, final DisplayMode mode, final List<DisplayMode> supportedDisplayModes) {
        this.id = id;
        this.name = name;
        this.size.set(size);
        this.position.set(position);
        this.settings = mode;
        this.supportedSettings = supportedDisplayModes;
    }

    /**
     * Returns the GLFW pointer that identifies this physical display device.
     *
     * @return the id of this display device.
     */
    public final long getId() {
        return id;
    }

    /**
     * Returns the human-readable name of the physical display device.
     *
     * @return the human-readable name of this display device.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the estimated physical size of the display device in millimeters.
     * The returned value has no relation to the current resolution of the
     * display.
     *
     * @return the estimated size of this device.
     */
    public final Vector2f getSize() {
        return size;
    }

    /**
     * Returns the estimated physical width of the display device in
     * millimeters. The returned value has no relation to the current resolution
     * of the display.
     *
     * @return the estimated width of this device.
     */
    public final int getWidth() {
        return (int) size.x();
    }

    /**
     * Returns the estimated physical height of the display device in
     * millimeters. The returned value has no relation to the current resolution
     * of the display.
     *
     * @return the estimated height of this device.
     */
    public final int getHeight() {
        return (int) size.y();
    }

    /**
     * Returns the virtual position of the display device in screen coordinates.
     *
     * @return the virtual position of this device.
     */
    public final Vector2f getPosition() {
        return position;
    }

    /**
     * Returns the virtual x-axis position of the display device in screen
     * coordinates.
     *
     * @return the virtual x-axis position of this device.
     */
    public final int getPositionX() {
        return (int) position.x();
    }

    /**
     * Returns the virtual y-axis position of the display device in screen
     * coordinates.
     *
     * @return the virtual y-axis position of this device.
     */
    public final int getPositionY() {
        return (int) position.y();
    }

    /**
     * Returns the current resolution of the display device in pixels.
     *
     * @return the current resolution of the display device in pixels.
     */
    public final Vector2f getResolution() {
        return settings.getResolution();
    }

    /**
     * Returns the current refresh rate of the display device.
     *
     * @return the refresh rate of the display device.
     */
    public final int getRefreshRate() {
        return settings.getRefreshRate();
    }

    /**
     * Returns the number of bits per pixel.
     *
     * @return the number of bits per pixel.
     */
    public final int getBpp() {
        return settings.getBpp();
    }

    /**
     * Returns the current {@link DisplayMode display mode} for this display
     * device.
     *
     * @return the current {@link DisplayMode display mode} of this device.
     */
    public final DisplayMode getDisplayMode() {
        return settings;
    }

    /**
     * Returns the list of {@link DisplayMode display modes} supported by the
     * display device.
     *
     * @return the list of {@link DisplayMode display modes} supported by this
     * device.
     */
    public final List<DisplayMode> getSupportedDisplaySettings() {
        return supportedSettings;
    }

    /**
     * Indicates if the specified {@link DisplayMode display mode} is supported
     * by this display device.
     *
     * @param mode the {@link DisplayMode display mode}.
     * @return true if the specified {@link DisplayMode display mode} is
     * supported by this device, false otherwise.
     */
    public final boolean isSupported(final DisplayMode mode) {
        return mode == null ? false : supportedSettings.stream().anyMatch((m) -> (m.equals(mode)));
    }

    /**
     * Indicates if the specified display resolution is supported by this
     * display device.
     *
     * @param resolution a {@link Vector2f vector} that specifies the display
     * resolution in pixels.
     * @return true if the specified display resolution is supported by this
     * device, false otherwise.
     */
    public final boolean isResolutionSupported(final Vector2f resolution) {
        return resolution == null ? false : supportedSettings.stream().anyMatch((m) -> (m.getResolution().equals(resolution)));
    }

    /**
     * Indicates if the specified display resolution is supported by this
     * display device.
     *
     * @param width the display resolution width in pixels.
     * @param height the display resolution height in pixels.
     * @return true if the specified display resolution is supported by this
     * device, false otherwise.
     */
    public final boolean isResolutionSupported(final int width, final int height) {
        return supportedSettings.stream().anyMatch((m) -> (m.getWidth() == width && m.getHeight() == height));
    }

    /**
     * Indicates if the specified refresh rate is supported by this display
     * device.
     *
     * @param refreshRate the refresh rate in hertz.
     * @return true if the specified refresh rate is supported by this device,
     * false otherwise.
     */
    public final boolean isRefreshRateSupported(final int refreshRate) {
        return supportedSettings.stream().anyMatch((m) -> (m.getRefreshRate() == refreshRate));
    }

    /**
     * Indicates if the specified number of bits per pixel is supported by this
     * display device.
     *
     * @param bpp the number of color bits per pixel.
     * @return true if the specified number of bits per pixel is supported by
     * this device, false otherwise.
     */
    public final boolean isBppSupported(final int bpp) {
        return supportedSettings.stream().anyMatch((m) -> (m.getBpp() == bpp));
    }

    /**
     * Returns a human-readable String that contains basic information about
     * this device. Lists the device name, id, size, position, current display
     * mode, and number of supported display modes.
     *
     * @return the human-readable String that contains basic information about
     * this device.
     */
    @Override
    public final String toString() {
        return "\nDevice ID: [" + getId() + "]\n"
                + "Device Name: [" + getName() + "]\n"
                + "Device Size: [" + getSize() + "]\n"
                + "Device Position: [" + getPosition() + "]\n"
                + "Device Resolution: [" + getResolution() + "\n"
                + "Device Refresh Rate: [" + getRefreshRate() + "\n"
                + "Device Bits Per Pixel: [" + getBpp() + "\n"
                + "Supported Display Modes: [" + getSupportedDisplaySettings().size() + "]\n";
    }
}
