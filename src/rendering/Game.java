package rendering;

import GUI.Button;
import GUI.MasterGUI;
import camera.Camera;
import camera.CameraSpecs;
import events.EventListener;
import math.Vector3f;
import models.ColoredMesh;
import models.Entity;
import models.Mesh;
import models.TexturedMesh;
import shaders.BasicShader;
import shaders.TextShader;
import xml.GUIXMLLoader;

public class Game {
	
	private Light light;
	private Camera cam;
	private MasterRenderer renderer;
	private BasicShader basicShader;
	private TextShader textShader;
	
	public void init() {
		
		GLFWWindow.init(1440, 1080, "Test");
		
		light = new Light(new Vector3f(1f, 1f, 1f), new Vector3f(-1000f, 1000f, 100f));

		new GUIXMLLoader("src/xml/gui.xml");
		
		CameraSpecs specs = new CameraSpecs();
		specs.setAspect(1440f/1080f);
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		cam = new Camera(specs);
		renderer = new MasterRenderer(light, cam);
		
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
		
		TexturedMesh tree = new TexturedMesh("/assets/tree.obj", "/assets/tree.png");
		TexturedMesh fern = new TexturedMesh("/assets/fern.obj", "/assets/fern.png");
		TexturedMesh grass = new TexturedMesh("/assets/grassModel.obj", "/assets/grassTexture.png");
		
		fern.setCullFace(false);
		grass.setCullFace(false);
		grass.setUseFakeLighting(true);
		
		
		for(int i = 0; i < 800; i ++) {
			float x = (float) (Math.random() * 800);
			float z = (float) (Math.random() * 800);
			Entity treeEntity = new Entity(tree);
			treeEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			treeEntity.getTransform().setScale(new Vector3f(2, 2, 2));
			ModelBatch.addEntity(treeEntity);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity grassEntity = new Entity(grass);
			grassEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			ModelBatch.addEntity(grassEntity);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity fernEntity = new Entity(fern);
			fernEntity.getTransform().setTranslation(new Vector3f(x, 0, z));
			ModelBatch.addEntity(fernEntity);
			
			x = (float) (Math.random() * 800);
			z = (float) (Math.random() * 800);
			Entity grassEntity2 = new Entity(grass);
			grassEntity2.getTransform().setTranslation(new Vector3f(x, 0, z));
			ModelBatch.addEntity(grassEntity2);
		}
	}
	
	public void render() {
		System.out.println(((Button)MasterGUI.getComponent("button1")).pressed());
		renderer.render();
		EventListener.endFrame();
	}
	
	public void dispose() {
		
		basicShader.remove();
		textShader.remove();
		
	}
	
}
