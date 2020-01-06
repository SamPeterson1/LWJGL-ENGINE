package lights;

import math.Vector3f;

public class PointLight {
	
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation;
	private boolean enabled = true;
	
	public PointLight(Vector3f position, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void toggle() {
		this.enabled = !this.enabled;
	}
	
	public boolean enabled() {
		return this.enabled;
	}
	
	public Vector3f getAttenuation() {
		return this.attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
}

