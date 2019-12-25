package lights;

import camera.CameraSpecs;
import math.Matrix4f;
import math.Transform;
import math.Vector3f;

public class DirectionalLight {
	
	private Vector3f direction;
	private Vector3f lightColor;
	
	public DirectionalLight(Vector3f direction, Vector3f lightColor) {
		
		this.direction = direction;
		this.lightColor = lightColor;
		
	}
	
	public Vector3f getDirection() {
		return this.direction;
	}
	
	public Vector3f getColor() {
		return this.lightColor;
	}

	
}
