package camera;

import events.EventHandler;
import math.Matrix4f;
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
	
	public void setPitch(float pitch) {
		this.transform.setRotationX(pitch);
	}
	
	public void setYaw(float yaw) {
		this.transform.setRotationY(yaw);
	}
	
	public void setRoll(float roll) {
		this.transform.setRotationZ(roll);
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
	
	public Matrix4f perspective() {
        return Matrix4f.perspective(this.camSpecs);
    }
	
	public Matrix4f viewMatrix() {
		return Matrix4f.viewMatrix(this.transform);
	}
	
}
