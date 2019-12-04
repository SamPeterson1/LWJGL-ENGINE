package camera;

import org.lwjgl.glfw.GLFW;

import math.Transform;
import math.Vector3f;
import misc.Time;
import window.GLFWWindow;

public class KeyboardCamController implements CameraController {
	
	private float speed = 20f;
	private float xSense = 0.05f;
	private float ySense = 0.05f;
	
	private boolean cursorEnabled = false;
	
	@Override
	public void update(Transform camera) {
		
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_ESCAPE)) {
			if(cursorEnabled) {
				GLFWWindow.disableCursor();
				this.cursorEnabled = false;
			} else {
				GLFWWindow.enableCursor();
				this.cursorEnabled = true;
			}
		}
		
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_W)) {
			camera.translateX(this.movementX(speed * Time.deltaTime, camera));
			camera.translateZ(this.movementZ(-speed * Time.deltaTime, camera));
		}
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_S)) {
			camera.translateX(this.movementX(-speed * Time.deltaTime, camera));
			camera.translateZ(this.movementZ(speed * Time.deltaTime, camera));
		}
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_A)) {
			camera.translateZ(this.strafeZ(speed * Time.deltaTime, camera));
			camera.translateX(this.strafeX(-speed * Time.deltaTime, camera));
		}
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_D)) {
			camera.translateZ(this.strafeZ(-speed * Time.deltaTime, camera));
			camera.translateX(this.strafeX(speed * Time.deltaTime, camera));
		}
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_SPACE)) {
			camera.translateY(speed * Time.deltaTime);
		}
		if(GLFWWindow.keyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			camera.translateY(-speed * Time.deltaTime);
		}
		Vector3f newRotation = new Vector3f();
		newRotation.setY((float)GLFWWindow.getCursorX() * this.xSense);
		newRotation.setX((float)GLFWWindow.getCursorY() * this.ySense);
		camera.setRotation(newRotation);
		
	}
	
	private float strafeZ(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY() + 90f);
		return dist * (float)Math.cos(radians);
	}
	
	private float strafeX(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY() + 90f);
		return dist * (float)Math.sin(radians);
	}
	
	private float movementZ(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY());
		return dist * (float)Math.cos(radians);
	}
	
	private float movementX(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY());
		return dist * (float)Math.sin(radians);
	}

}
