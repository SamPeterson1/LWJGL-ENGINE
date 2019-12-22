package terrain;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import math.Vector3f;
import models.Entity;
import models.Mesh;
import rendering.Light;
import rendering.Renderer;
import shaders.TerrainShader;

public class TerrainRenderer implements Renderer {

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
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		this.shader.loadLight(light);
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
		this.shader.setSkyColor(new Vector3f(0f, 1f, 1f));
	}

	@Override
	public void end() {
		this.shader.unbind();
		glDisable(GL_CULL_FACE);
	}

	public void loadMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		this.shader.setTextured(true);
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
	}
	
	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	@Override
	public void render(Mesh mesh, List<Entity> entities) {
		this.loadMesh(mesh);
		for(Entity e: entities) {
			this.shader.setTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getMesh().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		this.unloadMesh();
	}

}
