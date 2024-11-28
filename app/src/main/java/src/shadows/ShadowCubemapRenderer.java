package shadows;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
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
import shaders.ShadowMapShader2;
import window.GLFWWindow;

public class ShadowCubemapRenderer {
	
	private static final int CUBEMAP_SIZE = 512;
	private static ShadowMapShader2 shader;
	private static Camera cam;
	private static CameraSpecs camSpecs;
	
	private static ShadowCubeMap cubeMap;
	private static PointLight light;
	
	public static void init() {
		shader = new ShadowMapShader2();
		shader.create();
		shader.bindAllAttributes();
		shader.createUniforms();
		camSpecs = new CameraSpecs();
		camSpecs.setAspect(1);
		camSpecs.setFov(90f);
		camSpecs.setzFar(100f);
		camSpecs.setzNear(0.01f);
		cam = new Camera(camSpecs);
	}
	
	public ShadowCubemapRenderer(PointLight light) {
		this.cubeMap = new ShadowCubeMap(1024);
		
		this.light = light;
		

	}
	
	public static void begin(PointLight light) {
		
		cubeMap = light.getShadowMap();
		ShadowCubemapRenderer.light = light;
		cubeMap.bindFBO();
		cam.getTransform().setTranslation(light.getPosition());
		shader.bind();
		shader.loadProjectionMatrix(Matrix4f.perspective(camSpecs));
		for(int face = 0; face < 6; face ++) {
			cubeMap.bindCubeMapFace(face);
			glClear(GL_DEPTH_BUFFER_BIT);
		}
		glEnable(GL_DEPTH_TEST);
		
	}
		
	public void begin() {
		
		
	}
	
	public static void render(Mesh mesh, List<Entity> entities) {
	
		glBindVertexArray(mesh.getModel().getVAO_ID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		cam.getTransform().setTranslation(light.getPosition());
		cam.getTransform().setRotationZ(180);
		mesh.getMaterial().getTexture().bind();
		shader.setSampler(GL_TEXTURE0);
		for(int face = 0; face < 6; face ++) {
			switchToFace(face);
			cubeMap.bindCubeMapFace(face);
			shader.loadViewMatrix(cam.viewMatrix());
			for(Entity e: entities) {
				shader.loadTransformationMatrix(e.getTransform().getTransform());
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}
		
		glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public static void end() {
		shader.unbind();
		glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	    glViewport(0, 0, GLFWWindow.getWidth(), GLFWWindow.getHeight());
	}
	
	public static void switchToFace(int face) {
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
