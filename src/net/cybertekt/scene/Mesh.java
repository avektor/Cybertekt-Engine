package net.cybertekt.scene;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glDeleteVertexArrays;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.lwjgl.system.MemoryUtil.memAllocInt;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * Mesh - (C) Cybertekt Software
 *
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Vektor
 */
public class Mesh {

    /* Vertex Array Object Identifier */
    private final int vertexArrayObject;

    /* Vertex Buffer Object Identifier */
    private final int positionBufferId;
    
    /* Vertex Color Buffer Object Identifier */
    private final int colorBufferId;

    /* Vertex Indices Buffer Object Identifier */
    private final int indexBufferId;

    /* Number of total vertices in the mesh */
    private final int vertexCount;

    public Mesh(float[] vertices, float[] colors, int[] indices) {
        
        vertexCount = indices.length;

        vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);

        /* Create Vertex Position Buffer */
        positionBufferId = glGenBuffers();
        FloatBuffer vertexBuffer = memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, positionBufferId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        memFree(vertexBuffer);

        /* Create Vertex Color Buffer */
        colorBufferId = glGenBuffers();
        FloatBuffer colorBuffer = memAllocFloat(colors.length);
        colorBuffer.put(colors).flip();
        glBindBuffer(GL_ARRAY_BUFFER, colorBufferId);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        memFree(colorBuffer);
        
        /* Create Vertex Index Buffer */
        indexBufferId = glGenBuffers();
        IntBuffer indexBuffer = memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        memFree(indexBuffer);
    }

    /**
     * Returns the vertex array object identifier.
     *
     * @return the vertex array object identifier.
     */
    public int getVertexArrayId() {
        return vertexArrayObject;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void destroy() {
        /* Delete Buffers */
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(positionBufferId);
        glDeleteBuffers(colorBufferId);
        glDeleteBuffers(indexBufferId);
        
        /* Delete Vertex Array Object */
        glBindVertexArray(0);
        glDeleteVertexArrays(vertexArrayObject);
    }
}
