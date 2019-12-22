package shadows;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.lwjgl.opengl.GL11;

import camera.Camera;
import lights.DirectionalLight;
import math.Matrix;
import math.Transform;
import models.Entity;
import models.Mesh;
import rendering.Renderer;
import rendering.Texture;
import shaders.ShadowMapShader;

public class ShadowMapRenderer implements Renderer {

	private ShadowMapShader shader;
	private ShadowMap shadowMap;
	private Camera cam;
	
	public ShadowMapRenderer(Camera cam) {
		this.shadowMap = new ShadowMap();
		this.shader = new ShadowMapShader();
		this.cam = cam;
		this.shader.create();
		this.shader.bindAllAttributes();
		this.shader.createUniforms();
	}
	
	public Texture getShadowMap() {
		return this.shadowMap.getTexture();
	}
	
	@Override
	public void begin() {
		
		this.shadowMap.bindFBO();
		this.shader.bind();
		this.shader.loadViewMatrix(cam.viewMatrix());
		this.shader.loadProjectionMatrix(Matrix.orthographic(50, -50, 50));
		
		glClear(GL_DEPTH_BUFFER_BIT);
		glDisable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
	}

	@Override
	public void render(Mesh mesh, List<Entity> entities) {

		System.out.println("FOO!");
		glBindVertexArray(mesh.getModel().getVAO_ID());
		glEnableVertexAttribArray(0);

        for(Entity e: entities) {
			this.shader.loadTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
 
        glBindVertexArray(0);
		glDisableVertexAttribArray(0);
	}

	@Override
	public void end() {
		glEnable(GL_CULL_FACE);
		this.shader.unbind();
		this.shadowMap.unbind();
	}

}
