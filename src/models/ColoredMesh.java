package models;

import files.OBJFile;
import math.Vector3f;
import rendering.Material;

public class ColoredMesh extends Mesh {
	
	public ColoredMesh(String objFilePath, Vector3f color) {
		
		super(Mesh.UNTEXTURED);
		
		OBJFile objFile = new OBJFile(objFilePath);
		super.setModel(objFile.read());
		Material material = new Material();
		material.setColor(color);
		super.setMaterial(material);
		
	}
	
	public void setReflectivity(float reflectivity) {
		super.material.setReflectivity(reflectivity);
	}
	
	public void setShineDamping(float shineDamping) {
		super.material.setShineDamping(shineDamping);
	}
	
	
	public Vector3f getColor() {
		return this.material.getColor();
	}

	public float getShineDamper() {
		return this.material.getShineDamping();
	}

	public float getReflectivity() {
		return this.getShineDamper();
	}

}
