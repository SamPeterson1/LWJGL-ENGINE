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
import math.Matrix4f;
import math.Transform;
import math.Vector2f;
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
	private DirectionalLight light;
	
	public ShadowMapRenderer(Camera cam, DirectionalLight light) {
		this.shadowMap = new ShadowMap();
		this.shader = new ShadowMapShader();
		this.cam = cam;
		this.light = light;
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
		this.lightTransform.setRotation(new Vector3f(70, 0, 0));
		this.lightViewMatrix = Matrix4f.viewMatrix(lightTransform);
		this.lightProjectionMatrix = Matrix4f.orthographic(50, 50, 50);
		this.shader.loadViewMatrix(this.lightViewMatrix);
		this.shader.loadProjectionMatrix(this.lightProjectionMatrix);
		this.shadowMap.bindFBO();
		glClear(GL_DEPTH_BUFFER_BIT);
		glDisable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
	}
	
	private void updateLightViewMatrix(Vector3f direction, Vector3f center) {
        direction.normalize();
        center.negate();
        lightViewMatrix = Matrix4f.getIdentity();
        float pitch = (float) Math.acos(new Vector2f(direction.getX(), direction.getZ()).length());
        lightViewMatrix.multiply(Transform.xRotation(pitch));
        float yaw = (float) Math.toDegrees(((float) Math.atan(direction.getX() / direction.getZ())));
        yaw = direction.getZ() > 0 ? yaw - 180 : yaw;
        lightViewMatrix.multiply(Transform.yRotation(yaw));
        lightViewMatrix.multiply(Transform.translation(center));
    }
	
	@Override
	public void render(Mesh mesh, List<Entity> entities) {

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
