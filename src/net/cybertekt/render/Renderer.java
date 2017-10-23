package net.cybertekt.render;

/**
 * Renderer Interface - (C) Cybertekt Software
 *
 * @author Vektor
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Renderer {

    /**
     * Called on each application loop for rendering screen contents.
     */
    public void render();
    
    /**
     * Called when the renderer is shut down.
     */
    public void destroy();

    //public void setViewport(int x, int y, int width, int height);
}
