package models;

import math.Transform;
import particles.ParticleAnimation;

public class Entity {
	
	private Transform transform;
	private Mesh mesh;
	private ParticleAnimation animation;
	
	public Entity(Mesh mesh) {
		this.transform = new Transform();
		this.mesh = mesh;
	}
	
	public void setAnimation(ParticleAnimation animation) {
		this.animation = animation;
	}
	
	public ParticleAnimation getAnimation() {
		return this.animation;
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
