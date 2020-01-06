package shadows;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import rendering.Texture;

public class ShadowCubeMap {
	
	private Texture depthCubeMap;
	private int depthFBO;
	
	public ShadowCubeMap(int res) {
		this.depthCubeMap = new Texture();
		this.depthCubeMap.emptyDepthCubemap(res);
		
		this.depthFBO = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, this.depthFBO);
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Could not create FrameBuffer");
		}

	}
	
	public void bindCubeMapFace(int face) {
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_COMPONENT16, GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, this.depthCubeMap.getID(), 0);
	}
	
}
