package GUI;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import math.Vector3f;
import models.Model;
import rendering.Texture;

public class TextMesh extends Model {

	private int VBO_ID, IBO_ID, VAO_ID, textureCoordsBufferID;
	private int vertexCount;
	private Texture material;
	private Vector3f color;
	
	public TextMesh(float[] vertices, float[] textCoords, int[] indices, Vector3f color, String path) {
		
		this.VAO_ID = super.createVAO();
		this.IBO_ID = super.bindIndicesBuffer(indices);
		this.VBO_ID = super.storeData(0, 3, vertices);
		this.textureCoordsBufferID = super.storeData(1, 2, textCoords);
		this.material = new Texture("/assets/TestFont.png");
		this.vertexCount = indices.length;
		this.color = color;
        GL30.glBindVertexArray(0);
	}
	
	public Vector3f getColor() {
		return this.color;
	}
	
	public int getTextureID() {
		return this.material.getID();
	}
	
	public int getVertexCount() {
		return this.vertexCount;
	}
	
	public void remove() {
    	GL30.glDeleteVertexArrays(this.VAO_ID);
    	GL15.glDeleteBuffers(this.VBO_ID);
    	GL15.glDeleteBuffers(textureCoordsBufferID);
    	GL15.glDeleteBuffers(this.IBO_ID);
    	material.remove();
    }
	
	@Override
	public int getVAO_ID() {
		return this.VAO_ID;
	}

}
