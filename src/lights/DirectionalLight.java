package lights;

import camera.CameraSpecs;
import math.Matrix;
import math.Transform;
import math.Vector3f;

public class DirectionalLight {
	
	private Transform t;
	private Vector3f lightColor;
	private CameraSpecs camSpecs;
	
	public DirectionalLight(Vector3f pos, Vector3f rotation, Vector3f lightColor) {
		
		this.t = new Transform();
		this.t.setTranslation(pos);
		this.t.setRotation(rotation);
		
		this.camSpecs = new CameraSpecs();
		this.camSpecs.setAspect(1440f/1080f);
		this.camSpecs.setFov(70f);
		this.camSpecs.setzFar(1000f);
		this.camSpecs.setzNear(0.01f);
		
	}
	
	public Transform getTransform() {
		return this.t;
	}
	
	public Vector3f getColor() {
		return this.lightColor;
	}
	
	public Matrix lightViewMatrix() {
		return Matrix.viewMatrix(t);
	}
	
	public Matrix perspective() {
		return Matrix.perspective(camSpecs);
	}
	
}
