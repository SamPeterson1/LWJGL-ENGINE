package models;

import files.OBJFile;
import rendering.Texture;

public class TexturedMesh implements Mesh {
	
	private Model model;
	private Texture texture;
	
	public TexturedMesh(String objFilePath, String textureFilePath) {
		
		this.texture = new Texture(textureFilePath);
		OBJFile objFile = new OBJFile(objFilePath);
		this.model = objFile.read();
		this.texture = new Texture(textureFilePath);
		
	}
	
	@Override
	public Model getModel() {
		return this.model;
	}
	
	public Texture getTexture() {
		return this.texture;
	}

	@Override
	public int getType() {
		return Mesh.TEXTURED;
	}
	
}
