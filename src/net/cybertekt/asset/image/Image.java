package net.cybertekt.asset.image;

import java.nio.ByteBuffer;
import net.cybertekt.asset.Asset;
import net.cybertekt.asset.AssetKey;

/**
 * Image - (C) Cybertekt Software.
 *
 * Immutable {@link Asset asset} class that stores the format and surface data
 * of an image.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Image extends Asset {

    /**
     * Indicates how the image surface data is stored within the image.
     */
    public enum ImageFormat {

        /*
         * One 8-bit channel (single alpha channel).
         */
        Alpha(1, 8, true),
        /**
         * Three 8-bit channels: blue, green, and red.
         */
        BGR(3, 24, false),
        /**
         * Four 8-bit channels: blue, green, red, and alpha.
         */
        BGRA(4, 32, true),
        /**
         * One 8-bit luminance/grayscale channel.
         */
        LUM8(1, 8, false),
        /**
         * Two 8-bit channels: luminance/grayscale and alpha.
         */
        LUMA8(2, 16, true),
        /**
         * Three 8-bit channels: red, green, and blue.
         */
        RGB8(3, 24, false),
        /**
         * Three 16-bit channels: red, green, and blue.
         */
        RGB16(6, 48, false),
        /**
         * Four 8-bit channels: red, green, blue, and alpha.
         */
        RGBA8(4, 32, true),
        /**
         * Four 16-bit channels: red, green, blue, and alpha.
         */
        RGBA16(4, 64, true);

        /**
         * The number of channels per pixel.
         */
        private final int components;

        /**
         * The number of bits that describe a single pixel channel.
         */
        private final int bitsPerPixel;

        /**
         * Indicates if the format has an alpha channel.
         */
        private final boolean hasAlpha;

        /**
         * Image Format enumerator constructor.
         *
         * @param components the number of channels per pixel.
         * @param bpp the number of bits per pixel.
         * @param hasAlpha indicate if the format has an alpha channel.
         */
        ImageFormat(final int components, final int bpp, final boolean hasAlpha) {
            this.components = components;
            this.bitsPerPixel = bpp;
            this.hasAlpha = hasAlpha;
        }

        /**
         * Returns the number of bits per pixel.
         *
         * @return the number of bits per pixel.
         */
        public final int getBitsPerPixel() {
            return bitsPerPixel;
        }

        /**
         * Returns the number of bytes per pixel.
         *
         * @return the number of bytes per pixel.
         */
        public final int getBytesPerPixel() {
            return bitsPerPixel / 8;
        }

        /**
         * Returns the number of color components (channels) in this image
         * format. E.g. RGBA returns four.
         *
         * @return the number of color components (channels).
         */
        public final int getComponents() {
            return components;
        }

        /**
         * Indicates if the image format has an alpha channel.
         *
         * @return true if the format has an alpha channel or false if it does
         * not.
         */
        public final boolean hasAlpha() {
            return hasAlpha;
        }
    }
    /**
     * The size of the image.
     */
    private final int width, height;

    /**
     * Describes how the image data is stored.
     */
    private final ImageFormat format;

    /**
     * Image surface data.
     */
    private final ByteBuffer data;

    public Image(final AssetKey key, final ImageFormat format, final int width, final int height, final ByteBuffer surfaceData) {
        super(key);
        this.format = format;
        this.width = width;
        this.height = height;
        this.data = surfaceData;
    }

    /**
     * Returns a String that contains the file name of the image as specified by
     * its associated {@link AssetKey asset key}.
     *
     * @param includeFileExtension true to include the file type extension of
     * the image.
     * @return the file name of the image as specified by its
     * {@link AssetKey asset key}.
     */
    public final String getName(final boolean includeFileExtension) {
        return getKey().getName(includeFileExtension);
    }

    /**
     * Indicates the width of the image in pixels.
     *
     * @return the width of the image in pixels.
     */
    public final int getWidth() {
        return width;
    }

    /**
     * Indicates the height of the image in pixels.
     *
     * @return the height of the image in pixels.
     */
    public final int getHeight() {
        return height;
    }

    /**
     * Indicates how the image surface data is stored within this image object.
     *
     * @return the image format.
     */
    public final ImageFormat getFormat() {
        return format;
    }

    /**
     * Returns the surface data of the image as a byte buffer.
     *
     * @return the image surface data.
     */
    public final ByteBuffer getData() {
        return data.asReadOnlyBuffer();
    }

    /**
     * Returns the surface data of the image as a byte array.
     *
     * @return the image surface data.
     */
    public byte[] getDataArray() {
        ByteBuffer buf = data.asReadOnlyBuffer();
        byte[] dat = new byte[buf.rewind().remaining()];
        buf.get(dat);
        return dat;
    }
}
