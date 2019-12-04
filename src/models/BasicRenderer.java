package models;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import math.Matrix;
import math.Vector3f;
import rendering.Light;
import rendering.MasterRenderer;
import rendering.Renderer;
import shaders.BasicShader;

public class BasicRenderer implements Renderer {

	private BasicShader shader;
	private Light light;
	private Camera cam;
	
	public BasicRenderer(Light light, Camera cam) {
		this.shader = new BasicShader();
		this.shader.create();	
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.light = light;
		this.cam = cam;
	}
	
	@Override
	public void begin() {
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
	}

	@Override
	public void end() {
		this.shader.unbind();
	}

	@Override
	public void loadMesh(Mesh mesh) {
		if(mesh.getType() == Mesh.TEXTURED) {
			this.loadTexturedMesh(mesh);
		} else {
			this.loadColoredMesh(mesh);
		}
		this.shader.loadLight(light);
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
		this.shader.setSkyColor(new Vector3f(0f, 1f, 1f));
	}
	
	@Override
	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	private void loadColoredMesh(Mesh mesh) {
		glDisable(GL_BLEND);
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
		this.shader.setMaterialReflectivity(mesh.getMaterial().getReflectivity(), mesh.getMaterial().getShineDamping());
		this.shader.setUseFakeLighting(false);
		this.shader.setTextured(false);
		this.shader.setColor(mesh.getMaterial().getColor());
	}
	
	private void loadTexturedMesh(Mesh mesh) {
		TexturedMesh texturedMesh = (TexturedMesh) mesh;
		MasterRenderer.setDoCull((texturedMesh).cullFace());
		
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		this.shader.setTextured(true);
		this.shader.setUseFakeLighting(texturedMesh.usesFakeLighting());
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
		
	}

	@Override
	public void setTransformationMatrix(Matrix matrix) {
		this.shader.setTransformationMatrix(matrix);
	}
	
}
