package rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import GUI.GUIRenderer;
import camera.Camera;
import lights.PointLight;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.Skybox;
import particles.ParticleRenderer;
import shadows.ShadowCubemapRenderer;
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
	private ParticleRenderer particleRenderer;
	private ShadowCubemapRenderer cubemapRenderer;
	private Camera cam;
	private boolean foo;
	private int index;
	
	public static float NEAR_PLANE = 1f;
	public static float FAR_PLANE = 1000f;
	public static float FOV = 70f;
	
	
	private static final float[] rectVerts = new float[] {
			1f, 1f,
			1f, -1f,
			-1f, -1f,
			-1f, 1f
	};
	
	public MasterRenderer(Camera cam) {
		this.cam = cam;
		Skybox skybox = new Skybox(100, "hills");
		ModelBatch.addEntity(new Entity(skybox));
		this.skyboxRenderer = new SkyboxRenderer(cam);
		this.shadowRenderer = new ShadowMapRenderer(cam);		
		this.terrainRenderer = new TerrainRenderer(cam);
		this.renderer = new BasicRenderer(cam);
		this.particleRenderer = new ParticleRenderer(cam);
		this.textRenderer = new TextRenderer();
		this.guiRenderer = new GUIRenderer();
		this.particleRenderer = new ParticleRenderer(cam);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		GLFWWindow.addListener(this);
		Environment.pointLights.add(new PointLight(new Vector3f(50, 5, 50), new Vector3f(0f,0f, 1f), new Vector3f(0.7f, 0.02f, 0.0f)));
		this.cubemapRenderer = new ShadowCubemapRenderer(skybox, Environment.pointLights.get(0), cam);
		Environment.pointLights.add(new PointLight(new Vector3f(80, 5, 80), new Vector3f(1f,0f, 0f), new Vector3f(0.7f, 0.02f, 0.0f)));
		//ColoredMesh foo = new ColoredMesh("/assets/tree.obj", new Vector3f(1f, 1f, 1f));
		//Entity foo2 = new Entity(foo);
		////foo2.getTransform().setTranslation(new Vector3f(5f, 5f, 5f));
		//ModelBatch.addEntity(foo2);
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
		
		
		this.cubemapRenderer.begin();
		for(Mesh mesh: meshesMap.keySet()) {
			if(mesh.isEnabled() && mesh.getMaterial().castsShadow()) {
				System.out.println("GOPO");
				List<Entity> entities = meshesMap.get(mesh);
				this.cubemapRenderer.render(mesh, entities);
			}
		}
		this.cubemapRenderer.end();
		
		
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
					this.terrainRenderer.render(mesh, entities, this.shadowRenderer.getShadowMap(), this.cubemapRenderer.getTexture());
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
