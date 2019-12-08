package camera;

import events.EventHandler;
import math.Matrix;
import math.Transform;
import math.Vector3f;
public class Camera {
	
	private CameraSpecs camSpecs;
	private CameraController controller;
	private Transform transform;
	
	public Camera(CameraSpecs camSpecs) {
		this.camSpecs = camSpecs;
		this.transform = new Transform();
		EventHandler.disableCursor();
		this.controller = new KeyboardCamController();
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

        float f = (float) (1f / Math.tan(Math.toRadians(this.camSpecs.getFov()) / 2f));

        float a = f / this.camSpecs.getAspect();
        float b = f;
        float c = this.camSpecs.getSumZ()/this.camSpecs.getDiffZ();
        float d = (2f * this.camSpecs.getzFar() * this.camSpecs.getzNear()) / this.camSpecs.getDiffZ();

        return new Matrix(4, 
        		a, 0, 0, 0,
        		0, b, 0, 0,
        		0, 0, c, -1,
        		0, 0, d, 0			
        );
    }
	
	public Matrix viewMatrix() {
		return new Matrix(4,
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		)
		.multiply(this.transform.xRotation(1))
		.multiply(this.transform.yRotation(1))
		.multiply(this.transform.zRotation(1))
		.multiply(this.transform.calculateTranslation(true).multiply(this.transform.calculateScale()));
	}
	
}
