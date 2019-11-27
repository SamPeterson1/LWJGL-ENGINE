package rendering;

import camera.Camera;
import camera.CameraSpecs;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.TextMesh;
import models.TexturedMesh;
import shaders.BasicShader;
import shaders.TextShader;
import terrain.Terrain;

public class Game {
	
	private ModelBatch batch;
	private TextMesh textMesh;
	private Light light;
	private Camera cam;
	private MasterRenderer renderer;
	private TextRenderer textRenderer;
	private BasicShader basicShader;
	private TextShader textShader;
	
	public void init() {
		
		GLFWWindow.init(1440, 1080, "Test");

		light = new Light(new Vector3f(1f, 1f, 1f), new Vector3f(200f, 200f, 100f));

		CameraSpecs specs = new CameraSpecs();
		specs.setAspect((float)GLFWWindow.getWidth()/(float)GLFWWindow.getHeight());
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		cam = new Camera(specs);
		renderer = new MasterRenderer(light, cam);
		
		Mesh terrainMesh = new Terrain(1, 1, "/assets/grass.png");
		Entity terrain = new Entity(terrainMesh);
		
		Time.setCap(100);
		ColoredMesh coloredMesh = new ColoredMesh("src/assets/dragon.obj", new Vector3f(1f, 0f, 0f));
		coloredMesh.setReflectivity(0.5f);
		coloredMesh.setShineDamping(10f);
		Mesh texturedMesh = new TexturedMesh("src/assets/stall.obj", "/assets/stallTexture.png");
		Entity element1 = new Entity(coloredMesh);
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

		textMesh = new TextMesh("testing123", 50, 50, 0.3f, new Vector3f(1f, 0f, 0f), "src/assets/TestFont.fnt");
		TextShader textShader = new TextShader();
		textShader.create();
		textRenderer = new TextRenderer(textShader);
		
		batch = new ModelBatch(terrain, element1, element2, element3);
		
	}
	
	public void render() {
		renderer.render(batch);
		textRenderer.renderText(textMesh);
	}
	
	public void dispose() {
		
		basicShader.remove();
		textShader.remove();
		
	}
	
}
