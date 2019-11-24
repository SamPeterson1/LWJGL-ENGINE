package models;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import math.Vector3f;
import rendering.Texture;

public class Mesh extends Model{

	private int vertexArrayID, vertexBufferID, normalsBufferID, textureCoordsBufferID, indicesBufferID, vertexCount;
    private Texture material = null;
    private Vector3f color = new Vector3f();
    private boolean textured;
    
    public Mesh(float[] vertices, float[] textureCoords, float[] normals, int[] indices, String file) {
    	vertexArrayID = super.createVAO();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
        textureCoordsBufferID = super.storeData(1, 2, textureCoords);
        normalsBufferID = super.storeData(2, 3, normals);
		vertexCount = indices.length;
        GL30.glBindVertexArray(0);
        material = new Texture(file);
    }
    
    public Mesh(float[] vertices, float[] textureCoords, int[] indices, float[] normals) {
    	vertexArrayID = super.createVAO();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
        textureCoordsBufferID = super.storeData(1, 2, textureCoords);
		normalsBufferID = super.storeData(2, 3, normals);
        vertexCount = indices.length;
        GL30.glBindVertexArray(0);
        //material = new Texture(file);
    }
    
    public Mesh(float[] vertices, int[] indices, Vector3f color) {
    	vertexArrayID = super.createVAO();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
        vertexCount = indices.length;
        GL30.glBindVertexArray(0);
        this.color = color;
        this.textured = false;
    }

    public void setColor(Vector3f color) {
    	this.color = color;
    }
    
    public Vector3f getColor() {
    	return this.color;
    }
    
    public boolean isTextured() {
    	return this.textured;
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
