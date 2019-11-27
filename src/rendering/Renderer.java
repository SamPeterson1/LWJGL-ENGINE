package rendering;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import models.ColoredMesh;
import models.Mesh;
import models.Model;
import models.TexturedMesh;
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
	
	private void begin(Model mesh) {
		glEnable(GL_DEPTH_TEST);
		GL30.glBindVertexArray(mesh.getVAO_ID());
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
		

		for(GameElement element: batch.getElementsOfType(Mesh.TEXTURED)) {
			
			TexturedMesh model = (TexturedMesh) element.getMesh();
			element.update();
			this.begin(element.getModel());
			this.shader.loadLight(light);
			this.shader.setTextured(true);
			this.shader.setProjectionMatrix(cam.perspective());
			this.shader.setTransformationMatrix(element.getTransformationMatrix());
			this.shader.setViewMatrix(cam.viewMatrix());
			glActiveTexture(GL_TEXTURE0);
			model.getTexture().bind();
			this.shader.setSampler(GL_TEXTURE0);
			GL11.glDrawElements(GL11.GL_TRIANGLES, element.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			this.end();
		}

		for(GameElement element: batch.getElementsOfType(Mesh.UNTEXTURED)) {
			
			ColoredMesh model = (ColoredMesh) element.getMesh();
			
			element.update();
			this.begin(element.getModel());
			this.shader.loadLight(light);
			this.shader.setMaterialReflectivity(model.getReflectivity(), model.getShineDamper());
			this.shader.setTextured(false);
			this.shader.setProjectionMatrix(cam.perspective());
			this.shader.setColor(model.getColor());
			this.shader.setTransformationMatrix(element.getTransformationMatrix());
			this.shader.setViewMatrix(cam.viewMatrix());
			GL11.glDrawElements(GL11.GL_TRIANGLES, element.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			this.end();
		}
		shader.unbind();
	}
}
