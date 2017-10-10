package net.cybertekt.asset.shader;

import net.cybertekt.asset.Asset;
import net.cybertekt.asset.AssetKey;
import net.cybertekt.exception.OGLException;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

/**
 * OGLShader - (C) Cybertekt Software
 *
 * An immutable GLSL shader {@link Asset asset} defined by the file located at a
 * path specified by the {@link AssetKey key} specified during construction.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class OGLShader extends Asset {

    /**
     * Wraps the LWJGL constants that identify each shader type.
     */
    public enum Type {
        /**
         * Vertex Shader Type.
         */
        Vertex(GL_VERTEX_SHADER),
        /**
         * Fragment Shader Type.
         */
        Fragment(GL_FRAGMENT_SHADER),
        /**
         * Geometry Shader Type.
         */
        Geometry(GL_GEOMETRY_SHADER),
        /**
         * Compute Shader Type.
         */
        Compute(GL_COMPUTE_SHADER);

        /**
         * Index that maps to each types corresponding LWJGL constant.
         */
        private final int id;

        /**
         * Constructs a new shader type with an integer constant that identifies
         * the shader type.
         *
         * @param id the value of the LWJGL constant that identifies the shader
         * type.
         */
        Type(final int id) {
            this.id = id;
        }

        /**
         * Returns the value of the LWJGL constant that identifies the shader
         * type.
         *
         * @return the value of the LWJGL constant that corresponds to this
         * shader type.
         */
        public final int getId() {
            return id;
        }
    }

    /**
     * Indicates the shader {@link Type type}.
     */
    private final Type type;

    /**
     * The GLSL source code for the shader.
     */
    private final String source;

    /**
     * The identifier assigned by LWJGL during construction.
     */
    private int id;

    /**
     * Constructs and compiles a new OpenGL shader asset of the specified
     * {@link Type type}
     *
     * @param key the {@link AssetKey key} that points to the location of the
     * GLSL shader file.
     * @param type the shader {@link Type type}.
     * @param source the GLSL source code of the shader.
     */
    public OGLShader(final AssetKey key, final Type type, final String source) {
        super(key);
        this.type = type;
        this.source = source;
    }

    public final int compile() throws OGLException {
        if (id == 0) {
            id = glCreateShader(type.getId());
        }
        
        glShaderSource(id, source);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
            throw new OGLException(getKey().getPath() + " compilation failed:\n\t" + glGetShaderInfoLog(id));
        }
        return id;
    }
    
    public final void delete() {
        glDeleteShader(id);
        id = 0;
    }

    /**
     * Returns the ID number assigned to the shader during construction by
     * LWJGL.
     *
     * @return the ID number assigned to the shader during construction.f
     */
    public final int getId() {
        return id;
    }

    /**
     * Returns the {@link Type type} of this shader.
     *
     * @return the {@link Type} of this shader.
     */
    public final Type getType() {
        return type;
    }

    /**
     * Overridden to return a string containing the name and ID of the shader.
     *
     * @return a string containing the name and ID of the shader.
     */
    @Override
    public final String toString() {
        return getKey().getName(false) + " shader " + getId();
    }

}
