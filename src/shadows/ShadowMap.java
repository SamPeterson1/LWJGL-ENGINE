package shadows;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import rendering.Texture;
import window.GLFWWindow;

public class ShadowMap {
	
	public static final int WIDTH = 2048;
	public static final int HEIGHT = 2048;
	
	private int depthMapFBO;
	private Texture depthMap; 
	
	public ShadowMap() {
		
		this.depthMapFBO = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
		GL11.glDrawBuffer(GL11.GL_NONE);
        GL11.glReadBuffer(GL11.GL_NONE);
		
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
		
		this.depthMap = new Texture(WIDTH, HEIGHT);
		
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, GLFWWindow.getWidth(), GLFWWindow.getHeight());
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Could not create FrameBuffer");
		}
		
	}
	
	public Texture getTexture() {
		return this.depthMap;
	}
	
	public void unbind() {
		glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        glViewport(0, 0, GLFWWindow.getWidth(), GLFWWindow.getHeight());
	}
	
	public void bindFBO() {
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, depthMapFBO);
		glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public void cleanup() {
		glDeleteFramebuffers(depthMapFBO);
	}
	
}
