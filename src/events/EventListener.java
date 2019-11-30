package events;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class EventListener {
	
	private static long windowID;
	private static boolean leftMouseDown;
	private static boolean leftMouseJustDown;

	public static void init(long windowID) {
		EventListener.windowID = windowID;
		setEventCallbacks();
	}
	
	public static void endFrame() {
		leftMouseJustDown = false;
	}
	
	private static void setEventCallbacks() {
		GLFW.glfwSetMouseButtonCallback(windowID, GLFWMouseButtonCallback.create((window, button, action, mods) -> mouseCallback(window, button, action, mods)));
	}
	
	private static void mouseCallback(long window, int button, int action, int mods) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			if(action == GLFW.GLFW_PRESS) {
				leftMouseDown = true;
				leftMouseJustDown = true;
			} else if(action == GLFW.GLFW_RELEASE) {
				leftMouseDown = false;
			}
		}
	}
	
	public static boolean leftMouseJustDown() {
		return leftMouseJustDown;
	}
	
	public static boolean leftMouseDown() {
		return leftMouseDown;
	}
	
	public static void setWindowID(long windowID) {
		EventListener.windowID = windowID;
	}
	
	public static boolean keyDown(int key) {
		return GLFW.glfwGetKey(windowID, key) == 1;
	}
	
	public static boolean rightMouseDown() {
		return GLFW.glfwGetMouseButton(windowID, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == 1;
	}
	
	public static boolean middleMouseDown() {
		return GLFW.glfwGetMouseButton(windowID, GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == 1;
	}
	
	
}
