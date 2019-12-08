package events;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class EventHandler {
	
	private static long windowID;
	
	private static Map<Integer, Integer> mouseEvents = new HashMap<>();
	private static Map<Integer, Integer> keyEvents = new HashMap<>(); 
	
	private static double scrollPosition;
	private static double scrollChange;
	private static double mouseX;
	private static double mouseY;

	//remember raw mouse motion is an option, not implemented yet
	
	public static void init(long windowID) {
		EventHandler.windowID = windowID;
		setEventCallbacks();
	}
	
	public static int getCursorMode() {
		return GLFW.glfwGetInputMode(windowID, GLFW.GLFW_CURSOR);
	}
	
	public static void enableCursor() {
		GLFW.glfwSetInputMode(windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
	
	public static void disableCursor() {
		GLFW.glfwSetInputMode(windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public static void clearEvents() {
		mouseEvents.clear();
		keyEvents.clear();
		scrollChange = 0;
	}
	
	public static void setWindowID(long windowID) {
		EventHandler.windowID = windowID;
	}
	
	private static void setEventCallbacks() {
		GLFW.glfwSetMouseButtonCallback(windowID, GLFWMouseButtonCallback.create((window, button, action, mods) -> mouseCallback(window, button, action, mods)));
		GLFW.glfwSetScrollCallback(windowID, GLFWScrollCallback.create((window, xOffset, yOffset) -> scrollCallback(window, xOffset, yOffset)));
		GLFW.glfwSetKeyCallback(windowID, GLFWKeyCallback.create((window, key, scancode, action, mods) -> keyCallback(window, key, scancode, action, mods)));
		GLFW.glfwSetCursorPosCallback(windowID, GLFWCursorPosCallback.create((window, xpos, ypos) -> cursorPositionCallback(window, xpos, ypos)));
	}
	
	//------------------------MOUSE POSITION------------------------
	
	private static void cursorPositionCallback(long window, double xpos, double ypos) {
		mouseX = xpos;
		mouseY = ypos;
	}
	
	public static double getCursorX() {
		return mouseX;
	}
	
	public static double getCursorY() {
		return mouseY;
	}
	
	//------------------------SCROLL EVENTS------------------------
	
	private static void scrollCallback(long window, double xOffset, double yOffset) {
		scrollPosition += yOffset;
		scrollChange = yOffset;
	}
	
	public static double getScrollPosition() {
		 return scrollPosition;
	}
	
	public static double getScrollChange() {
		return scrollChange;
	}
	
	//------------------------MOUSE BUTTON EVENTS------------------------
	
	private static void mouseCallback(long window, int button, int action, int mods) {
		mouseEvents.put(button, action);
	}
	
	public static boolean mouseButtonDown(int button) {
		return (GLFW.glfwGetMouseButton(windowID, button) == GLFW.GLFW_PRESS);
	}
	
	public static boolean mouseButtonJustDown(int button) {
		return mouseHasState(button, GLFW.GLFW_PRESS);
	}
	
	public static boolean mouseButtonReleased(int button) {
		return mouseHasState(button, GLFW.GLFW_RELEASE);
	}
	
	private static boolean mouseHasState(int button, int state) {
		if(mouseEvents.containsKey(button)) {
			return (mouseEvents.get(button) == state);
		}
		return false;
	}
	
	//------------------------KEY EVENTS------------------------
	
	private static void keyCallback(long window, int key, int scancode, int action, int mods) {
		keyEvents.put(key, action);
	}
	
	private static boolean keyHasState(int key, int state) {
		if(keyEvents.containsKey(key)) {
			return (keyEvents.get(key) == state);
		}
		return false;
	}
	
	public static boolean keyRepeated(int key) {
		return keyHasState(key, GLFW.GLFW_REPEAT);
	}
	
	public static boolean keyReleased(int key) {
		return keyHasState(key, GLFW.GLFW_RELEASE);
	}
	
	public static boolean keyJustDown(int key) {
		return keyHasState(key, GLFW.GLFW_PRESS);
	}
	
	public static boolean keyDown(int key) {
		return GLFW.glfwGetKey(windowID, key) == GLFW.GLFW_PRESS;
	}
		
}
