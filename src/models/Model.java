package models;

import java.util.HashMap;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Model {
	
	private HashMap<Integer, Integer> ids = new HashMap<>();
	private int vertexCount;
	
	public static final int VAO = 0;
	public static final int VBO = 1;
	public static final int IBO = 2;
	public static final int TBO = 3;
	public static final int NBO = 4;
	
	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
	
	public int getVertexCount() {
		return this.vertexCount;
	}
	
	public void setVAO_ID(int vaoID) {
		this.ids.put(Model.VAO, vaoID);
	}
	
	public int getVAO_ID() {
		return this.ids.get(Model.VAO);
	}
	
	public void setBuffer(int bufferID, int type) {
		this.ids.put(type, bufferID);
	}
	
	public void remove() {
		
		if(this.ids.containsKey(Model.VAO))
			GL30.glDeleteVertexArrays(this.ids.get(Model.VAO));
		
		this.deleteBuffer(Model.VBO);
		this.deleteBuffer(Model.TBO);
		this.deleteBuffer(Model.IBO);
		this.deleteBuffer(Model.NBO);
		
	}
	
	private void deleteBuffer(int type) {
		if(this.ids.containsKey(type))
			GL15.glDeleteBuffers(this.ids.get(type));
	}
	
}
