package models;

import files.OBJFile;
import rendering.Material;
import rendering.Texture;

public class TexturedMesh extends Mesh {
	
	private boolean cullFace = true;
	private boolean useFakelighting = false;
	
	public TexturedMesh(String objFilePath, String textureFilePath) {
		
		super(Mesh.TEXTURED);
		
		OBJFile objFile = new OBJFile(objFilePath);
		super.setModel(objFile.read());
		Material material = new Material();
		material.setTexture(new Texture(textureFilePath));
		super.setMaterial(material);
	}
	
	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakelighting = useFakeLighting;
	}
	
	public boolean usesFakeLighting() {
		return this.useFakelighting;
	}
	
	public void setCullFace(boolean cullFace) {
		this.cullFace = cullFace;
	}
	
	public boolean cullFace() {
		return this.cullFace;
	}
	
	public Texture getTexture() {
		return this.material.getTexture();
	}
	
}
