package terrain;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import lights.PointLight;
import math.Matrix4f;
import math.Vector3f;
import models.Entity;
import models.Mesh;
import rendering.Environment;
import rendering.Texture;
import shaders.TerrainShader;

public class TerrainRenderer{

	private TerrainShader shader;
	private Camera cam;
	
	public TerrainRenderer(Camera cam) {
		this.shader = new TerrainShader();
		this.shader.create();	
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.cam = cam;
	}
	
	public void setShadowMatrices(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		this.shader.loadShadowMapMatrices(viewMatrix, projectionMatrix);
	}
	
	public void begin() {
		this.shader.bind();
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		this.shader.loadLights();
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
		this.shader.setSkyColor(Environment.fogColor);
	}

	public void end() {
		this.shader.unbind();
		glDisable(GL_CULL_FACE);
	}

	public void loadMesh(Mesh mesh, Texture shadowMap) {
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
        mesh.getMaterial().getTexture().bind();
        this.shader.setSampler(0);
		glActiveTexture(GL_TEXTURE1);
		shadowMap.bind();
		this.shader.setShadowSampler(1);		
	}
	
	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	public void render(Mesh mesh, List<Entity> entities, Texture shadowMap) {
		this.loadMesh(mesh, shadowMap);
		for(Entity e: entities) {
			this.shader.setTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, e.getMesh().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		this.unloadMesh();
	}

}
