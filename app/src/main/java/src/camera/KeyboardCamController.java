package camera;

import org.lwjgl.glfw.GLFW;

import events.EventHandler;
import math.Transform;
import math.Vector3f;
import misc.Time;
import terrain.Terrain;

public class KeyboardCamController {
	
	private float speed = 20f;
	private float xSense = 0.05f;
	private float ySense = 0.05f;
	private Terrain terrain;
	private float foot = 0f;
	
	private boolean cursorEnabled = false;
	
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public void update(Transform camera) {
		
		if(EventHandler.keyJustDown(GLFW.GLFW_KEY_ESCAPE)) {
			if(cursorEnabled) {
				EventHandler.disableCursor();
				this.cursorEnabled = false;
			} else {
				EventHandler.enableCursor();
				this.cursorEnabled = true;
			}
		}
		
		if(EventHandler.keyDown(GLFW.GLFW_KEY_W)) {
			camera.translateX(this.movementX(speed * Time.deltaTime, camera));
			camera.translateY(this.movementY(-speed * Time.deltaTime, camera));
			camera.translateZ(this.movementZ(-speed * Time.deltaTime, camera));
			//beta += -speed * Time.deltaTime;
		}
		if(EventHandler.keyDown(GLFW.GLFW_KEY_S)) {
			camera.translateX(this.movementX(-speed * Time.deltaTime, camera));
			camera.translateY(this.movementY(-speed * Time.deltaTime, camera));
			camera.translateZ(this.movementZ(speed * Time.deltaTime, camera));
		}
		if(EventHandler.keyDown(GLFW.GLFW_KEY_A)) {
			camera.translateZ(this.strafeZ(speed * Time.deltaTime, camera));
			camera.translateY(this.strafeY(speed * Time.deltaTime, camera));
			camera.translateX(this.strafeX(-speed * Time.deltaTime, camera));
		}
		if(EventHandler.keyDown(GLFW.GLFW_KEY_D)) {
			camera.translateZ(this.strafeZ(-speed * Time.deltaTime, camera));
			camera.translateY(this.strafeY(speed * Time.deltaTime, camera));
			camera.translateX(this.strafeX(speed * Time.deltaTime, camera));
		}
		
		Vector3f newRotation = new Vector3f();
		newRotation.setY((float)EventHandler.getCursorX() * this.xSense);
		newRotation.setX((float)EventHandler.getCursorY() * this.ySense);
		foot += 10*Time.deltaTime;
		camera.setRotation(newRotation);
	}
	
	private float strafeZ(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY() + 90f);
		return dist * (float)Math.cos(radians);
	}
	
	private float strafeY(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getX() + 90f);
		return dist * (float)Math.sin(radians);
	}
	
	private float strafeX(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY() + 90f);
		return dist * (float)Math.sin(radians);
	}
	
	private float movementZ(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY());
		return dist * (float)Math.cos(radians);
	}
	
	private float movementY(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getX());
		return dist * (float)Math.sin(radians);
	}
	
	private float movementX(float dist, Transform camera) {
		double radians = Math.toRadians(camera.getRotation().getY());
		return dist * (float)Math.sin(radians);
	}

}
