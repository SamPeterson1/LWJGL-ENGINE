package misc;

import java.util.ArrayList;
import java.util.List;

import GUI.MasterGUI;
import camera.Camera;
import camera.CameraSpecs;
import events.EventHandler;
import math.Noise;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.TexturedMesh;
import particles.CircularEmission;
import particles.ParticleMaster;
import particles.ParticleSystem;
import player.Player;
import rendering.MasterRenderer;
import shaders.BasicShader;
import shaders.TextShader;
import terrain.Terrain;
import terrain.TerrainSphere;
import window.GLFWWindow;
import xml.GUIXMLLoader;

public class Game {
	
	private Camera cam;
	private MasterRenderer renderer;
	private BasicShader basicShader;
	private TextShader textShader;
	private ParticleSystem particles;
	private Player player;
	
	private static List<FrameListener> frameListeners = new ArrayList<>();
	
	public void init() {
		
		
		GLFWWindow.init(1920, 1080, "Test");
		CameraSpecs specs = new CameraSpecs();
		new GUIXMLLoader("xml/fpsCount.xml");
		this.player = new Player();
		specs.setAspect(1920/1080f);
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		cam = new Camera(specs);
		
		Noise noise = new Noise(130);
		
		Time.setCap(100);
		ColoredMesh coloredMesh = new ColoredMesh("dragon.obj", new Vector3f(1f, 0f, 0f));
		coloredMesh.setReflectivity(0.5f);
		coloredMesh.setShineDamping(10f);
		Mesh texturedMesh = new TexturedMesh("stall.obj", "stallTexture.png");
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
		
		Terrain terrain = new Terrain("grassTexture.png", noise);
		terrain.enable();
		cam.setTerrain(terrain);
		cam.getTransform().setRotation(new Vector3f(-90f, 90f, 0f));
		cam.getPosition().setZ(20);
		
		//ModelBatch.addEntity(new Entity(new TerrainSphere(25)));
		
		//terrain.getTransform().setTranslationX(10f);
		ModelBatch.addEntity(terrain);
		ModelBatch.addEntity(element2);
		ModelBatch.addEntity(element3);
		renderer = new MasterRenderer(cam);
		this.particles = new ParticleSystem(new CircularEmission(100f, 10f), 200, 1, 0f, 5000, "fireAtlas.png", 16, 4);
		ParticleMaster.addParticleSystem(particles);
		EventHandler.enableCursor();
		
		
	}
	
	public static void addFrameListener(FrameListener listener) {
		frameListeners.add(listener);
	}
		
	public void render() {
		
		for(FrameListener listener: frameListeners) {
			listener.beginFrame();
		}
		
		cam.update();
		long time = System.currentTimeMillis();
		renderer.render();
		MasterGUI.updateComponents();
		ParticleMaster.update();
		player.update();
		System.out.println("Oxygen: " + player.getOxygen() + "\nHealth: " + player.getHealth());
		System.out.println(1000f/(System.currentTimeMillis() - time));
		
		for(FrameListener listener: frameListeners) {
			listener.endFrame();
		}
		
	}
	
	public void dispose() {
		basicShader.remove();
		textShader.remove();
	}
	
}
