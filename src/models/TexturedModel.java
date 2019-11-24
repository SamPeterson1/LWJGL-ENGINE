package models;

import org.lwjgl.opengl.GL30;

import math.Vector3f;
import rendering.Texture;

import org.lwjgl.opengl.GL15;

public class TexturedModel extends Model {
    private int vertexArrayID, vertexBufferID, textureCoordsBufferID, indicesBufferID, vertexCount;
    private Texture material;
    private Vector3f color;
    
    public TexturedModel(float[] vertices, float[] textureCoords, int[] indices, String file) {
    	vertexArrayID = super.createVAO();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
        textureCoordsBufferID = super.storeData(1, 2, textureCoords);
        
		vertexCount = indices.length;
        GL30.glBindVertexArray(0);
        material = new Texture(file);
    }
    
    public void remove() {
    	GL30.glDeleteVertexArrays(vertexArrayID);
    	GL15.glDeleteBuffers(vertexBufferID);
    	GL15.glDeleteBuffers(textureCoordsBufferID);
    	GL15.glDeleteBuffers(indicesBufferID);
    	material.remove();
    }
 
    @Override
    public int getVAO_ID() {
        return vertexArrayID;
    }
 
    public int getVertexCt() {
        return vertexCount;
    }

	public Texture getMaterial() {
		return material;
	}
}