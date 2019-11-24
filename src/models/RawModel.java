package models;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL15;

public class RawModel extends Model {
    private int vertexArrayID, vertexBufferID, indicesBufferID, vertexCount;
    
    public RawModel(float[] vertices, int[] indices) {
    	vertexArrayID = super.createVAO();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
		vertexCount = indices.length;
        GL30.glBindVertexArray(0);
    }
    
    public void remove() {
    	GL30.glDeleteVertexArrays(vertexArrayID);
    	GL15.glDeleteBuffers(vertexBufferID);
    	GL15.glDeleteBuffers(indicesBufferID);
    }
 
    public int getVAO_ID() {
        return vertexArrayID;
    }
 
    public int getVertexCt() {
        return vertexCount;
    }
}