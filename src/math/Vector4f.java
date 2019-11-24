package math;

public class Vector4f {
	
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	public float dotProduct(Vector4f other) {
		return this.getX()*other.getX() + this.getY()*other.getY() + this.getZ() * other.getZ() + this.getW() * other.getW();
	}
	
	public void subtract(Vector4f other) {
		this.x -= other.getX();
		this.y -= other.getY();
		this.z -= other.getZ();
		this.w -= other.getW();
	}
	
	public void add(Vector4f other) {
		this.x += other.getX();
		this.y += other.getY();
		this.z += other.getZ();
		this.w += other.getW();
	}
	
	public void multiply(Vector4f other) {
		this.x *= other.getX();
		this.y *= other.getY();
		this.z *= other.getZ();
		this.w *= other.getW();
	}
	
	public void addX(float dx) {
		this.x += dx;
	}
	
	public void addY(float dy) {
		this.y += dy;
	}
	
	public void addZ(float dz) {
		this.z += dz;
	}

	public void addW(float dw) {
		this.w += dw;
	}
	
	public void subtractX(float dx) {
		this.x -= dx;
	}
	
	public void subtractY(float dy) {
		this.y -= dy;
	}
	
	public void subtractZ(float dz) {
		this.z -= dz;
	}
	
	public void subtractW(float dw) {
		this.w -= dw;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getW() {
		return this.w;
	}
	

	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public void setW(float w) {
		this.w = w;
	}
	 
	public float[] getFloats() {
		return new float[] {this.x, this.y, this.z, this.w};
	}
}
