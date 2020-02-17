package shadows;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import rendering.Texture;
import window.GLFWWindow;

public class ShadowCubeMap {
	
	private int depthMapFBO;
	private Texture depthMap; 
	private int res;
	
	public ShadowCubeMap(int res) {
		
		this.depthMapFBO = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);

		glDrawBuffer(this.depthMapFBO);
		glReadBuffer(GL_NONE);
		
		this.res = res;
		this.depthMap = new Texture();
		this.depthMap.emptyDepthCubemap(res);
		
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
		glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
		glViewport(0, 0, res, res);
	}
	
	public void bindCubeMapFace(int face) {
		this.depthMap.bindAsCubemap();
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, this.depthMap.getID(), 0);
	}
	
	public void cleanup() {
		glDeleteFramebuffers(depthMapFBO);
	}
	
}
