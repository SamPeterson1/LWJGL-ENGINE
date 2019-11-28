package rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import camera.Camera;
import models.Entity;
import models.Mesh;

public class MasterRenderer implements WindowListener {
	
	private IRenderer activeRenderer;
	private TerrainRenderer terrainRenderer;
	private BasicRenderer renderer;
	private GUIRenderer guiRenderer;
	private Light light;
	private Camera cam;
	
	public MasterRenderer(Light light, Camera cam) {
		this.light = light;
		this.cam = cam;
		this.terrainRenderer = new TerrainRenderer(light, cam);
		this.renderer = new BasicRenderer(light, cam);
		this.guiRenderer = new GUIRenderer();
		this.activeRenderer = renderer;
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		GLFWWindow.addListener(this);
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
			} else if(mesh.getType() == Mesh.GUI) {
				this.activateRenderer(guiRenderer);
				this.guiRenderer.loadMesh(mesh);
			}
			
			for(Entity e: entities) {
				this.renderEntity(e);
			}
		}
		
		this.activeRenderer.unloadMesh();
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

	@Override
	public void onResize(int width, int height) {
		this.cam.setAspect((float)width/(float)height);
		GL20.glViewport(0, 0, width, height);
	}
	
}
