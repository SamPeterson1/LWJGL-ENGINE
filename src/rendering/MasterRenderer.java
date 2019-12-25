package rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import GUI.GUIComponent;
import GUI.GUIRenderer;
import camera.Camera;
import lights.DirectionalLight;
import math.Vector3f;
import models.BasicRenderer;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.ModelLoader;
import models.RawModel;
import particles.TestParticleRenderer;
import shadows.ShadowMapRenderer;
import terrain.TerrainRenderer;
import text.TextRenderer;
import window.GLFWWindow;
import window.WindowListener;

public class MasterRenderer implements WindowListener {
	
	int particleCt = 0;
	private TerrainRenderer terrainRenderer;
	private BasicRenderer renderer;
	private TextRenderer textRenderer;
	private GUIRenderer guiRenderer;
	private ShadowMapRenderer shadowRenderer;
	private SkyboxRenderer skyboxRenderer;
	private Renderer particleRenderer;
	private Renderer testParticleRenderer;
	private Camera cam;
	
	public static float NEAR_PLANE = 1f;
	public static float FAR_PLANE = 1000f;
	public static float FOV = 70f;
	
	private static final float[] rectVerts = new float[] {
			0.1f, 0.1f,
			0.1f, -0.1f,
			-0.1f, -0.1f,
			-0.1f, 0.1f
	};
	
	private static final int[] rectIndices = new int[] {
			0, 1, 2,
			0, 3, 2
	};
	
	private static final float[] rectTextCoords = new float[] {
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f,
			0.0f, 0.0f
	};
	
	private static final RawModel rectangle = ModelLoader.loadColoredGUIModel(rectVerts, rectIndices);
	private static final RawModel texturedRectangle = ModelLoader.load2DModel(rectVerts, rectTextCoords, rectIndices, true);
	
	public MasterRenderer(Light light, Camera cam, DirectionalLight directionalLight) {
		this.cam = cam;
		this.skyboxRenderer = new SkyboxRenderer(cam);
		this.shadowRenderer = new ShadowMapRenderer(cam, directionalLight);
		this.terrainRenderer = new TerrainRenderer(light, cam);
		this.renderer = new BasicRenderer(light, cam);
		this.particleRenderer = new TestParticleRenderer(cam);
		this.textRenderer = new TextRenderer();
		this.guiRenderer = new GUIRenderer();
		this.testParticleRenderer = new TestParticleRenderer(cam);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		GLFWWindow.addListener(this);
	}
	
	public static void setDoCull(boolean cull) {
		if(cull) {
			glEnable(GL_CULL_FACE);
		} else {
			glDisable(GL_CULL_FACE);
		}
	}
	
	public void render() {
		particleCt = 0;
		Map<Mesh, List<Entity>> meshesMap = ModelBatch.getEntities();
		this.cam.update();
		System.out.println(this.cam.getTransform().getRotation().getY() + "ta");
		
		this.shadowRenderer.begin();
		for(Mesh mesh: meshesMap.keySet()) {
			if(mesh.isEnabled() && mesh.getMaterial().castsShadow()) {
				List<Entity> entities = meshesMap.get(mesh);
				this.shadowRenderer.render(mesh, entities);
			}
		}
		this.shadowRenderer.end();
		
		for(Mesh mesh: meshesMap.keySet()) {
			List<Entity> entities = meshesMap.get(mesh);
			if(mesh.isEnabled()) {
				if(mesh.getType() == Mesh.TEXTURED || mesh.getType() == Mesh.UNTEXTURED) {
					this.renderer.begin();
					this.renderer.render(mesh, entities);
					this.renderer.end();
				} else if(mesh.getType() == Mesh.TERRAIN) {
					this.terrainRenderer.begin();
					this.terrainRenderer.setShadowMatrices(shadowRenderer.getLightProjectionMatrix(), shadowRenderer.getLightViewMatrix());
					this.terrainRenderer.render(mesh, entities, this.shadowRenderer.getShadowMap());
					this.terrainRenderer.end();
				} else if(mesh.getType() == Mesh.GUI_COLORED || mesh.getType() == Mesh.GUI_TEXTURED) {
					this.guiRenderer.begin();
					this.guiRenderer.render(mesh, entities);
					this.guiRenderer.end();
				} else if(mesh.getType() == Mesh.PARTICLE) {
					this.particleRenderer.begin();
					this.particleRenderer.render(mesh, entities);
					this.particleRenderer.end();
				} else if(mesh.getType() == Mesh.SKYBOX) {
					this.skyboxRenderer.begin();
					this.skyboxRenderer.render(mesh, entities);
					this.skyboxRenderer.end();
				}

			} else {
				for(Entity e: entities) {
					e.update();
				}
			}
		}
		
		for(Mesh mesh: ModelBatch.getText().keySet()) {
			if(mesh.isEnabled()) {
				List<Entity> entities = ModelBatch.getText().get(mesh);
				this.textRenderer.begin();
				this.textRenderer.render(mesh, entities);
				this.textRenderer.end();
			}
		}
		
	}

	@Override
	public void onResize(int width, int height) {
		this.cam.setAspect((float)width/(float)height);
		GL20.glViewport(0, 0, width, height);
	}
	
}
