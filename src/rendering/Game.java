package rendering;

import org.lwjgl.opengl.GL20;

import GUI.GUIComponent;
import camera.Camera;
import camera.CameraSpecs;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.ModelLoader;
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

		light = new Light(new Vector3f(1f, 1f, 1f), new Vector3f(-1000f, 1000f, 100f));

		CameraSpecs specs = new CameraSpecs();
		specs.setAspect(1440f/1080f);
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		cam = new Camera(specs);
		renderer = new MasterRenderer(light, cam);
		
		Mesh terrainMesh = new Terrain(1, 1, "/assets/grass.png");
		Entity terrain = new Entity(terrainMesh);
		
		float[] rectVerts = new float[] {
				0.5f, 0.5f, 0f,
				0.5f, -0.5f, 0f,
				-0.5f, -0.5f, 0f,
				-0.5f, 0.5f, 0f
		};
		
		float[] rectVerts2 = new float[] {
				0.25f, 0.25f, -1f,
				0.25f, -0.25f, -1f,
				-0.25f, -0.25f, -1f,
				-0.25f, 0.25f, -1f
		};
		
		int[] rectIndices2 = new int[] {
				0, 1, 2,
				2, 0, 3
		};
		
		int[] rectIndices = new int[] {
				0, 2, 1,
				2, 0, 3
		};
				
		
		GUIComponent test = new GUIComponent(new Vector3f(1f, 1f, 0f), 100, 0, 0.4f);
		GUIComponent test2 = new GUIComponent(new Vector3f(1f, 0f, 0f), 0, 0, 0.5f);
		
		Time.setCap(100);
		ColoredMesh coloredMesh = new ColoredMesh("/assets/dragon.obj", new Vector3f(1f, 0f, 0f));
		coloredMesh.setReflectivity(0.5f);
		coloredMesh.setShineDamping(10f);
		Mesh texturedMesh = new TexturedMesh("/assets/stall.obj", "/assets/stallTexture.png");
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

		//textMesh = new TextMesh("testing123", 50, 50, 0.3f, new Vector3f(1f, 0f, 0f), "/assets/TestFont.fnt");
		//TextShader textShader = new TextShader();
		//textShader.create();
		//textRenderer = new TextRenderer(textShader);
		
		TexturedMesh tree = new TexturedMesh("/assets/tree.obj", "/assets/tree.png");
		TexturedMesh fern = new TexturedMesh("/assets/fern.obj", "/assets/fern.png");
		TexturedMesh grass = new TexturedMesh("/assets/grassModel.obj", "/assets/grassTexture.png");
		
		fern.setCullFace(false);
		grass.setCullFace(false);
		grass.setUseFakeLighting(true);
		
		batch = new ModelBatch(test.getEntity(), test2.getEntity(), terrain, element1, element2, element3);
		
		for(int i = 0; i < 800; i ++) {
			float x = (float) (Math.random() * 800);
			float z = (float) (Math.random() * 800);
			Entity treeEntity = new Entity(tree);
			treeEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			treeEntity.getTransform().setScale(new Vector3f(2, 2, 2));
			batch.addEntity(treeEntity);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity grassEntity = new Entity(grass);
			grassEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			batch.addEntity(grassEntity);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity grassEntity2 = new Entity(grass);
			grassEntity2.getTransform().setTranslation(new Vector3f(x, 0, z));
			batch.addEntity(grassEntity2);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity fernEntity = new Entity(fern);
			fernEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			batch.addEntity(fernEntity);
		}
		
	}
	
	public void render() {
		renderer.render(batch);
	}
	
	public void dispose() {
		
		basicShader.remove();
		textShader.remove();
		
	}
	
}
