package shadows;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import camera.CameraSpecs;
import lights.PointLight;
import math.Matrix4f;
import models.Entity;
import models.Mesh;
import models.Skybox;
import rendering.Texture;
import shaders.ShadowMapShader2;
import window.GLFWWindow;

public class ShadowCubemapRenderer {
	
	private static final int CUBEMAP_SIZE = 512;
	private ShadowCubeMap cubeMap;
	private ShadowMapShader2 shader;
	private Camera cam2;
	private Camera cam;
	private CameraSpecs camSpecs;
	
	public ShadowCubemapRenderer(Skybox skybox, PointLight light, Camera cam2) {
		this.cubeMap = new ShadowCubeMap(1024);
		this.shader = new ShadowMapShader2();
		this.shader.create();
		this.shader.bindAllAttributes();
		this.shader.createUniforms();
		camSpecs = new CameraSpecs();
		camSpecs.setAspect(1);
		camSpecs.setFov(90f);
		camSpecs.setzFar(100f);
		camSpecs.setzNear(0.01f);
		this.cam2 = cam2;
		this.cam = new Camera(camSpecs);
		this.cam.getTransform().setTranslation(light.getPosition());
		skybox.getMaterial().setTexture(this.cubeMap.getTexture());

	}
	
	public void begin() {
		this.shader.bind();
		this.shader.loadProjectionMatrix(Matrix4f.perspective(camSpecs));
		//this.shader.setSampler(GL_TEXTURE0);
		this.cubeMap.bindFBO();
		for(int face = 0; face < 6; face ++) {
			this.cubeMap.bindCubeMapFace(face);
			glClear(GL_DEPTH_BUFFER_BIT);
		}
		glEnable(GL_DEPTH_TEST);
	}
	
	public Texture getTexture() {
		return this.cubeMap.getTexture();
	}
	
	public void render(Mesh mesh, List<Entity> entities) {
		
		//mesh.getMaterial().getTexture().bind();
		glBindVertexArray(mesh.getModel().getVAO_ID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		cam.getTransform().setTranslation(cam2.getPosition());
		for(int face = 0; face < 6; face ++) {
			this.switchToFace(face);
			this.cubeMap.bindCubeMapFace(face);
			this.shader.loadViewMatrix(cam.viewMatrix());
			for(Entity e: entities) {
				this.shader.loadTransformationMatrix(e.getTransform().getTransform());
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}
		
		glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public void end() {
		this.shader.unbind();
		glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	    glViewport(0, 0, GLFWWindow.getWidth(), GLFWWindow.getHeight());
	}
	
	public void switchToFace(int face) {
		switch(face) {
		case 0:
			cam.setPitch(0);
			cam.setYaw(90);
			break;
		case 1:
			cam.setPitch(0);
			cam.setYaw(-90);
			break;
		case 2:
			cam.setPitch(-90);
			cam.setYaw(180);
			break;
		case 3:
			cam.setPitch(90);
			cam.setYaw(180);
			break;
		case 4:
			cam.setPitch(0);
			cam.setYaw(180);
			break;
		case 5:
			cam.setPitch(0);
			cam.setYaw(0);
			break;
		}
	}
	
}
