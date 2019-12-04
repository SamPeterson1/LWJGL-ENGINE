package models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelLoader {
	
	protected static int createVAO() {
        int vertexArrayID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayID);
        return vertexArrayID;
    }
	
    protected static int storeData(int attributeNumber, int coordSize, float[] data, boolean isStatic) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, isStatic ? GL15.GL_STATIC_DRAW : GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
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