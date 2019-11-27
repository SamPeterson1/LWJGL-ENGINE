package models;

import files.OBJFile;
import rendering.Material;
import rendering.Texture;

public class TexturedMesh extends Mesh {
	
	public TexturedMesh(String objFilePath, String textureFilePath) {
		
		super(Mesh.TEXTURED);
		
		OBJFile objFile = new OBJFile(objFilePath);
		super.setModel(objFile.read());
		Material material = new Material();
		material.setTexture(new Texture(textureFilePath));
		super.setMaterial(material);
	}
	
	public Texture getTexture() {
		return this.material.getTexture();
	}
	
}
