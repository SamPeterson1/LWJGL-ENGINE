package shadows;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.lwjgl.opengl.GL11;

import camera.Camera;
import math.Matrix4f;
import math.Transform;
import math.Vector3f;
import models.Entity;
import models.Mesh;
import rendering.Renderer;
import rendering.Texture;
import shaders.ShadowMapShader;

public class ShadowMapRenderer implements Renderer {

	private ShadowMapShader shader;
	private ShadowMap shadowMap;
	private ShadowBox shadowBox;
	private Matrix4f lightViewMatrix;
	private Matrix4f lightProjectionMatrix;
	private Camera cam;
	private Transform lightTransform;
	
	public ShadowMapRenderer(Camera cam) {
		this.shadowMap = new ShadowMap();
		this.shader = new ShadowMapShader();
		this.cam = cam;
		this.lightViewMatrix = Matrix4f.getIdentity();
		this.lightProjectionMatrix = Matrix4f.getIdentity();
		this.shadowBox = new ShadowBox(this.lightViewMatrix, cam);
		this.lightTransform = new Transform();
		this.shader.create();
		this.shader.bindAllAttributes();
		this.shader.createUniforms();
	}
	
	public Matrix4f getLightViewMatrix() {
		return this.lightViewMatrix;
	}
	
	public Matrix4f getLightProjectionMatrix() {
		return this.lightProjectionMatrix;
	}
	
	public Texture getShadowMap() {
		return this.shadowMap.getTexture();
	}
	
	@Override
	public void begin() {
		
		this.shader.bind();
		shadowBox.update();
		this.lightTransform.setTranslation(new Vector3f(cam.getPosition().getX(), 0, cam.getPosition().getZ()));
		this.lightTransform.setRotation(new Vector3f(90, 0, 0));
		this.lightViewMatrix = Matrix4f.viewMatrix(lightTransform);
		this.lightProjectionMatrix = Matrix4f.orthographic(100, 100, 100);
		this.shader.loadViewMatrix(this.lightViewMatrix);
		this.shader.loadProjectionMatrix(this.lightProjectionMatrix);
		this.shadowMap.bindFBO();
		glClear(GL_DEPTH_BUFFER_BIT);
		glDisable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
	}
	
	@Override
	public void render(Mesh mesh, List<Entity> entities) {

		glBindVertexArray(mesh.getModel().getVAO_ID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
        for(Entity e: entities) {
			this.shader.loadTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
 
        glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}

	@Override
	public void end() {
		glEnable(GL_CULL_FACE);
		this.shader.unbind();
		this.shadowMap.unbind();
	}

}