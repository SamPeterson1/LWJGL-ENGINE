package models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import math.Vector3f;
import rendering.Light;
import rendering.ModelBatch;
import shaders.BasicShader;

public class Renderer {
	
	BasicShader shader;
	Camera cam;
	Light light;
	
	public Renderer(BasicShader shader, Camera cam, Light light) {
		this.shader = shader;
		this.cam = cam;
		this.shader.createUniforms();
		this.light = light;
	}
	
	public void renderModel(RawModel model) {
		shader.bind();
		GL30.glBindVertexArray(model.getVAO_ID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCt(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.unbind();
	}
	
	public void renderTexturedModel(TexturedModel model) {
		shader.bind();
		GL30.glBindVertexArray(model.getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCt(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shader.unbind();
	}
	
	private void begin(Model model) {
		GL30.glBindVertexArray(model.getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
	}
	
	private void end() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	public void renderElements(ModelBatch batch) {
		cam.update();	
		this.shader.bind();
		
		for(GameElement element: batch.getElements()) {
			element.update();
			this.begin(element.getMesh());
			this.shader.loadLight(light);
			this.shader.setTextured(false);
			this.shader.setProjectionMatrix(cam.perspective());
			this.shader.setColor(new Vector3f(1f, 0f, 1f));
			this.shader.setTransformationMatrix(element.getTransformationMatrix());
			this.shader.setViewMatrix(cam.viewMatrix());
			GL11.glDrawElements(GL11.GL_TRIANGLES, element.getMesh().getVertexCt(), GL11.GL_UNSIGNED_INT, 0);
			this.end();
		}
		shader.unbind();
	}
}
