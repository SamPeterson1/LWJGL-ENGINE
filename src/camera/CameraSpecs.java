package camera;

public class CameraSpecs {

	private float fov;
	private float aspect;
	private float diffZ;
	private float sumZ;
	private float zFar;
	private float zNear;
	
	private void recalculate() {
		this.diffZ = this.zNear - this.zFar;
		this.sumZ = this.zFar + this.zNear;
	}
	
	public float getFov() {
		return fov;
	}
	
	public void setFov(float fov) {
		this.fov = fov;
	}
	
	public float getzFar() {
		return zFar;
	}
	
	public void setzFar(float zFar) {
		this.zFar = zFar;
		recalculate();
	}
	
	public float getzNear() {
		return zNear;
	}
	
	public void setzNear(float zNear) {
		this.zNear = zNear;
		recalculate();
	}
	
	public float getDiffZ() {
		return diffZ;
	}
	
	public float getSumZ() {
		return sumZ;
	}
	
	public void setAspect(float aspect) {
		this.aspect = aspect;
	}
	
	public float getAspect() {
		return this.aspect;
	}
	
}
