package text;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.Entity;
import models.Mesh;
import rendering.Renderer;
import shaders.TextShader;

public class TextRenderer implements Renderer {
	
	TextShader shader;
	
	public TextRenderer() {
		this.shader = new TextShader();
		this.shader.create();
		this.shader.createUniforms();
		this.shader.bindAllAttributes();
		
		glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER,0);
	}
	
	@Override
	public void begin() {
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		this.shader.bind();
	}
	
	public void loadMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        this.shader.setTextColor(mesh.getMaterial().getRGB());
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
	}

	public void unloadMesh() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}

	@Override
	public void end() {
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		this.shader.unbind();
	}

	@Override
	public void render(Mesh mesh, List<Entity> entities) {
		this.loadMesh(mesh);
		
		for(Entity e: entities) {
			this.shader.setTransformationMatrix(e.getTransform().getTransform());
			GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		this.unloadMesh();
	}

}
