package net.cybertekt.display;

import java.util.Objects;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * Display Mode - (C) Cybertekt Software
 *
 * Immutable class that stores the video settings supported by a
 * {@link DisplayDevice display device}.
 *
 * @author Andrew Vektor
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DisplayMode {

    /**
     * Width and height of the display in screen coordinates (usually, but not
     * always, pixels).
     */
    private final Vector2f resolution;

    /**
     * Screen refresh rate.
     */
    private final int refreshRate;

    /**
     * Number of bits per pixel for each color channel.
     */
    private final Vector3f bpp;

    /**
     * Constructs an immutable display mode from a GLFW
     * {@link org.lwjgl.glfw.GLFWvidmode video mode}.
     *
     * @param mode the GLFW {@link org.lwjgl.glfw.GLFWvidmode video mode} to use
     * for constructing this display mode.
     */
    public DisplayMode(final GLFWVidMode mode) {
        this(new Vector2f(mode.width(), mode.height()), mode.refreshRate(), new Vector3f(mode.redBits(), mode.blueBits(), mode.greenBits()));
    }

    /**
     * Constructs an immutable display mode.
     *
     * @param resolution the resolution if the display in screen coordinates.
     * @param refreshRate the refresh rate of the display in hertz.
     * @param bpp the number of bits per pixel in each color channel.
     */
    public DisplayMode(final Vector2f resolution, final int refreshRate, final Vector3f bpp) {
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.bpp = bpp;
    }

    /**
     * Returns a copy of the {@link Vector2f vector} that stores the display
     * resolution as screen coordinates.
     *
     * @return a copy of the {@link Vector2f vector} that stores the display
     * resolution screen coordinates.
     */
    public final Vector2f getResolution() {
        return new Vector2f(resolution);
    }

    /**
     * Returns the width of the display resolution as screen coordinates.
     *
     * @return the width of the display resolution as screen coordinates.
     */
    public final int getWidth() {
        return (int) resolution.x();
    }

    /**
     * Returns the height of the display resolution as screen coordinates.
     *
     * @return the height of the display resolution as screen coordinates.
     */
    public final int getHeight() {
        return (int) resolution.y();
    }

    /**
     * Returns the display refresh rate in hertz.
     *
     * @return the display refresh rate in hertz.
     */
    public final int getRefreshRate() {
        return refreshRate;
    }

    /**
     * Returns the total number of bits per pixel by totaling the total number
     * of bits per pixel for each of the three color channels.
     *
     * @return the total number of bits per pixel.
     */
    public final int getBpp() {
        return (int) (bpp.x() + bpp.y() + bpp.z());
    }

    /**
     * Determines if the specified object is equal to this object. This method
     * will return true if and only if the provided object is an instance of
     * {@link DisplayMode} and its {@link #resolution}, {@link #refreshRate},
     * and {@link #bpp} fields are all equal.
     *
     * @param obj the object to compare.
     * @return true if the specified object is equal to this object.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof DisplayMode) {
            DisplayMode m = (DisplayMode) obj;
            return m.resolution.equals(resolution) && m.refreshRate == refreshRate && m.bpp.equals(bpp);
        } else {
            return false;
        }
    }

    /**
     * Returns the hash code of this display mode which is calculated based on
     * the internal {@link #resolution}, {@link #refreshRate}, and {@link #bpp}
     * fields.
     *
     * @return the computed hash code of this display mode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.resolution);
        hash = 47 * hash + this.refreshRate;
        hash = 47 * hash + Objects.hashCode(this.bpp);
        return hash;
    }

    /**
     * Returns a human-readable String that summarizes the internal fields of
     * this display mode. The returned String is defined as: <br />
     * [Width]x[Height] @ [Refresh Rate] - [Bits Per Pixel]
     *
     * @return a human-readable String that summarizes the internal properties
     * of this display mode.
     */
    @Override
    public final String toString() {
        return getWidth() + "x" + getHeight() + " @ " + getRefreshRate() + "hz - " + getBpp() + " Bpp";
    }
}
