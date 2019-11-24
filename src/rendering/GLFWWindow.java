package rendering;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class GLFWWindow {

	private static long window;
	
	public static void init(int width, int height, String title) {
		
		if(!GLFW.glfwInit()) {
			System.err.println("Couldn't initialize GLFW window");
			System.exit(-1);
		}
		
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
	
		if(window == 0) {
			System.err.println("Window couldn't be created");
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, (videoMode.height() - height)/2);
		GLFW.glfwShowWindow(window);	
		glEnable(GL_DEPTH_TEST);
	}
	
	public static void update() {
		GL11.glClearColor(1f, 0f, 1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GLFW.glfwPollEvents();
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
	
	public static void disableCursor() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
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
}
