package rendering;

import math.Vector3f;
import math.Vector4f;

public class Material {
	
	Vector4f color = null;
	Texture texture = null;
	boolean castsShadow = false;
	boolean recievesShadow = false;
	float reflectivity = 0;
	float shineDamping = 0;
	
	public Material(Vector3f color, Texture texture, float reflectivity, float shineDamping) {
		this.color = new Vector4f(color, 1.0f);
		this.texture = texture;
		this.reflectivity = reflectivity;
		this.shineDamping = shineDamping;
	}

	public Material() {}

	public boolean castsShadow() {
		return this.castsShadow;
	}
	
	public boolean recievesShadow() {
		return this.recievesShadow;
	}
	
	public void setCastsShadow(boolean castsShadow) {
		this.castsShadow = castsShadow;
	}
	
	public void setRecievesShadow(boolean recievesShadow) {
		this.recievesShadow = recievesShadow;
	}
	
	public boolean isTextured() {
		return this.texture != null;
	}
	
	public Vector4f getColor() {
		return color;
	}
	
	public Vector3f getRGB() {
		return new Vector3f(this.color.getX(), this.color.getY(), this.color.getZ());
	}
	
	public void setColor(Vector3f color) {
		this.color = new Vector4f(color, 1.0f);
	}
	
	public void setColor(Vector4f color) {
		this.color = color;
		System.out.println(this.color.getW() + "YAAAAAASNEMOIASNMFOIUNWA_F(WEAF_(UEWA");
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
