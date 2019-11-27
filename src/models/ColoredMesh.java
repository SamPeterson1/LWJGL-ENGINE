package models;

import files.OBJFile;
import math.Vector3f;

public class ColoredMesh implements Mesh {
	
	private Model model;
	private Vector3f color;
	private float shineDamper = 10;
	private float reflectivity = 1f;
	
	public ColoredMesh(String objFilePath, Vector3f color) {
		
		OBJFile objFile = new OBJFile(objFilePath);
		this.model = objFile.read();
		this.color = color;
		
	}
	
	@Override
	public Model getModel() {
		return this.model;
	}
	
	public Vector3f getColor() {
		return this.color;
	}

	@Override
	public int getType() {
		return Mesh.UNTEXTURED;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

}
