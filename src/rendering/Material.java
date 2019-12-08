package rendering;

import math.Vector3f;

public class Material {
	
	Vector3f color = null;
	Texture texture = null;
	float reflectivity = 0;
	float shineDamping = 0;
	
	public Material(Vector3f color, Texture texture, float reflectivity, float shineDamping) {
		this.color = color;
		this.texture = texture;
		this.reflectivity = reflectivity;
		this.shineDamping = shineDamping;
	}

	public Material() {}

	public boolean isTextured() {
		return this.texture != null;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}
	
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	public float getShineDamping() {
		return shineDamping;
	}
	
	public void setShineDamping(float shineDamping) {
		this.shineDamping = shineDamping;
	}
		
}
