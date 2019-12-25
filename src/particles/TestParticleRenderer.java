package particles;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
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
import math.Matrix4f;
import models.Entity;
import models.Mesh;
import rendering.Renderer;
import shaders.ParticleShader;

public class TestParticleRenderer implements Renderer {
	
	private ParticleShader shader;
	private Camera cam;
	public static final int MAX_PARTICLES = 30000;
	public static final int FLOATS_PER_PARTICLE = 22;
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_PARTICLES*FLOATS_PER_PARTICLE);
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
	}

	@Override
	public void render(Mesh mesh, List<Entity> entities) {
		
		this.loadMesh(mesh);
		
		Matrix4f viewMatrix = cam.viewMatrix();
		float[] vboData = new float[entities.size() * 22];
		this.shader.setProjectionMatrix(cam.perspective());
		this.shader.setViewMatrix(viewMatrix);
		this.shader.setAtlasRows(((Particle) entities.get(0)).getAnimation());
		
		int numParticles = 0;
		for(Entity e: entities) {
			if(e.isEnabled()) {
				Particle p = (Particle) e;
				pointer = p.getTransform().getTransposedTransform(viewMatrix).storeData(vboData, pointer);
				vboData[pointer++] = p.getAnimation().getTexOffset1().getX();
				vboData[pointer++] = p.getAnimation().getTexOffset1().getY();
				vboData[pointer++] = p.getAnimation().getTexOffset2().getX();
				vboData[pointer++] = p.getAnimation().getTexOffset2().getY();
				vboData[pointer++] = p.getAnimation().getTextureData().getY();
				vboData[pointer++] = p.getAnimation().getFadeOut();
				numParticles ++;
			}
		}
		this.pointer = 0;
		
		mesh.getModel().updateVBO(mesh.getModel().getVBO(0), vboData, buffer);
		
		GL40.glDrawElementsInstanced(GL_TRIANGLES, entities.get(0).getMesh().getModel().getVertexCount(), GL_UNSIGNED_INT, 0, numParticles);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(6);
		GL20.glDisableVertexAttribArray(7);
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
		GL20.glEnableVertexAttribArray(7);
		
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
		
	}

	@Override
	public void end() {
		this.shader.unbind();
		glEnable(GL_CULL_FACE);
	}
	
}
