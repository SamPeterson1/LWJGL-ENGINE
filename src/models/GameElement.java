package models;

import math.Matrix;
import math.Transform;

public class GameElement {
	
	private Mesh mesh;
	private Transform transform;
	
	public GameElement(Mesh mesh) {
		this.mesh = mesh;
		this.transform = new Transform();
	}
	
	public GameElement() {
		this.mesh = null;
		this.transform = new Transform();
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public void update() {

	}
	
	public Matrix getTransformationMatrix() {
		return this.transform.getTransform();
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public boolean isTextured() {
		return this.mesh instanceof TexturedMesh;
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
	
	public Model getModel() {
		return this.mesh.getModel();
	}
	
}
