package rendering;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import GUI.GUIComponent;
import math.Matrix;
import models.Mesh;
import shaders.GUIShader;

public class GUIRenderer implements IRenderer {

	private GUIShader shader;
	
	public GUIRenderer() {
		
		this.shader = new GUIShader();
		this.shader.create();
		this.shader.createUniforms();
		
	}
	
	@Override
	public void begin() {
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		this.shader.bind();
	}
	
	@Override
	public void end() {
		this.shader.unbind();
		glEnable(GL_CULL_FACE);
	}

	@Override
	public void loadMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        this.shader.setDepth(((GUIComponent) mesh).getDepth());
        this.shader.setColor(mesh.getMaterial().getColor());
	}

	@Override
	public void unloadMesh() {
		GL30.glBindVertexArray(0);
        GL20.glDisableVertexAttribArray(0);
	}

	@Override
	public void setTransformationMatrix(Matrix matrix) {
		this.shader.setTransformationMatrix(matrix);
	}

}
