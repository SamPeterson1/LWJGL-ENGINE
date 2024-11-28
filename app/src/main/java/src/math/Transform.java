package math;

import java.util.ArrayList;
import java.util.List;

public class Transform {
	
	private Vector3f pos = new Vector3f();
	private Vector3f rotation = new Vector3f();
	private Vector3f scale = new Vector3f(1f, 1f, 1f);
	private boolean updated = false;
	
	public Transform() {
	}

	public Vector3f getScale() {
		return this.scale;
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
		updated = true;
	}
	
	public Vector3f getPos() {
		return this.pos;
	}
	
	public void setScaleX(float scaleX) {
		this.scale.setX(scaleX);
		updated = true;
	}
	
	public void setScaleY(float scaleY) {
		this.scale.setY(scaleY);
		updated = true;
	}
	
	public void setScaleZ(float scaleZ) {
		this.scale.setZ(scaleZ);
		updated = true;
	}
	
	public void scaleX(float scaleX) {
		this.scale.setX(this.scale.getX() * scaleX);
		updated = true;
	}
	
	public void scaleY(float scaleY) {
		this.scale.setY(this.scale.getY() * scaleY);
		updated = true;
	}
	
	public void scaleZ(float scaleZ) {
		this.scale.setZ(this.scale.getZ() * scaleZ);
		updated = true;
	}
	
	public void translateX(float dist) {
		this.pos.addX(dist);
		updated = true;
	}
	
	public void translateY(float dist) {
		this.pos.addY(dist);
		updated = true;
	}
	
	public void translateZ(float dist) {
		this.pos.addZ(dist);
		updated = true;
	}
	
	public void setTranslationX(float pos) {
		this.pos.setX(pos);
		updated = true;
	}
	
	public void setTranslationY(float pos) {
		this.pos.setY(pos);
		updated = true;
	}
	
	public void setTranslationZ(float pos) {
		this.pos.setZ(pos);
		updated = true;
	}
	
	public void setTranslation(Vector3f pos) {
		this.pos = pos;
		updated = true;
	}
	
	public Vector3f getRotation() {
		return this.rotation;
	}
	
	public void rotateX(float deg) {
		this.rotation.addX(deg);
		updated = true;
	}
	
	public void rotateY(float deg) {
		this.rotation.addY(deg);
		updated = true;
	}
	
	public void rotateZ(float deg) {
		this.rotation.addZ(deg);
		updated = true;
	}
	
	public void setRotationX(float deg) {
		this.rotation.setX(deg);
		updated = true;
	}
	
	public void setRotationY(float deg) {
		this.rotation.setY(deg);
		updated = true;
	}
	
	public void setRotationZ(float deg) {
		this.rotation.setZ(deg);
		updated = true;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
		updated = true;
	}
	
	public boolean checkForUpdates() {
		if(this.updated) {
			updated = false;
			return true;
		}
		return false;
	}
	
	public Matrix4f xRotation(int i) {
		float rad = (float)Math.toRadians(i*this.rotation.getX());
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		return new Matrix4f(
				1,   0,   0,  0,
				0,  cos, sin, 0,
				0, -sin, cos, 0,
				0,   0,   0,  1
		);
	}
	
	public Matrix4f yRotation(int i) {
		float rad = (float)Math.toRadians(i*this.rotation.getY());
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		return new Matrix4f(
				cos, 0, -sin, 0,
				0,   1,   0,  0,
				sin, 0, cos,  0,
				0,   0,   0,  1
		);
	}
	
	public static Matrix4f xRotation(float deg) {
		float rad = (float)Math.toRadians(deg);
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		return new Matrix4f(
				1,   0,   0,  0,
				0,  cos, sin, 0,
				0, -sin, cos, 0,
				0,   0,   0,  1
		);
	}
	
	public Matrix4f zRotation(float deg) {
		float rad = (float)Math.toRadians(deg);
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		return new Matrix4f(
				cos,  sin, 0,   0,
				-sin, cos, 0,   0,
				0,    0,   1,   0,
				0,    0,   0,   1
		);
	}
	
	public static Matrix4f yRotation(float deg) {
		float rad = (float)Math.toRadians(deg);
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		return new Matrix4f(
				cos, 0, -sin, 0,
				0,   1,   0,  0,
				sin, 0, cos,  0,
				0,   0,   0,  1
		);
	}
	
	public Matrix4f zRotation(int i) {
		float rad = (float)Math.toRadians(i*this.rotation.getZ());
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		return new Matrix4f(
				cos,  sin, 0,   0,
				-sin, cos, 0,   0,
				0,    0,   1,   0,
				0,    0,   0,   1
		);
	}
	
	public Matrix4f calculateRotation(boolean inv) {
		
		int i = inv ? -1 : 1;
		
		Matrix4f rotationMatrix = Matrix4f.getIdentity();
		rotationMatrix.multiply(this.xRotation(i));
		rotationMatrix.multiply(this.yRotation(i));
		rotationMatrix.multiply(this.zRotation(i));
		
		return rotationMatrix;
		
	}
	
	public static Matrix4f translation(Vector3f translation) {
		return new Matrix4f(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				translation.getX(), translation.getY(), translation.getZ(), 1
		);	
	}
	
	public Matrix4f calculateTranslation(boolean inv) {
		int i = inv ? -1 : 1;
		return new Matrix4f(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				i*this.pos.getX(), i*this.pos.getY(), i*this.pos.getZ(), 1
		);			
	}
	
	public Matrix4f calculateScale() {
		return new Matrix4f(
				this.scale.getX(), 0, 0, 0,
				0, this.scale.getY(), 0, 0,
				0, 0, this.scale.getZ(), 0,
				0, 0, 0,                   1
		);
	}
	
	public Matrix4f getTransposedTransform(Matrix4f viewMatrix) {
		Matrix4f retVal = this.calculateTranslation(false);
		retVal.multiply(this.calculateRotation(false))
		.multiply(this.calculateScale());
		retVal.transpose3x3(viewMatrix);
		return retVal;
	}
	
	public Matrix4f getTransform() {
		
		return this.calculateTranslation(false)
		.multiply(this.calculateRotation(false))
		.multiply(this.calculateScale());
		
	}
	
}
