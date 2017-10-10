 package net.cybertekt.asset;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import net.cybertekt.app.BasicApplication;
import net.cybertekt.asset.shader.OGLShader;
import net.cybertekt.display.Display;
import net.cybertekt.display.input.Input;
import net.cybertekt.display.input.InputMapping;
import net.cybertekt.exception.OGLException;
import net.cybertekt.render.OGLShaderProgram;
import net.cybertekt.render.Renderer;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import org.lwjgl.system.MemoryUtil;

/**
 * Shading System Unit Test - (C) Cybertekt Software
 *
 * Demonstrates and tests the capabilities of the Cybertekt Engine shading
 * system.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class ShaderTest extends BasicApplication implements Renderer {
    
    /**
     * 
     */
    private final Map<String, OGLShaderProgram> shaders = new HashMap<>();
    
    private int vertexArrayId;
    
    private int vertexBufferId;
    
    private final float[] vertices = new float[] {
        0.0f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f
    };

    public static void main(final String[] args) {
        ShaderTest app = new ShaderTest();
        app.initialize();
    }

    public ShaderTest() {
        super("Shader Test");
    }

    @Override
    public void init() {
        display.setRenderer(this);
        display.addInputMapping("print", new InputMapping(Input.Key.P).when(Input.State.Pressed));
        
        long time = System.nanoTime();
        
        
        try {
            /* Load OpenGL Shader Programs */
            shaders.put("Solid", (new OGLShaderProgram("Solid", AssetManager.get(OGLShader.class, "Shaders/solid.vert", "Shaders/solid.frag"))));
            shaders.put("Textured", (new OGLShaderProgram("Textured", AssetManager.get(OGLShader.class, "Shaders/textured.vert", "Shaders/textured.frag"))));
            shaders.put("Font", (new OGLShaderProgram("Font", AssetManager.get(OGLShader.class, "Shaders/font.vert", "Shaders/font.frag"))));
            
            
            /* Validate Shaders (For Debugging Purposes Only - Validation is not required for a shader to function) */
            //shaders.values().forEach((shader) -> { 
            //    shader.validate();
            //});
            
        } catch (OGLException e) {
            log.error(e.getMessage());
        }
        
        long after = System.nanoTime();
        
        /* Log the number and total time required to initialize the shader programs */
        log.info("{} shader(s) loaded in {}ms", shaders.size(), (after - time) / 1000000);
        
        
        /* Generate Vertex Array and Vertex Buffers */
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();
        
        vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);

        vertexBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(vertexArrayId);
        
        MemoryUtil.memFree(vertexBuffer);
    }
    
    @Override
    public final void update(final float tpf) {
        
    }
    
    /**
     * Renders a triangle to the screen.
     */
    @Override
    public final void render() {
        glClearColor(0.5f, 0f, 0.5f, 0f);
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        /* Bind the Shader Program to use for rendering. */
        glUseProgram(shaders.get("Solid").getId());
        
        /* Bind and enable the vertex array object. */
        glBindVertexArray(vertexArrayId);
        glEnableVertexAttribArray(0);
        
        /* Draw the vertices */
        glDrawArrays(GL_TRIANGLES, 0, 3);
        
        /* Restore the default state and unbind the shader program */
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        
        glUseProgram(0);
    }
    
    @Override
    public final void stop() {
        super.stop();
    }
    
    @Override
    public final void onInput(final Display display, final String mapping, final Input.State event, final float tpf) {
        super.onInput(display, mapping, event, tpf);
        if (mapping.equals("print")) {
            shaders.values().forEach((s) -> {
                log.info(s.toString());
            });
        }
    }

}
