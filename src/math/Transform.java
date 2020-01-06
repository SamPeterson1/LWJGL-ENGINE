package math;

public class Transform {
	
	private Vector3f pos = new Vector3f();
	private Vector3f rotation = new Vector3f();
	private Vector3f scale = new Vector3f(1f, 1f, 1f);

	public Vector3f getScale() {
		return this.scale;
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public Vector3f getPos() {
		return this.pos;
	}
	
	public void setScaleX(float scaleX) {
		this.scale.setX(scaleX);
	}
	
	public void setScaleY(float scaleY) {
		this.scale.setY(scaleY);
	}
	
	public void setScaleZ(float scaleZ) {
		this.scale.setZ(scaleZ);
	}
	
	public void scaleX(float scaleX) {
		this.scale.setX(this.scale.getX() * scaleX);
	}
	
	public void scaleY(float scaleY) {
		this.scale.setY(this.scale.getY() * scaleY);
	}
	
	public void scaleZ(float scaleZ) {
		this.scale.setZ(this.scale.getZ() * scaleZ);
	}
	
	public void translateX(float dist) {
		this.pos.addX(dist);
	}
	
	public void translateY(float dist) {
		this.pos.addY(dist);
	}
	
	public void translateZ(float dist) {
		this.pos.addZ(dist);
	}
	
	public void setTranslationX(float pos) {
		this.pos.setX(pos);
	}
	
	public void setTranslationY(float pos) {
		this.pos.setY(pos);
	}
	
	public void setTranslationZ(float pos) {
		this.pos.setZ(pos);
	}
	
	public void setTranslation(Vector3f pos) {
		this.pos = pos;
	}
	
	public Vector3f getRotation() {
		return this.rotation;
	}
	
	public void rotateX(float deg) {
		this.rotation.addX(deg);
	}
	
	public void rotateY(float deg) {
		this.rotation.addY(deg);
	}
	
	public void rotateZ(float deg) {
		this.rotation.addZ(deg);
	}
	
	public void setRotationX(float deg) {
		this.rotation.setX(deg);
	}
	
	public void setRotationY(float deg) {
		this.rotation.setY(deg);
	}
	
	public void setRotationZ(float deg) {
		this.rotation.setZ(deg);
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
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
		int j = 1;
		if(i == -1) j = 0;
		float rad = (float)Math.toRadians(j*i*this.rotation.getZ());
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
