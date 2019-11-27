package rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import models.ColoredMesh;
import models.Entity;
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
		this.shader.loadLight(light);
		this.shader.setViewMatrix(cam.viewMatrix());
		this.shader.setProjectionMatrix(cam.perspective());
		
		Map<Mesh, List<Entity>> allEntities = batch.getEntities();
		
		System.out.println("Begin render");
		
		for(Mesh mesh: allEntities.keySet()) {
			mesh.update();

			List<Entity> entities = allEntities.get(mesh);
			if(mesh.getType() == Mesh.TEXTURED) {
				this.loadTexturedMesh(mesh);
			} else if(mesh.getType() == Mesh.UNTEXTURED) {
				this.loadColoredMesh((ColoredMesh) mesh);
			}
			for(Entity e: entities) {
				System.out.println("entity rendered");
				this.renderEntity(e);
			}
			this.end();
		}
		shader.unbind();
	}
	
	private void loadColoredMesh(ColoredMesh mesh) {
		System.out.println("Colored mesh loaded");
		this.begin(mesh.getModel());
		this.shader.setMaterialReflectivity(mesh.getMaterial().getReflectivity(), mesh.getMaterial().getShineDamping());
		this.shader.setTextured(false);
		this.shader.setColor(mesh.getColor());
	}
	
	private void loadTexturedMesh(Mesh mesh) {
		System.out.println("Textured mesh loaded");
		this.begin(mesh.getModel());
		this.shader.setTextured(true);
		glActiveTexture(GL_TEXTURE0);
		mesh.getMaterial().getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
	}
	
	private void renderEntity(Entity e) {
		e.update();
		this.shader.setTransformationMatrix(e.getTransform().getTransform());
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getMesh().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
}
