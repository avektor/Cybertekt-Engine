package net.cybertekt.asset.shader;

import java.io.InputStream;
import net.cybertekt.asset.AssetKey;
import net.cybertekt.asset.AssetLoader;
import net.cybertekt.asset.AssetType;

/**
 * Shader Loader - (C) Cybertekt Software.
 *
 *
 * @author Andrew Vektor
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShaderLoader extends AssetLoader {

    /**
     * GLSL Vertex Shader {@link AssetType asset type}.
     */
    public final AssetType VERT = AssetType.getType("VERT");

    /**
     * GLSL Fragment Shader {@link AssetType asset type}.
     */
    public final AssetType FRAG = AssetType.getType("FRAG");

    /**
     * GLSL Geometry Shader {@link AssetType asset type}.
     */
    public final AssetType GEOM = AssetType.getType("GEOM");

    /**
     * GLSL Compute Shader (@link AssetType asset type}.
     */
    public final AssetType COMP = AssetType.getType("COMP");

    @Override
    public AssetTask newTask(final AssetKey key, final InputStream stream) {
        if (key.getType().equals(VERT)) {
            return new VSLoader(key, stream);
        } else if (key.getType().equals(FRAG)) {
            return new FSLoader(key, stream);
        } else if (key.getType().equals(GEOM)) {
            return new GSLoader(key, stream);
        } else if (key.getType().equals(COMP)) {
            return new CSLoader(key, stream);
        }
        throw new UnsupportedOperationException("Invalid shader file type extension: " + key.getAbsolutePath());
    }

    /**
     * Vertex Shader Loader - (C) Cybertekt Software.
     *
     * {@link AssetTask Task} that constructs a {@link OGLShader vertex shader}
     * by extracting valid GLSL source code from an input stream of a file
     * located at a path specified by an {@link AssetKey key}.
     */
    private class VSLoader extends AssetTask {

        /**
         * Constructs a new shader loader loader for an
         * {@link AssetKey asset key} and its corresponding
         * {@link InputStream input stream}.
         *
         * @param key an {@link AssetKey asset key} that points to the location
         * of a valid vertex shader (.frag) written in GLSL.
         * @param stream the {@link InputStream input stream} for the file
         * located at the path specified by the {@link AssetKey key}.
         */
        VSLoader(final AssetKey key, final InputStream stream) {
            super(key, stream);
        }

        /**
         * Creates a {@link OGLShader vertex shader asset} by extracting a
         * String from the {@link InputStream stream} provided during the
         * construction of this {@link AssetTask task} and using it as the
         * source code to construct the {@link OGLShader vertex shader asset}.
         *
         * @return the {@link OGLShader vertex shader} constructed using the
         * String extracted from the {@link InputStream input stream} associated
         * with this loader as the shader source code.
         */
        @Override
        public OGLShader load() {
            return new OGLShader(key, OGLShader.Type.Vertex, new java.util.Scanner(input).useDelimiter("\\A").next());
        }

    }

    /**
     * Fragment Shader Loader - (C) Cybertekt Software.
     *
     * {@link AssetTask Task} that constructs a {@link OGLShader frag shader} by
     * extracting valid GLSL source code from an input stream of the file
     * located at the path specified by an {@link AssetKey key}.
     */
    private class FSLoader extends AssetTask {

        /**
         * Constructs a new shader loader for an {@link AssetKey asset key} and
         * its corresponding {@link InputStream input stream}.
         *
         * @param key an {@link AssetKey asset key} that points to the location
         * of a valid fragment shader (.frag) written in GLSL.
         * @param stream the {@link InputStream input stream} for the file
         * located at the path specified by the {@link AssetKey key}.
         */
        FSLoader(final AssetKey key, final InputStream stream) {
            super(key, stream);
        }

        /**
         * Creates a {@link OGLShader fragment shader asset} by extracting a
         * String from the {@link InputStream stream} provided during the
         * construction of this {@link AssetTask task} and using it as the
         * source code to construct the {@link OGLShader fragment shader asset}.
         *
         * @return the {@link OGLShader fragment shader} constructed using the
         * String extracted from the {@link InputStream input stream} associated
         * with this loader as the shader source code.
         */
        @Override
        public OGLShader load() {
            return new OGLShader(key, OGLShader.Type.Fragment, new java.util.Scanner(input).useDelimiter("\\A").next());
        }
    }

    /**
     * Geometry Shader Loader - (C) Cybertekt Software.
     *
     * {@link AssetTask Task} that constructs a {@link OGLShader geom shader} by
     * extracting valid GLSL source code from an input stream of the file
     * located at the path specified by an {@link AssetKey key}.
     */
    private class GSLoader extends AssetTask {

        /**
         * Constructs a new shader loader for an {@link AssetKey asset key} and
         * its corresponding {@link InputStream input stream}.
         *
         * @param key an {@link AssetKey asset key} that points to the location
         * of a valid geometry shader (.geom) written in GLSL.
         * @param stream the {@link InputStream input stream} for the file
         * located at the path specified by the {@link AssetKey key}.
         */
        GSLoader(final AssetKey key, final InputStream stream) {
            super(key, stream);
        }

        /**
         * Creates a {@link OGLShader geometry shader asset} by extracting a
         * String from the {@link InputStream stream} provided during the
         * construction of this {@link AssetTask task} and using it as the
         * source code to construct the {@link OGLShader geometry shader asset}.
         *
         * @return the {@link OGLShader geometry shader} constructed using the
         * String extracted from the {@link InputStream input stream} associated
         * with this loader as the shader source code.
         */
        @Override
        public OGLShader load() {
            return new OGLShader(key, OGLShader.Type.Geometry, new java.util.Scanner(input).useDelimiter("\\A").next());
        }
    }

    /**
     * Compute Shader Loader - (C) Cybertekt Software.
     *
     * {@link AssetTask Task} that constructs a {@link OGLShader comp shader} by
     * extracting valid GLSL source code from an input stream of the file
     * located at the path specified by an {@link AssetKey key}.
     */
    private class CSLoader extends AssetTask {

        /**
         * Constructs a new shader loader for an {@link AssetKey asset key} and
         * its corresponding {@link InputStream input stream}.
         *
         * @param key an {@link AssetKey asset key} that points to the location
         * of a valid compute shader (.comp) written in GLSL.
         * @param stream the {@link InputStream input stream} for the file
         * located at the path specified by the {@link AssetKey key}.
         */
        CSLoader(final AssetKey key, final InputStream stream) {
            super(key, stream);
        }

        /**
         * Creates a {@link OGLShader compute shader asset} by extracting a
         * String from the {@link InputStream stream} provided during the
         * construction of this {@link AssetTask task} and using it as the
         * source code for constructing an {@link OGLShader compute shader asset}.
         *
         * @return the {@link OGLShader compute shader} constructed using the
         * String extracted from the {@link InputStream input stream} associated
         * with this loader as the shader source code.
         */
        @Override
        public OGLShader load() {
            return new OGLShader(key, OGLShader.Type.Compute, new java.util.Scanner(input).useDelimiter("\\A").next());
        }
    }
}
