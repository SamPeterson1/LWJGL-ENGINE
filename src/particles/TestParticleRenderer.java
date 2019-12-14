package particles;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;

import camera.Camera;
import math.Matrix;
import models.Entity;
import models.Mesh;
import rendering.Renderer2;
import shaders.ParticleShader;

public class TestParticleRenderer implements Renderer2 {
	
	private ParticleShader shader;
	private Camera cam;
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(10000*21);
	private int pointer = 0;
	
	public TestParticleRenderer(Camera cam) {
		this.shader = new ParticleShader();
		this.shader.create();
		this.shader.createUniforms();
		this.cam = cam;
	}

	@Override
	public void begin() {
		this.shader.bind();
		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDepthMask(false);
	}

	@Override
	public void render(Mesh mesh, List<Entity> entities) {
		
		this.loadMesh(mesh);
		
		Matrix viewMatrix = cam.viewMatrix();
		float[] vboData = new float[entities.size() * 21];
		this.shader.setProjectionMatrix(cam.perspective());
		this.shader.setViewMatrix(viewMatrix);
		
		for(Entity e: entities) {
			Particle p = (Particle) e;
			pointer = p.getTransform().getTransposedTransform(viewMatrix).storeData(vboData, pointer);
			vboData[pointer++] = p.getAnimation().getTexOffset1().getX();
			vboData[pointer++] = p.getAnimation().getTexOffset1().getY();
			vboData[pointer++] = p.getAnimation().getTexOffset2().getX();
			vboData[pointer++] = p.getAnimation().getTexOffset2().getY();
			vboData[pointer++] = p.getAnimation().getTextureData().getY();
		}
		this.pointer = 0;
		
		mesh.getModel().updateVBO(mesh.getModel().getVBO(0), vboData, buffer);
		
		GL40.glDrawElementsInstanced(GL_TRIANGLES, entities.get(0).getMesh().getModel().getVertexCount(), GL_UNSIGNED_INT, 0, entities.size());
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(6);
	}
	
	private void loadMesh(Mesh mesh) {
		
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
		GL20.glEnableVertexAttribArray(5);
		GL20.glEnableVertexAttribArray(6);
		
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
		
	}

	@Override
	public void end() {
		this.shader.unbind();
		glDepthMask(true);
		glEnable(GL_CULL_FACE);
	}
	
}
