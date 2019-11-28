package models;

import math.Transform;

public class Entity {
	
	private Transform transform;
	private Mesh mesh;
	
	public Entity(Mesh mesh) {
		this.transform = new Transform();
		this.mesh = mesh;
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
	
	public int getMeshType() {
		return this.mesh.getType();
	}
	
	public void update() {
		this.mesh.update();
	}
	
}
