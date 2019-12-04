package GUI;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import math.Matrix;
import models.Mesh;
import rendering.Renderer;
import shaders.GUIShader;

public class GUIRenderer implements Renderer {

	private GUIShader shader;
	
	public GUIRenderer() {
		
		this.shader = new GUIShader();
		this.shader.create();
		this.shader.bindAllAttributes();
		this.shader.createUniforms();
		
	}
	
	@Override
	public void begin() {
		glEnable(GL_DEPTH_TEST);
		//glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		this.shader.bind();
	}
	
	@Override
	public void end() {
		this.shader.unbind();
		//glDisable(GL_BLEND);
		glEnable(GL_CULL_FACE);
	}

	@Override
	public void loadMesh(Mesh mesh) {
		
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        this.shader.setDepth(((GUIComponent) mesh).getDepth());
        if(mesh.getType() == Mesh.GUI_COLORED) {
        	this.shader.setColor(mesh.getMaterial().getColor());
        	this.shader.setTextured(false);
        } else if(mesh.getType() == Mesh.GUI_TEXTURED) {
        	this.shader.setTextured(true);
        	GL20.glEnableVertexAttribArray(1);
        	glActiveTexture(GL_TEXTURE0);
    		mesh.getMaterial().getTexture().bind();
    		this.shader.setSampler(GL_TEXTURE0);
        }
	}
	
	@Override
	public void unloadMesh() {
		GL30.glBindVertexArray(0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
	}

	@Override
	public void setTransformationMatrix(Matrix matrix) {
		this.shader.setTransformationMatrix(matrix);
	}

}
