package rendering;

import GUI.AspectConstraint;
import GUI.Constraint;
import GUI.GUIComponent;
import GUI.MasterGUI;
import GUI.PixelConstraint;
import GUI.RelativeConstraint;
import camera.Camera;
import camera.CameraSpecs;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.Text;
import models.TexturedMesh;
import shaders.BasicShader;
import shaders.TextShader;
import terrain.Terrain;

public class Game {
	
	private ModelBatch batch;
	private Text textMesh;
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
				
		
		MasterGUI gui = new MasterGUI();
		gui.setEntity(new Entity(gui));
		
		GUIComponent container = new GUIComponent(new Vector3f(0f, 1f, 0f), 0.2f, "container");
		container.addConstraint(new RelativeConstraint(1f, Constraint.HEIGHT));
		container.addConstraint(new AspectConstraint(0.33f, Constraint.WIDTH));
		container.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		container.addConstraint(new AspectConstraint(0.33f, Constraint.X));
		GUIComponent bottom = new GUIComponent(new Vector3f(0.8f, 0.8f, 0.8f), 0.1f, "bottom");
		bottom.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		bottom.addConstraint(new RelativeConstraint(0.05f, Constraint.Y));
		bottom.addConstraint(new RelativeConstraint(0.7f, Constraint.WIDTH));
		bottom.addConstraint(new RelativeConstraint(0.05f, Constraint.HEIGHT));
		GUIComponent box1 = new GUIComponent(new Vector3f(1f, 1f, 0f), 0.1f, "box1");
		box1.addConstraint(new RelativeConstraint(0.3f, Constraint.X));
		box1.addConstraint(new RelativeConstraint(0.2f, Constraint.Y));
		box1.addConstraint(new RelativeConstraint(0.3f, Constraint.WIDTH));
		box1.addConstraint(new AspectConstraint(1f, Constraint.HEIGHT));
		
		GUIComponent box2 = new GUIComponent(new Vector3f(1f, 1f, 0f), 0.1f, "box2");
		box2.addConstraint(new RelativeConstraint(0.7f, Constraint.X));
		box2.addConstraint(new RelativeConstraint(0.2f, Constraint.Y));
		box2.addConstraint(new RelativeConstraint(0.3f, Constraint.WIDTH));
		box2.addConstraint(new AspectConstraint(1f, Constraint.HEIGHT));
		
		GUIComponent container2 = new GUIComponent(new Vector3f(1f, 1f, 0f), 0.1f, "box2");
		container2.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		container2.addConstraint(new RelativeConstraint(0.63f, Constraint.Y));
		container2.addConstraint(new RelativeConstraint(0.7f, Constraint.WIDTH));
		container2.addConstraint(new RelativeConstraint(0.7f, Constraint.HEIGHT));
		
		GUIComponent box3 = new GUIComponent(new Vector3f(1f, 0f, 0f), 0.05f, "box3");
		box3.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		box3.addConstraint(new RelativeConstraint(0.75f, Constraint.Y));
		box3.addConstraint(new RelativeConstraint(0.7f, Constraint.WIDTH));
		box3.addConstraint(new RelativeConstraint(0.4f, Constraint.HEIGHT));
		
		GUIComponent box4 = new GUIComponent(new Vector3f(1f, 0f, 0f), 0.05f, "box4");
		box4.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		box4.addConstraint(new RelativeConstraint(0.25f, Constraint.Y));
		box4.addConstraint(new RelativeConstraint(0.7f, Constraint.WIDTH));
		box4.addConstraint(new RelativeConstraint(0.4f, Constraint.HEIGHT));
		
		container.setEntity(new Entity(container));
		bottom.setEntity(new Entity(bottom));
		box1.setEntity(new Entity(box1));
		box2.setEntity(new Entity(box2));
		box3.setEntity(new Entity(box3));
		box4.setEntity(new Entity(box4));
		container2.setEntity(new Entity(container2));
		
		container.addChild(bottom);
		container.addChild(box1);
		container.addChild(box2);
		container.addChild(container2);
		container2.addChild(box3);
		container2.addChild(box4);
		gui.addComponent(container);
		
		textMesh = new Text("testing123", 50, 50, 0.3f, new Vector3f(1f, 0f, 0f), "/assets/TestFont.fnt");
		textMesh.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		textMesh.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		textMesh.addConstraint(new RelativeConstraint(17f, Constraint.HEIGHT));
		textMesh.addConstraint(new AspectConstraint(1.9f, Constraint.WIDTH));
		textMesh.setEntity(new Entity(textMesh));
		bottom.addChild(textMesh);
		
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
		//TextShader textShader = new TextShader();
		//textShader.create();
		//textRenderer = new TextRenderer(textShader);
		
		TexturedMesh tree = new TexturedMesh("/assets/tree.obj", "/assets/tree.png");
		TexturedMesh fern = new TexturedMesh("/assets/fern.obj", "/assets/fern.png");
		TexturedMesh grass = new TexturedMesh("/assets/grassModel.obj", "/assets/grassTexture.png");
		
		fern.setCullFace(false);
		grass.setCullFace(false);
		grass.setUseFakeLighting(true);
		
		batch = new ModelBatch(container.getEntity(), bottom.getEntity(), container2.getEntity(), box1.getEntity(), box2.getEntity(), box3.getEntity(), box4.getEntity(), textMesh.getEntity(), terrain, element1, element2);
		
		
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
			Entity fernEntity = new Entity(fern);
			fernEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			batch.addEntity(fernEntity);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity grassEntity2 = new Entity(grass);
			grassEntity2.getTransform().setTranslation(new Vector3f(x, 0, z));
			batch.addEntity(grassEntity2);
		}
		
		batch.addEntity(textMesh.getEntity());
	}
	
	public void render() {
		renderer.render(batch);
	}
	
	public void dispose() {
		
		basicShader.remove();
		textShader.remove();
		
	}
	
}
