package particles;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import math.Matrix;
import models.Mesh;
import rendering.Renderer;
import shaders.ParticleShader;

public class ParticleRenderer implements Renderer {

	private ParticleShader shader;
	private Camera cam;
	
	public ParticleRenderer(Camera cam) {
		this.shader = new ParticleShader();
		this.shader.create();	
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.cam = cam;
	}
	
	@Override
	public void begin() {
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
	}

	@Override
	public void end() {
		this.shader.unbind();
		glEnable(GL_CULL_FACE);
	}

	@Override
	public void loadMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
	}
	
	@Override
	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
	}

	@Override
	public void setTransformationMatrix(Matrix matrix) {
		this.shader.setTransformationMatrix(matrix);
	}

}
