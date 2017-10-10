package net.cybertekt.asset.image;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.cybertekt.asset.AssetKey;
import net.cybertekt.asset.AssetLoader;
import net.cybertekt.asset.AssetManager.AssetInitializationException;
import net.cybertekt.asset.AssetType;
import net.cybertekt.asset.image.Image.ImageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image Loader - (C) Cybertekt Software.
 * <p>
 * Loads images from external files and converts them into
 * {@link Image image assets}.</p>
 *
 * Supported File Types: PNG
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class ImageLoader extends AssetLoader {

    /**
     * Static SLF4J class logger for debugging.
     */
    public static final Logger log = LoggerFactory.getLogger(ImageLoader.class);

    /**
     * {@link AssetType Asset type} for Portable Network Graphics (PNG) files.
     */
    public final AssetType PNG = AssetType.getType("PNG");

    /**
     * Returns a callable {@link AssetTask loading task} for constructing an
     * {@link Image image asset} from the {@link InputStream input stream} of
     * the image file at the path specified by the {@link AssetKey asset key}.
     *
     * @param key the {@link AssetKey key} associated with the image file.
     * @param stream the {@link InputStream input stream} of the image file.
     * @return the callable task for constructing the {@link Image image asset}.
     */
    @Override
    public AssetTask newTask(final AssetKey key, final InputStream stream) {
        if (key.getType().equals(PNG)) {
            return new PNGLoader(key, stream);
        }
        log.error("Unsupported image file type extension: {}", key.getType().toString());
        throw new UnsupportedOperationException("Unsupported image file type extension");
    }

    /**
     * PNG Loader Task - (C) Cybertekt Software.
     * <p>
     * Loads and decodes a PNG file and returns an image object for use within a
     * Cybertekt application. Supports all color types and required data chunks
     * that are defined by the W3C PNG specification. Only 8-bit and 16-bit
     * color channels are supported. Alpha transparency is supported for all
     * color types but indexed transparency (poor man's transparency) is not
     * supported. Both compressed and filtered images are supported, but
     * interlacing is not. A cyclic redundancy check is performed on each data
     * chunk of valid PNG files to ensure the file is not corrupted.</p>
     *
     * @see <a href="w3.org/tr/png">w3.org/tr/png</a> for PNG specification.
     *
     * @author Andrew Vektor
     * @version 1.0.0
     * @since 1.0.0
     */
    private final class PNGLoader extends AssetTask {

        /**
         * PNG File Signature - the first eight bytes of a PNG file always
         * contains these decimal values. If the first eight bytes differ then
         * the file is not a valid PNG file.
         */
        private final byte[] PNG = {(byte) 137, 80, 78, 71, 13, 10, 26, 10};

        /**
         * Cycle Redundancy Check - Ensures the file is not corrupted.
         */
        private final CRC32 CRC32 = new CRC32();

        /**
         * Constructs a new {@link AssetTask asset loading task} which will
         * attempt to construct an {@link Image image asset} by reading from the
         * provided {@link InputStream input stream} which must contain valid
         * PNG image file data. Interlaced PNG images are not supported.
         *
         * @param toLoad the {@link AssetKey asset key} for the PNG image file.
         * @param input the {@link InputStream input stream for the PNG image file.
         */
        PNGLoader(final AssetKey key, final InputStream input) {
            super(key, input);
        }

        /**
         * Attempts to construct and return an {@link Image image asset} using
         * the {@link InputStream input stream} provided during construction.
         *
         * @return the Image asset constructed from the data in the
         * {@link #input input stream} provided during construction.
         * @throws IOException if an {@link Image image asset} could not be
         * constructed from the data in the {@link #input input stream} provided
         * during construction.
         */
        @Override
        public final Image load() throws AssetInitializationException {
            try {
                if (input == null) {
                    throw new IOException("Provided input stream is null");
                }

                /* Check the 8-byte PNG file signature. */
                validateSignature(read(new byte[8]));

                /* Create resuable chunk object (is used to read and process each chunk sequentially). */
                Chunk chunk = new Chunk();

                /* Read the first chunk from the provided PNG file input stream. */
                chunk.readChunk(input);

                /* Header Chunk (IHDR) type must always come first directly after the file signature */
                if (chunk.getChunkType() != Chunk.ChunkType.IHDR) {
                    throw new IOException("Image file is unreadable and may be corrupt");
                }

                /* Define the width and height values by reading from the header chunk of the PNG file. */
                int width = chunk.readInt(0);
                int height = chunk.readInt(4);

                /* Validate the image dimensions. */
                if (width <= 0 || height <= 0) {
                    throw new IOException("Image size must be greater than zero");
                }

                /* Read the number of bits in each sample (not per pixel) */
                int bitDepthPerSample = chunk.data[8] & 255;

                /* Read the PNG color type code */
                int colorType = chunk.data[9] & 255;

                /* Color Palette Data */
                byte[] palette = null;

                /* Define the ImageFormat object. */
                ImageFormat format;

                switch (colorType) {
                    case 0: {
                        /* Grayscale Color Type - Each pixel is a grayscale sample */
                        switch (bitDepthPerSample) {
                            case 8: {
                                format = ImageFormat.LUM8;
                                break;
                            }
                            default: {
                                throw new IOException("Image file is unreadable and may be corrupt");
                            }
                        }
                        break;
                    }
                    case 2: {
                        /* RGB Color Type - Each pixel is an RGB triple. */
                        switch (bitDepthPerSample) {
                            case 16: {
                                format = ImageFormat.RGB16;
                                break;
                            }
                            case 8: {
                                format = ImageFormat.RGB8;
                                break;
                            }
                            default:
                                throw new IOException("Image file is unreadable and may be corrupt");
                        }
                        break;
                    }
                    case 3: {
                        /* Indexed Color Type - Each pixel is a palette index */
                        switch (bitDepthPerSample) {
                            case 8: {
                                format = ImageFormat.RGB8;
                                break;
                            }
                            default: {
                                throw new IOException("Image file is unreadable and may be corrupt");
                            }
                        }
                        break;
                    }
                    case 4: {
                        /* Grayscale Alpha Color Type - Each pixel is a grayscale sample followed by an alpha sample */
                        switch (bitDepthPerSample) {
                            case 8: {
                                format = ImageFormat.LUMA8;
                                break;
                            }
                            default:
                                throw new IOException("Image file is unreadable and may be corrupt");
                        }
                        break;
                    }
                    case 6: {
                        /* RGBA Color Type - Each pixel is an RGB triple followed by an alpha sample */
                        switch (bitDepthPerSample) {
                            case 16: {
                                format = ImageFormat.RGBA16;
                                break;
                            }
                            case 8: {
                                format = ImageFormat.RGBA8;
                                break;
                            }
                            default:
                                throw new IOException("Image file is unreadable and may be corrupt");
                        }
                        break;
                    }
                    default:
                        throw new IOException("Image file is unreadable and may be corrupt");
                }

                /* Check single-byte integer that indicates the method used to compress the image data. */
                if (chunk.data[10] != 0) {
                    throw new IOException("Unsupported compression method");
                }

                /* Check single-byte integer that indicates the preprocessing method applied to the image data before compression */
                if (chunk.data[11] != 0) {
                    throw new IOException("Unsupported filtering method");
                }

                /* Check single-byte integer that indicates the transmission order of the image data. */
                if (chunk.data[12] != 0) {
                    /* Interlaced image are not supported */
                    throw new IOException("Interlaced images are not supported");
                }

                /* Read the next chunk */
                chunk.readChunk(input);

                /* Create a ByteBuffer to store the image surface data */
                ByteBuffer surfaceData = ByteBuffer.allocate(width * height * format.getBytesPerPixel());

                /* Read each chunk and end when the IEND chunk is read */
                while (chunk.getChunkType() != Chunk.ChunkType.IEND) {

                    /* Reset the CRC calculator for each chunk. */
                    CRC32.reset();

                    /* Update calculated CRC from chunk information. */
                    CRC32.update(chunk.typeCode);
                    CRC32.update(chunk.data);

                    /* Ensure the stored chunk CRC matches the calculated CRC. */
                    if (chunk.crc != (int) CRC32.getValue()) {
                        /* CRC Mismatch */
                        throw new IOException("Image file is unreadable and may be corrupt");
                    }

                    /**
                     * Process PNG Chunk Data
                     */
                    switch (chunk.getChunkType()) {

                        /**
                         * PNG Header Chunk (IHDR) - Already processed above,
                         * there should never be more than one file header
                         * chunk.
                         */
                        case IHDR: {
                            /* File has multiple header chunks. */
                            throw new IOException("Image file is unreadable and may be corrupt");
                        }
                        /**
                         * PNG Palette Chunk (PLTE) - Stores the color palette
                         * for images with indexed colors.
                         */
                        case PLTE: {
                            if (chunk.length % 3 != 0) {
                                /* Palette length must be divisible by three */
                                throw new IOException("Image file is unreadable and may be corrupt");
                            }
                            palette = chunk.data;
                            break;
                        }
                        /**
                         * PNG Data Chunk (IDAT) - Stores the image surface
                         * data.
                         */
                        case IDAT: {
                            /* Calculate the size of each scanline. A scanline is a horizontal row of pixel data. */
                            final int lineSize = (colorType == 3) ? width : width * format.getBytesPerPixel();
                            int offset, stride;

                            /* Need to keep a reference to the last scanline for the purpose of unfiltering */
                            byte[] lastLine = new byte[lineSize + 1];
                            byte[] curLine = new byte[lineSize + 1];

                            /* Inflater decompresses the image surface data */
                            Inflater inflater = new Inflater();
                            inflater.setInput(chunk.data);

                            /* Calculate the buffer offset and stride based on */
                            stride = -(width * (format.getBytesPerPixel()));
                            offset = (height - 1) * -stride;

                            /* Use this instead of the two lines above to flip the image. */
                            //offset = 0;
                            //stride = img.getWidth() * img.getFormat().getComponents();
                            surfaceData.position(offset);

                            for (int y = 0; y < height; y++) {
                                surfaceData.position(offset + y * stride);
                                try {
                                    inflater.inflate(curLine);
                                } catch (final DataFormatException e) {
                                    /* Image Decompression Failed */
                                    throw new IOException("Image file is unreadable and may be corrupt");
                                }
                                unfilter(curLine, lastLine, format.getBytesPerPixel());

                                if (colorType == 3) {
                                    for (int i = 1; i < curLine.length; i++) {
                                        int index = curLine[i] & 255;
                                        byte r = palette[index * 3];
                                        byte g = palette[index * 3 + 1];
                                        byte b = palette[index * 3 + 2];
                                        surfaceData.put(r).put(g).put(b);
                                    }
                                } else {
                                    surfaceData.put(curLine, 1, curLine.length - 1);
                                }

                                byte[] tmp = curLine;
                                curLine = lastLine;
                                lastLine = tmp;
                            }
                            surfaceData.rewind();
                            break;
                        }
                        case UNKNOWN: {
                            /**
                             * Ensure the chunk is ancillary (meaning not
                             * critical to displaying the image). If the unknown
                             * chunk is ancillary it can be safely skipped.
                             */
                            if (chunk.isAncillary == false) {
                                /* An unreadable data chunk is marked as critical */
                                throw new IOException("Image file is unreadable and may be corrupt");
                            }
                            break;
                        }
                    }
                    /* Read Next Chunk */
                    chunk.readChunk(input);
                }
                input.close();
                Image img = new Image(key, format, width, height, surfaceData);
                log.debug("Loaded {}x{} PNG image from {}", width, height, key.getAbsolutePath());
                return img;
            } catch (IOException e) {
                throw new AssetInitializationException(key, e.getMessage());
            }
        }

        /*
         * Reverses the line filter based on the filter method indicated by the
         * first byte in the line.
         *
         * @param curLine the current line on which to reverse the filter.
         * @param prevLine the previous line, if any.
         * @param bpp number of bytes per pixel.
         * @return the unfiltered line.
         */
        private byte[] unfilter(byte[] curLine, byte[] prevLine, int bpp) {
            switch (curLine[0]) {
                case 0: {
                    break;
                }
                case 1: {
                    unfilterSub(curLine, bpp);
                    break;
                }
                case 2: {
                    unfilterUp(curLine, prevLine, bpp);
                    break;
                }
                case 3: {
                    unfilterAverage(curLine, prevLine, bpp);
                    break;
                }
                case 4: {
                    unfilterPaeth(curLine, prevLine, bpp);
                    break;
                }
            }
            return curLine;
        }

        /**
         * Each byte is replaced with the difference between it and the
         * corresponding byte to its left.
         *
         * @param curLine the line to unfilter.
         * @param bpp number of bytes per pixel.
         */
        private void unfilterSub(byte[] curLine, int bpp) {
            for (int i = bpp + 1, n = curLine.length; i < n; ++i) {
                curLine[i] += curLine[i - bpp];
            }
        }

        /**
         * Each byte is replaced with the difference between it and the byte
         * above it (in the previous row, as it was before filtering).
         *
         * @param curLine the line to unfilter.
         * @param prevLine the previous line.
         * @param bpp number of bytes per pixel.
         */
        private void unfilterUp(byte[] curLine, byte[] prevLine, int bpp) {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                curLine[i] += prevLine[i];
            }
        }

        /**
         * Each byte is replaced with the difference between it and the average
         * of the corresponding bytes to its left and above it, truncating any
         * fractional part.
         *
         * @param curLine the line to unfilter.
         * @param prevLine the previous line.
         * @param bpp number of bytes per pixel.
         */
        private void unfilterAverage(byte[] curLine, byte[] prevLine, int bpp) {
            int i;
            for (i = 1; i <= bpp; ++i) {
                curLine[i] += (byte) ((prevLine[i] & 0xFF) >>> 1);
            }
            for (int n = curLine.length; i < n; ++i) {
                curLine[i] += (byte) (((prevLine[i] & 0xFF) + (curLine[i - bpp] & 0xFF)) >>> 1);
            }
        }

        /**
         * Each byte is replaced with the difference between it and the Paeth
         * predictor of the corresponding bytes to its left, above it, and to
         * its upper left.
         *
         * @param curLine the line to unfilter.
         * @param prevLine the previous line.
         * @param bpp number of bytes per pixel.
         */
        private void unfilterPaeth(byte[] curLine, byte[] prevLine, int bpp) {
            int i;
            for (i = 1; i <= bpp; ++i) {
                curLine[i] += prevLine[i];
            }
            for (int n = curLine.length; i < n; ++i) {
                int a = curLine[i - bpp] & 255;
                int b = prevLine[i] & 255;
                int c = prevLine[i - bpp] & 255;
                int p = a + b - c;
                int pa = p - a;
                if (pa < 0) {
                    pa = -pa;
                }
                int pb = p - b;
                if (pb < 0) {
                    pb = -pb;
                }
                int pc = p - c;
                if (pc < 0) {
                    pc = -pc;
                }
                if (pa <= pb && pa <= pc) {
                    c = a;
                } else if (pb <= pc) {
                    c = b;
                }
                curLine[i] += (byte) c;
            }
        }

        /**
         * Indicates if the provided byte array is a valid PNG file signature.
         *
         * @param in the InputStream to read from.
         * @throws IOException if the signature cannot be read from the input
         * stream or if the signature is not valid.
         */
        private void validateSignature(byte[] toValidate) throws IOException {
            for (int i = 0; i < PNG.length; i++) {
                if (toValidate[i] != PNG[i]) {
                    throw new IOException("File is not a valid PNG image");
                }
            }
        }

        /**
         * Reads bytes from the active InputStream into the provided byte array.
         * The number of bytes read is determined by the length of the byte
         * array.
         *
         * @param data byte array to store the data read from the InputStream.
         * @return the updated byte array.
         * @throws IOException if the InputStream cannot be read from.
         */
        private byte[] read(final byte[] data) throws IOException {
            input.read(data);
            return data;
        }
    }

    /**
     * Helper class that wraps a PNG file format chunk.
     */
    private static class Chunk {

        /**
         * Defines the various types of PNG data chunks.
         */
        public enum ChunkType {

            /**
             * Header Chuck - Must be the first chunk in the file and contains
             * the image width, height, bit depth, color type, compression
             * method, filter method, and interlace method.
             */
            IHDR,
            /**
             * Palette Chunk - Contains from 1 to 256 palette entries, each a
             * three byte series of the form red, green, and blue.
             */
            PLTE,
            /**
             * Data Chunk - Contains actual image data.
             */
            IDAT,
            /**
             * End Chunk - Empty chunk that must appear last in the file and
             * marks the end of the PNG data.
             */
            IEND,
            /**
             * Unknown Chunk Type.
             */
            UNKNOWN;
        }

        /**
         * The number of bytes in the chunk's data field.
         */
        int length;

        /**
         * 4-byte chunk type code.
         *
         * @see net.cybertekt.engine.utils.PNGDecoder.Chunk.ChunkType
         */
        byte[] typeCode;

        /**
         * Indicates if the chunk is strictly necessary in order to meaningfully
         * display the contents of the file. Unknown chunks in which ancillary
         * is true can safely be ignored.
         */
        boolean isAncillary;

        /**
         * A public chunk is one that is part of the PNG specification or is
         * registered in the list of PNG special-purpose public chunk types.
         */
        boolean isPrivate;

        /**
         * Indicates if the chunk may be copied to a modified PNG file whether
         * or not the software recognizes the chunk type and regardless of the
         * extent of the file modifications.
         */
        boolean isSafeToCopy;

        /**
         * The data bytes appropriate to the chunk type. This field can be of
         * zero length.
         */
        byte[] data;

        /**
         * Stored Cycle Redundancy Checksum - The stored CRC must match the
         * calculated CRC to ensure the file is not corrupted.
         */
        int crc;

        public final void readChunk(final InputStream in) throws IOException {
            resetFields();
            length = readInt(read(in, new byte[4]), 0);
            typeCode = read(in, new byte[4]);
            isAncillary = isBitSet(typeCode[0], 5);
            isPrivate = isBitSet(typeCode[1], 5);
            isSafeToCopy = isBitSet(typeCode[3], 5);
            data = read(in, new byte[length]);
            crc = readInt(read(in, new byte[4]), 0);
        }

        /**
         * Returns the corresponding ChunkType from a 4-byte type code.
         *
         * @param type the 4-byte chunk type code.
         * @return the ChunkType that matches the provided chunk type code.
         */
        public ChunkType getChunkType() {
            if (typeCode != null) {
                switch (readInt(typeCode, 0)) {
                    case 0x49484452: {
                        return ChunkType.IHDR;
                    }
                    case 0x504C5445: {
                        return ChunkType.PLTE;
                    }
                    case 0x49444154: {
                        return ChunkType.IDAT;
                    }
                    case 0x49454E44: {
                        return ChunkType.IEND;
                    }
                    default: {
                        return ChunkType.UNKNOWN;
                    }
                }
            } else {
                return ChunkType.UNKNOWN;
            }
        }

        /**
         * Resets the Chunk class members.
         */
        private void resetFields() {
            length = 0;
            typeCode = null;
            isAncillary = false;
            isPrivate = false;
            isSafeToCopy = false;
            data = null;
            crc = 0;
        }

        /**
         * Indicates if the bit at the specified bit index of the byte is set.
         *
         * @param toCheck the byte to check the bit at the specified bit index.
         * @param bitIndex the bit within the byte to check (must not be greater
         * than 8).
         * @return true if the bit at the specified index within the byte is
         * set.
         */
        private boolean isBitSet(byte toCheck, final int bitIndex) {
            return (toCheck >> (bitIndex % 8) & 1) == 1;
        }

        /**
         * Reads a 4-byte integer from the chunk data at the specified position.
         *
         * @param position the position within the chunk data to begin reading.
         * @return 4-byte integer from the specified position of the chunk data.
         */
        private int readInt(final int position) {
            return readInt(data, position);
        }

        /**
         * Reads and integer from the specified byte array.
         *
         * @param data the byte array to read from.
         * @param offset the offset in the array from which to read from.
         * @return the integer a the specified offset in the byte array.
         */
        private int readInt(byte[] data, int offset) {
            return ((data[offset]) << 24) | ((data[offset + 1] & 255) << 16) | ((data[offset + 2] & 255) << 8) | ((data[offset + 3] & 255));
        }

        /**
         * Reads bytes from the active InputStream into the provided byte array.
         * The number of bytes read is determined by the length of the byte
         * array.
         *
         * @param data byte array to store the data read from the InputStream.
         * @return the updated byte array.
         * @throws IOException if the InputStream cannot be read from.
         */
        private byte[] read(final InputStream inputStream, final byte[] data) throws IOException {
            inputStream.read(data);
            return data;
        }
    }
}
