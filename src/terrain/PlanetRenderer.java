package terrain;

import static org.lwjgl.opengl.GL11.GL_BLEND;
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
import models.Entity;
import models.Mesh;
import rendering.Environment;
import shaders.BasicShader;
import shaders.PlanetShader;

public class PlanetRenderer {
	
	private Camera cam;
	private PlanetShader shader;
	
	public PlanetRenderer(Camera cam) {
		this.shader = new PlanetShader();
		this.shader.create();	
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.cam = cam;
	}
	
	public void begin() {
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
		this.shader.setSkyColor(Environment.fogColor);
		this.shader.loadLights();
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
	}

	public void end() {
		this.shader.unbind();
	}

	
	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	private void loadMesh(Mesh mesh) {
		
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
		
	}

	public void render(Mesh mesh, List<Entity> entities) {
		
		this.loadMesh(mesh);
		
		for(Entity e: entities) {
			this.shader.setTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		
	}
	
}
