package rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import camera.Camera;
import models.Entity;
import models.Mesh;

public class MasterRenderer {
	
	private IRenderer activeRenderer;
	private TerrainRenderer terrainRenderer;
	private BasicRenderer renderer;
	private Light light;
	private Camera cam;
	
	public MasterRenderer(Light light, Camera cam) {
		this.light = light;
		this.cam = cam;
		this.terrainRenderer = new TerrainRenderer(light, cam);
		this.renderer = new BasicRenderer(light, cam);
		this.activeRenderer = renderer;
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	public static void setDoCull(boolean cull) {
		if(cull) {
			glEnable(GL_CULL_FACE);
			glCullFace(GL_BACK);
		} else {
			glDisable(GL_CULL_FACE);
		}
	}
	
	public void render(ModelBatch batch) {
		
		Map<Mesh, List<Entity>> meshesMap = batch.getEntities();
		this.cam.update();
		this.activeRenderer.begin();
		for(Mesh mesh: meshesMap.keySet()) {
			this.activeRenderer.unloadMesh();
			List<Entity> entities = meshesMap.get(mesh);
			if(mesh.getType() == Mesh.TEXTURED) {
				this.activateRenderer(renderer);
				this.renderer.loadMesh(mesh);
			} else if(mesh.getType() == Mesh.UNTEXTURED) {
				this.activateRenderer(renderer);
				this.renderer.loadMesh(mesh);
			} else if(mesh.getType() == Mesh.TERRAIN) {
				this.activateRenderer(terrainRenderer);
				this.terrainRenderer.loadMesh(mesh);
			}
			
			for(Entity e: entities) {
				this.renderEntity(e);
			}
		}
		
		this.activeRenderer.end();
		
	}
	
	private void renderEntity(Entity e) {
		e.update();
		this.activeRenderer.setTransformationMatrix(e.getTransform().getTransform());
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getMesh().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	private void activateRenderer(IRenderer renderer) {
		if(this.activeRenderer != renderer) {
			this.activeRenderer.end();
			this.activeRenderer = renderer;
			this.activeRenderer.begin();
		}
	}
	
}
