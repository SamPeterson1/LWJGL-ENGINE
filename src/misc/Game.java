package misc;

import GUI.MasterGUI;
import camera.Camera;
import camera.CameraSpecs;
import events.EventHandler;
import lights.DirectionalLight;
import math.Noise;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.Skybox;
import models.TexturedMesh;
import particles.CircularEmission;
import particles.ParticleMaster;
import particles.ParticleSystem;
import rendering.Light;
import rendering.MasterRenderer;
import shaders.BasicShader;
import shaders.TextShader;
import terrain.Terrain;
import window.GLFWWindow;

public class Game {
	
	private Light light;
	private Camera cam;
	private MasterRenderer renderer;
	private BasicShader basicShader;
	private TextShader textShader;
	private ParticleSystem particles;
	
	public void init() {
		
		GLFWWindow.init(1440, 1080, "Test");
		
		light = new Light(new Vector3f(1f, 1f, 1f), new Vector3f(-1000f, 1000f, 100f));
		DirectionalLight foo = new DirectionalLight(new Vector3f(0f, 10f, 0f), new Vector3f(74, 74, 74), new Vector3f(1f, 1f, 1f));
		
		CameraSpecs specs = new CameraSpecs();
		specs.setAspect(1440f/1080f);
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		cam = new Camera(specs);
		renderer = new MasterRenderer(light, cam, foo);
		
		Noise noise = new Noise(130);
		
		Time.setCap(100);
		ColoredMesh coloredMesh = new ColoredMesh("/assets/dragon.obj", new Vector3f(1f, 0f, 0f));
		coloredMesh.setReflectivity(0.5f);
		coloredMesh.setShineDamping(10f);
		Mesh texturedMesh = new TexturedMesh("/assets/stall.obj", "/assets/stallTexture.png");
		Entity element2 = new Entity(texturedMesh);
		element2.getMesh().getMaterial().setReflectivity(0.5f);
		element2.getMesh().getMaterial().setShineDamping(10);
		Entity element3 = new Entity(texturedMesh);
		element3.getMesh().getMaterial().setReflectivity(0.5f);
		element3.getMesh().getMaterial().setShineDamping(10);
		element2.getTransform().setTranslationZ(-5);
		element2.getTransform().setRotationY(90);
		element2.getTransform().setScale(new Vector3f(0.5f, 0.5f, 0.5f));
		element3.getTransform().setScale(new Vector3f(0.5f, 0.5f, 0.5f));
		element3.getTransform().setTranslationZ(3);
		
		Skybox skybox = new Skybox(500, "hills");
		ModelBatch.addEntity(new Entity(skybox));
		
		Terrain terrain = new Terrain("/assets/grass.png", noise);
		cam.setTerrain(terrain);
		cam.getTransform().setRotation(new Vector3f(-90f, 90f, 0f));
		cam.getPosition().setZ(20);
		
		terrain.getTransform().setTranslationX(10f);
		ModelBatch.addEntity(terrain);
		
		this.particles = new ParticleSystem(new CircularEmission(100f, 10f), 200, 1, 0f, 5000, "/assets/fireAtlas.png", 16, 4);
		EventHandler.enableCursor();
		
		
	}
		
	public void render() {
		
		cam.update();
		long time = System.currentTimeMillis();
		renderer.render();
		MasterGUI.updateComponents();
		ParticleMaster.update();
		System.out.println(1000f/(System.currentTimeMillis() - time));
	}
	
	public void dispose() {
		
		basicShader.remove();
		textShader.remove();
		
	}
	
}
