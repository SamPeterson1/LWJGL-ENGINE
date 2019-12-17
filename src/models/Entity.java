package models;

import math.Transform;

public class Entity {
	
	protected Transform transform;
	protected Mesh mesh;
	protected boolean enabled = true;
	
	public Entity(Mesh mesh) {
		this.transform = new Transform();
		this.mesh = mesh;
	}
	
	public Entity() {
		this(null);
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void disable() {
		this.enabled = false;
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
