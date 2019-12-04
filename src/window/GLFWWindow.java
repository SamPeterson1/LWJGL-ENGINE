package window;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import events.EventListener;

public class GLFWWindow {

	private static long window;
	private static int width;
	private static int height;
	private static boolean resized = false;
	private static List<WindowListener> listeners = new ArrayList<>();
	
	public static void init(int width, int height, String title) {
		
		GLFWWindow.width = width;
		GLFWWindow.height = height;
		
		if(!GLFW.glfwInit()) {
			System.err.println("Couldn't initialize GLFW window");
			System.exit(-1);
		}
		
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
	
		if(window == 0) {
			System.err.println("Window couldn't be created");
			System.exit(-1);
		}
		
		EventListener.init(window);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, (videoMode.height() - height)/2);
		GLFW.glfwShowWindow(window);	
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);
		glEnable(GL_MULTISAMPLE); 
	}
	
	public static void addListener(WindowListener listener) {
		GLFWWindow.listeners.add(listener);
	}
	
	public static boolean resized() {
		return resized;
	}
	
	private static void checkResize() {
		int[] width = new int[1];
		GLFW.glfwGetWindowSize(window, width, null);
		resized = GLFWWindow.width != width[0];		
		if(resized) {
			GLFWWindow.width = width[0];
			return;
		}
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(window, null, height);
		resized = GLFWWindow.height != height[0];
		if(resized) {
			GLFWWindow.height = height[0];
			return;
		}
	}
	
	public static int getWidth() {
		int[] width = new int[1];
		GLFW.glfwGetWindowSize(window, width, null);
		return width[0];
	}
	
	public static int getHeight() {
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(window, null, height);
		return height[0];
	}
	
	public static void update() {
		GL11.glClearColor(0f, 1f, 1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GLFW.glfwPollEvents();
	}
	
	public static void sendResize() {
		for(WindowListener listener: listeners) {
			listener.onResize(width, height);
		}
	}
	
	public static void updateSize() {
		int width = GLFWWindow.getWidth();
		int height = GLFWWindow.getHeight();
		checkResize();
		if(resized) {
			for(WindowListener listener: listeners) {
				listener.onResize(width, height);
			}
		}
	}
	
	public static double getCursorX() {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, posX, null);
		return posX.get(0);
	}
	
	public static double getCursorY() {
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, posY);
		return posY.get(0);
	}
	
	public static void enableCursor() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
	
	public static void disableCursor() {
		//GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public static boolean keyDown(int key) {
		return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
	}
	
	public static void swapBuffer() {
		GLFW.glfwSwapBuffers(window);
	}
	
	public static boolean closed() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public static long getWindowID() {
		return window;
	}
}
