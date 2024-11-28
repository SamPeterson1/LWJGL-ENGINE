package rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL20;

import GUI.GUIRenderer;
import camera.Camera;
import events.EventHandler;
import lights.PointLight;
import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.Skybox;
import particles.ParticleRenderer;
import shadows.ShadowCubemapRenderer;
import shadows.ShadowMapRenderer;
import terrain.PlanetRenderer;
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
	private PlanetRenderer planetRenderer;
	private Camera cam;
	private boolean foo;
	private int index;
	
	private static List<Texture> pointShadowMaps = new ArrayList<>();
	private static ShadowCubemapRenderer cubemapRenderer;
	
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
		Skybox skybox = new Skybox(512, "hills");
		ModelBatch.addEntity(new Entity(skybox));
		this.planetRenderer = new PlanetRenderer(cam);
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
		ShadowCubemapRenderer.init();
		Environment.pointLights.add(new PointLight(new Vector3f(50, 5, 50), new Vector3f(0f,0f, 1f), new Vector3f(0.3f, 0.02f, 0.0f), true));
		Environment.pointLights.add(new PointLight(new Vector3f(80, 5, 80), new Vector3f(1f,0f, 0f), new Vector3f(0.7f, 0.02f, 0.0f), true));
	}
	
	public static ShadowCubemapRenderer getShadowCubeMapRenderer() {
		return cubemapRenderer;
	}
	
	public static void addPointShadowMap(Texture shadowMap) {
		pointShadowMaps.add(shadowMap);
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
		this.shadowRenderer.begin();
		for(Mesh mesh: meshesMap.keySet()) {
			if(mesh.isEnabled() && mesh.getMaterial().castsShadow()) {
				List<Entity> entities = meshesMap.get(mesh);
				this.shadowRenderer.render(mesh, entities);
			}
		}
		this.shadowRenderer.end();
		
		/*
		this.cubemapRenderer.begin();
		for(Mesh mesh: meshesMap.keySet()) {
			if(mesh.isEnabled() && mesh.getMaterial().castsShadow()) {
				List<Entity> entities = meshesMap.get(mesh);
				this.cubemapRenderer.render(mesh, entities);
			}
		}
		this.cubemapRenderer.end();
		*/
		
		Vector3f camPos1 = cam.getPosition().copyOf();
		camPos1.subtract(Environment.pointLights.get(0).getPosition());
		float distToLight = camPos1.magnitude();
		System.out.println("Distance to blue light: " + distToLight);
		
		for(Mesh mesh: meshesMap.keySet()) {
			List<Entity> entities = meshesMap.get(mesh);
			for(Entity e: entities) {
				Vector3f camPos = cam.getPosition().copyOf();
				camPos.subtract(e.getTransform().getPos());
				float dist = camPos.magnitude();
				if(dist < 50 && EventHandler.keyReleased(GLFW.GLFW_KEY_M)) e.getTransform().translateZ(1);
			}
			if(mesh.isEnabled()) {
				
				for(Entity e: entities) {
					e.update();
				}
				
				if(mesh.getType() == Mesh.TEXTURED || mesh.getType() == Mesh.UNTEXTURED) {
					this.renderer.begin();
					if(mesh.getType() == Mesh.TEXTURED) {
						//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
					}
					this.setDoCull(false);
					this.renderer.render(mesh, entities);
					this.renderer.end();
					glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
				} else if(mesh.getType() == Mesh.TERRAIN) {
					this.terrainRenderer.begin();
					this.terrainRenderer.setShadowMatrices(shadowRenderer.getLightProjectionMatrix(), shadowRenderer.getLightViewMatrix());
					this.terrainRenderer.render(mesh, entities, this.shadowRenderer.getShadowMap(), pointShadowMaps.get(0));
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
				} else if(mesh.getType() == Mesh.PLANET) {
					this.planetRenderer.begin();
					this.planetRenderer.render(mesh, entities);
					this.planetRenderer.end();
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
