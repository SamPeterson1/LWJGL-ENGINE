package rendering;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import models.Entity;
import models.Mesh;
import shaders.SkyboxShader;

public class SkyboxRenderer implements Renderer {
	
	private SkyboxShader shader;
	private Camera cam;
	
	private static float FOG_UPPER = 0.4f;
	private static float FOG_LOWER = 0.1f;
	
	public SkyboxRenderer(Camera cam) {
		this.shader = new SkyboxShader();
		this.shader.create();
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		this.cam = cam;
	}
	
	@Override
	public void begin() {
		this.shader.bind();
		this.shader.loadViewMatrix(cam.viewMatrix());
		this.shader.loadProjectionMatrix(cam.perspective());
		this.shader.loadFogColor(Environment.fogColor);
	}

	@Override
	public void render(Mesh mesh, List<Entity> entities) {
		
		mesh.getMaterial().getTexture().bindAsCubemap();
		this.shader.setSampler(GL_TEXTURE0);
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getModel().getVertexCount());
	}

	@Override
	public void end() {
		this.shader.unbind();
	}

}
