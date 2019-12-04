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

import GUI.DropdownBox;
import GUI.GUIRenderer;
import camera.Camera;
import models.BasicRenderer;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import terrain.TerrainRenderer;
import text.TextRenderer;
import window.GLFWWindow;
import window.WindowListener;

public class MasterRenderer implements WindowListener {
	
	
	private Renderer activeRenderer;
	private TerrainRenderer terrainRenderer;
	private BasicRenderer renderer;
	private TextRenderer textRenderer;
	private GUIRenderer guiRenderer;
	private Camera cam;
	
	public MasterRenderer(Light light, Camera cam) {
		this.cam = cam;
		this.terrainRenderer = new TerrainRenderer(light, cam);
		this.renderer = new BasicRenderer(light, cam);
		this.textRenderer = new TextRenderer();
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
	
	public void render() {
		Map<Mesh, List<Entity>> meshesMap = ModelBatch.getEntities();
		this.cam.update();
		this.activeRenderer.begin();
		for(Mesh mesh: meshesMap.keySet()) {
			List<Entity> entities = meshesMap.get(mesh);
			if(mesh.isEnabled()) {
				this.activeRenderer.unloadMesh();
				if(mesh.getType() == Mesh.TEXTURED) {
					this.activateRenderer(renderer);
					this.renderer.loadMesh(mesh);
				} else if(mesh.getType() == Mesh.UNTEXTURED) {
					this.activateRenderer(renderer);
					this.renderer.loadMesh(mesh);
				} else if(mesh.getType() == Mesh.TERRAIN) {
					this.activateRenderer(terrainRenderer);
					this.terrainRenderer.loadMesh(mesh);
				} else if(mesh.getType() == Mesh.GUI_COLORED || mesh.getType() == Mesh.GUI_TEXTURED) {
					this.activateRenderer(guiRenderer);
					this.guiRenderer.loadMesh(mesh);
				}
				
				for(Entity e: entities) {
					this.renderEntity(e);
				}
			} else {
				for(Entity e: entities) {
					if(e.getMesh() instanceof DropdownBox) {
						System.out.println("sending update");
					}
					e.update();
				}
			}
		}
		
		this.activeRenderer.unloadMesh();
		this.activeRenderer.end();
		
		this.activateRenderer(textRenderer);
		for(Entity e: ModelBatch.getText()) {
			if(e.getMesh().isEnabled()) {
				textRenderer.loadMesh(e.getMesh());
				this.renderEntity(e);
			} else {
				e.update();
			}
		}
	}
	
	private void renderEntity(Entity e) {
		e.update();
		this.activeRenderer.setTransformationMatrix(e.getTransform().getTransform());
		GL11.glDrawElements(GL11.GL_TRIANGLES, e.getMesh().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	private void activateRenderer(Renderer renderer) {
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
