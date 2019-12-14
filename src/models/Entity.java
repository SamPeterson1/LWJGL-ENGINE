package models;

import math.Transform;
import particles.ParticleAnimation;

public class Entity {
	
	protected Transform transform;
	protected Mesh mesh;
	
	public Entity(Mesh mesh) {
		this.transform = new Transform();
		this.mesh = mesh;
	}
	
	public Entity() {
		this(null);
	}
	
	public void setMesh(Mesh mesh) {
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
