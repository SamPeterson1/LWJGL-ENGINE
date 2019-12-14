package misc;

import camera.Camera;
import camera.CameraSpecs;
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

		//new GUIXMLLoader("src/xml/gui.xml");
		
		CameraSpecs specs = new CameraSpecs();
		specs.setAspect(1440f/1080f);
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		cam = new Camera(specs);
		renderer = new MasterRenderer(light, cam);
		
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
		
		Terrain terrain = new Terrain(200, 200, "/assets/grass.png", noise);
		ModelBatch.addEntity(new Entity(terrain));
		
		this.particles = new ParticleSystem(new CircularEmission(100f, 10f), 100f, 0.01f, 5000, "/assets/particleAtlas.png", 15, 4);
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
		this.particles.update();
		ParticleMaster.update();
	}
		
	public void render() {
		
		long time = System.currentTimeMillis();
		renderer.render();
		System.out.println(1000f/(System.currentTimeMillis() - time));
	}
	
	public void dispose() {
		
		basicShader.remove();
		textShader.remove();
		
	}
	
}
