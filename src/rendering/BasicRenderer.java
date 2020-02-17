package rendering;

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
import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.TexturedMesh;
import shaders.BasicShader;

public class BasicRenderer implements Renderer {

	private BasicShader shader;
	private Camera cam;
	
	public BasicRenderer(Camera cam) {
		this.shader = new BasicShader();
		this.shader.create();	
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.cam = cam;
	}
	
	@Override
	public void begin() {
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
		this.shader.setSkyColor(Environment.fogColor);
		this.shader.loadLights();
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
	}

	@Override
	public void end() {
		this.shader.unbind();
	}

	
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
		this.shader.setUseFakeLighting(false);
		this.shader.setTextured(false);
		this.shader.setColor(mesh.getMaterial().getRGB());
	}
	
	private void loadTexturedMesh(Mesh mesh) {
		
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		this.shader.setTextured(true);
		this.shader.setUseFakeLighting(false);
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
		
	}

	@Override
	public void render(Mesh mesh, List<Entity> entities) {
		
		if(mesh.getType() == Mesh.TEXTURED) {
			this.loadTexturedMesh(mesh);	
		} else {
			this.loadColoredMesh(mesh);
		}
		
		for(Entity e: entities) {
			this.shader.setTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		
	}
	
}
