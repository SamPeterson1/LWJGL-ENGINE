package GUI;

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
		
		glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER,0);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		this.shader.bind();
	}
	
	@Override
	public void end() {
		this.shader.unbind();
		glDisable(GL_BLEND);
		glEnable(GL_CULL_FACE);
	}

	public void loadMesh(Mesh mesh) {
		
		GL30.glBindVertexArray(mesh.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        this.shader.setDepth(((GUIComponent) mesh).getDepth());
        if(mesh.getType() == Mesh.GUI_COLORED) {
        	this.shader.setColor(mesh.getMaterial().getColor());
        	this.shader.setTextured(false);
        	this.shader.setHasBackground(false);
        } else if(mesh.getType() == Mesh.GUI_TEXTURED) {
        	System.out.println("texturer???????");
        	this.shader.setTextured(true);
        	GL20.glEnableVertexAttribArray(1);
        	glActiveTexture(GL_TEXTURE0);
    		mesh.getMaterial().getTexture().bind();
    		if(mesh.getMaterial().getColor() != null) {
    			this.shader.setColor(mesh.getMaterial().getColor());
    			this.shader.setHasBackground(true);
    		} else {
    			this.shader.setHasBackground(false);
    		}
    		this.shader.setSampler(GL_TEXTURE0);
        }
	}
	
	public void unloadMesh() {
		GL30.glBindVertexArray(0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
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
