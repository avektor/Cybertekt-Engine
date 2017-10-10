package net.cybertekt.render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * OpenGL Renderer - (C) Cybertekt Software
 * 
 * @author Andrew Vektor
 * @version 1.0.0
 * @since 1.0.0
 */
public class OGLRenderer implements Renderer {
    
    @Override
    public final void render() {
        glClearColor(0.25f, 0.5f, 0.5f, 1f);
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
}
