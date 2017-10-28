 package net.cybertekt.render;

import net.cybertekt.asset.*;
import java.util.HashMap;
import java.util.Map;
import net.cybertekt.app.BasicApplication;
import net.cybertekt.asset.shader.OGLShader;
import net.cybertekt.display.Display;
import net.cybertekt.exception.OGLException;
import net.cybertekt.mesh.Mesh;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;

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
 
public class RenderTest extends BasicApplication implements Renderer {
    
    /**
     * 
     */
    private final Map<String, OGLShaderProgram> shaders = new HashMap<>();
    
    private final float[] vertices = new float[] {
        -0.5f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.5f, 0.5f, 0.0f
    };
    
    private final float[] colors = new float[] {
        0.5f, 0.0f, 0.0f,
        0.0f, 0.5f, 0.0f,
        0.0f, 0.0f, 0.5f,
        0.0f, 0.5f, 0.5f
    };
    
    private final int[] indices = new int[] {
        0, 1, 3, 3, 1, 2
    };
    
    private Mesh quad;

    public static void main(final String[] args) {
        RenderTest app = new RenderTest();
        app.initialize();
    }

    public RenderTest() {
        super("Render Unit Test");
    }

    @Override
    public void init() {
        display.setRenderer(this);
        //display.addInputMapping("print", new InputMapping(Input.Key.P).when(Input.State.Pressed));
        
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
        quad = new Mesh(vertices, colors, indices);
    }
    
    @Override
    public final void update(final float tpf) {
        
    }
    
    /**
     * Renders a triangle to the screen.
     */
    @Override
    public final void render() {
        
        /* Clear The Display */
        glClearColor(0.0f, 0f, 0.0f, 1f);
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        /* Bind the Shader */
        glUseProgram(shaders.get("Solid").getId());
        
        /* Bind The Vertex Attribute Array */
        glBindVertexArray(quad.getVertexArrayId());
        
        /* Enable Vertex Position and Color Attribute Arrays */
        glEnableVertexAttribArray(0); //Vertex Position Attribute In Solid Shader.
        glEnableVertexAttribArray(1); //Vertex Color Attribute In Solid Shader.
        
        /* Render The Quad */
        glDrawElements(GL_TRIANGLES, quad.getVertexCount(), GL_UNSIGNED_INT, 0);
        
        /* Disable Vertex Position and Color Attribute Arrays */
        glDisableVertexAttribArray(0); //Vertex Position Attribute In Solid Shader.
        glDisableVertexAttribArray(1); //Vertex Color Attribute In Solid Shader.
        
        /* Unbind The Vertex Attribute Array */
        glBindVertexArray(0);
        
        /* Unbind The Shader Program */
        glUseProgram(0);
    }
    
    @Override
    public final void destroy() {
        quad.destroy();
    }
    
    @Override
    public final void stop() {
        super.stop();
    }
    
    @Override
    public final void onInput(final Display display, final String mapping, final float tpf) {
        super.onInput(display, mapping, tpf);
        if (mapping.equals("print")) {
            shaders.values().forEach((s) -> {
                log.info(s.toString());
            });
        }
    }
}
