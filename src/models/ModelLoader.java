package models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

import particles.TestParticleRenderer;

public class ModelLoader {
	
	protected static int createVAO() {
        int vertexArrayID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayID);
        return vertexArrayID;
    }
	
	protected static int createEmptyVBO(int floatCount) {
	
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vbo;
		
	}
	
	protected static void addInstancedAttribute(int vao, int vbo, int attribute, int datasize,
			int instancedDataLength, int offset) {
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attribute, datasize, GL11.GL_FLOAT, false, instancedDataLength * 4, offset * 4);
		GL33.glVertexAttribDivisor(attribute, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
	}
	
	protected static int storeData(int attributeNumber, int coordSize, float[] data, boolean isStatic, int stride, int offset) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, isStatic ? GL15.GL_STATIC_DRAW : GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, stride, offset);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
	}
	
    protected static int storeData(int attributeNumber, int coordSize, float[] data, boolean isStatic) {
    	return storeData(attributeNumber, coordSize, data, isStatic, 0, 0);
    }
     
    protected static int bindIndicesBuffer(int[] indices, boolean isStatic) {
    	IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        int indicesBufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, isStatic ? GL15.GL_STATIC_DRAW : GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return indicesBufferID;
    }
    
    public static RawModel getInstancedParticles(float[] vertices, int[] indices) {
    	
    	RawModel model = new RawModel();
    	model.setVAO_ID(createVAO());
    	int vbo = storeData(0, 2, vertices, true);
    	model.setBuffer(vbo, RawModel.VBO);
    	model.setBuffer(bindIndicesBuffer(indices, true), RawModel.IBO);
    	model.setVertexCount(indices.length);
    	 
    	model.addVBO(createEmptyVBO(TestParticleRenderer.MAX_PARTICLES*TestParticleRenderer.FLOATS_PER_PARTICLE));
    	vbo = model.getVBO(0);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 1, 4, 22, 0);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 2, 4, 22, 4);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 3, 4, 22, 8);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 4, 4, 22, 12);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 5, 4, 22, 16);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 6, 1, 22, 20);
    	addInstancedAttribute(model.getVAO_ID(), vbo, 7, 1, 22, 21);
    	
    	GL30.glBindVertexArray(0);
    	return model;
    	
    }
    
    public static RawModel load3DModel(float[] vertices) {
    	
    	RawModel model = new RawModel();
    	model.setVAO_ID(createVAO());
    	model.setBuffer(storeData(0, 3, vertices, true), RawModel.VBO);
    	model.setVertexCount(vertices.length/3);
    	GL30.glBindVertexArray(0);
    	
    	return model;
    }
    
    public static RawModel load3DModel(float[] vertices, float[] textureCoords, float[] vertexNormals, int[] indices) {
    	
    	RawModel model = new RawModel();
    	model.setVAO_ID(createVAO());
    	model.setBuffer(bindIndicesBuffer(indices, true), RawModel.IBO);
    	model.setBuffer(storeData(0, 3, vertices, true), RawModel.VBO);
    	model.setBuffer(storeData(1, 3, vertexNormals, true), RawModel.NBO);
    	model.setBuffer(storeData(2, 2, textureCoords, true), RawModel.TBO);
    	model.setVertexCount(indices.length);
    	GL30.glBindVertexArray(0);
    	
    	return model;
    	
    }
    
    public static RawModel load3DModel(float[] vertices, float[] vertexNormals, int[] indices) {
    	
    	RawModel model = new RawModel();
    	model.setVAO_ID(createVAO());
    	model.setBuffer(bindIndicesBuffer(indices, true), RawModel.IBO);
    	model.setBuffer(storeData(0, 3, vertices, true), RawModel.VBO);
    	model.setBuffer(storeData(1, 3, vertexNormals, true), RawModel.NBO);
    	model.setVertexCount(indices.length);
    	GL30.glBindVertexArray(0);
    	
    	return model;
    	
    }
    
    public static RawModel load2DModel(float[] vertices, float[] textureCoords, int[] indices, boolean isStatic) {
    	
    	RawModel model = new RawModel();
    	model.setVAO_ID(createVAO());
    	model.setBuffer(bindIndicesBuffer(indices, isStatic), RawModel.IBO);
    	model.setBuffer(storeData(0, 2, vertices, isStatic), RawModel.VBO);
    	model.setBuffer(storeData(1, 2, textureCoords, isStatic), RawModel.TBO); 
    	model.setVertexCount(indices.length);
    	GL30.glBindVertexArray(0);
    	
    	return model;
    	
    }
    
    public static RawModel loadColoredGUIModel(float[] vertices, int[] indices) {
    	
    	RawModel model = new RawModel();
    	model.setVAO_ID(createVAO());
    	model.setBuffer(bindIndicesBuffer(indices, true), RawModel.IBO);
    	model.setBuffer(storeData(0, 2, vertices, true), RawModel.VBO);
    	model.setVertexCount(indices.length);
    	GL30.glBindVertexArray(0);
    	
    	return model;
    	
    }

}