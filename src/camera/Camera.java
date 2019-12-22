package camera;

import events.EventHandler;
import math.Matrix;
import math.Transform;
import math.Vector3f;
import terrain.Terrain;
public class Camera {
	
	private CameraSpecs camSpecs;
	private KeyboardCamController controller;
	private Transform transform;
	
	public Camera(CameraSpecs camSpecs) {
		this.camSpecs = camSpecs;
		this.transform = new Transform();
		EventHandler.disableCursor();
		this.controller = new KeyboardCamController();
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public void setTerrain(Terrain terrain) {
		controller.setTerrain(terrain);
	}
	
	public void setAspect(float aspect) {
		this.camSpecs.setAspect(aspect);
	}
	
	public Vector3f getPosition() {
		return this.transform.getPos();
	}
	
	public void update() {
		this.controller.update(this.transform);	
	}
	
	public Matrix perspective() {
        return Matrix.perspective(this.camSpecs);
    }
	
	public Matrix viewMatrix() {
		return Matrix.viewMatrix(this.transform);
	}
	
}
