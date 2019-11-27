package rendering;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import math.Matrix;
import models.Mesh;
import shaders.TerrainShader;

public class TerrainRenderer implements IRenderer {

	private TerrainShader shader;
	private Light light;
	private Camera cam;
	
	public TerrainRenderer(Light light, Camera cam) {
		this.shader = new TerrainShader();
		this.shader.create();	
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.light = light;
		this.cam = cam;
	}
	
	@Override
	public void begin() {
		System.out.println("BEGIN");
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
		this.shader.loadLight(light);
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
	}

	@Override
	public void end() {
		System.out.println("FOOY");
		this.shader.unbind();
	}

	@Override
	public void loadMesh(Mesh mesh) {
		System.out.println("Textured mesh loaded");
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		this.shader.setTextured(true);
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
	}
	
	@Override
	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}

	@Override
	public void setTransformationMatrix(Matrix matrix) {
		this.shader.setTransformationMatrix(matrix);
	}

}
