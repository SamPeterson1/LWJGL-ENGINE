package math;

public class Vector2f {
	
	private float x;
	private float y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}
	
	public float dotProduct(Vector2f other) {
		return this.getX()*other.getX() + this.getY()*other.getY();
	}
	
	public void subtract(Vector2f other) {
		this.x -= other.getX();
		this.y -= other.getY();
	}
	
	public void add(Vector2f other) {
		this.x += other.getX();
		this.y += other.getY();
	}
	
	public void multiply(Vector2f other) {
		this.x *= other.getX();
		this.y *= other.getY();
	}
	
	public void addX(float dx) {
		this.x += dx;
	}
	
	public void addY(float dy) {
		this.y += dy;
	}
	
	public void subtractX(float dx) {
		this.x -= dx;
	}
	
	public void subtractY(float dy) {
		this.y -= dy;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	 
	public float[] getFloats() {
		return new float[] {this.x, this.y};
	}
}
