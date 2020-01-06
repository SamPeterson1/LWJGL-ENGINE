package shadows;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.lwjgl.opengl.GL11;

import camera.Camera;
import camera.CameraSpecs;
import math.Matrix4f;
import models.Entity;
import models.Mesh;
import shaders.ShadowMapShader;

public class ShadowCubemapRenderer {
	
	private static final int CUBEMAP_SIZE = 512;
	private ShadowCubeMap cubeMap;
	private ShadowMapShader shader;
	private Camera cam;
	
	public ShadowCubemapRenderer() {
		this.cubeMap = new ShadowCubeMap(512);
		this.shader = new ShadowMapShader();
		CameraSpecs camSpecs = new CameraSpecs();
		camSpecs.setAspect(1f);
		camSpecs.setFov(90f);
		camSpecs.setzFar(1000f);
		camSpecs.setzNear(0.01f);
		this.cam = new Camera(camSpecs);
		this.shader.create();
		this.shader.bindAllAttributes();
		this.shader.createUniforms();
	}
	
	public void begin() {
		this.shader.bind();
		this.shader.loadProjectionMatrix(this.cam.perspective());
		this.shader.setSampler(GL_TEXTURE0);
	}
	
	public void render(Mesh mesh, List<Entity> entities) {
		
		mesh.getMaterial().getTexture().bind();
		
		glBindVertexArray(mesh.getModel().getVAO_ID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		for(int face = 0; face < 6; face ++) {
			this.switchToFace(face);
			this.shader.loadViewMatrix(cam.viewMatrix());
			this.cubeMap.bindCubeMapFace(face);
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
