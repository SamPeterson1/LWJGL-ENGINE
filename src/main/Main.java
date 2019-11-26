package main;

import GUI.Font;
import GUI.TextRenderer;
import GUI.TextMesh;
import GUI.TextMeshGenerator;
import camera.Camera;
import camera.CameraSpecs;
import files.FontFile;
import files.OBJFile;
import math.Vector3f;
import models.GameElement;
import models.Mesh;
import models.Renderer;
import rendering.GLFWWindow;
import rendering.Light;
import rendering.ModelBatch;
import rendering.Time;
import shaders.BasicShader;
import shaders.TextShader;

public class Main {
	
	public static void main(String[] args) {
		
		GLFWWindow.init(1440, 1080, "Test");
		
		OBJFile test = new OBJFile("src/assets/dragon.obj");
		Mesh foot = test.read();
		Light light = new Light(new Vector3f(1, 1, 0), new Vector3f(100f, 0, 100f));
		
		BasicShader shader = new BasicShader();

		CameraSpecs specs = new CameraSpecs();
		specs.setAspect(1440f/1080f);
		specs.setFov(70f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		Camera cam = new Camera(specs);
		shader.create();
		Renderer renderer = new Renderer(shader, cam, light);
		
		Time.setCap(100);
		
		GameElement element1 = new GameElement(foot);
		//GameElement element2 = new GameElement(TerrainGenerator.generateMesh());
		TextMeshGenerator textGen = new TextMeshGenerator("src/assets/TestFont.fnt");
		TextMesh meshh = textGen.genMesh("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 0, 200, 0.2f);
			
		TextShader textShader = new TextShader();
		textShader.create();
		TextRenderer textRenderer = new TextRenderer(textShader);
		
		//element1.getTransform().setTranslationZ(-2f);
		//element2.getMesh().setColor(new Vector3f(1f, 0.5f, 0.2f));
		//element2.getTransform().setTranslationZ(-2f);
		//element1.getTransform().setTranslationX(-0.7f);
		//element2.getTransform().setTranslationX(0.7f);
		
		element1.getTransform().setTranslationY(-8f);
		element1.getTransform().setTranslationX(20f);
		element1.getTransform().setTranslationZ(-5f);
		
		ModelBatch batch = new ModelBatch(element1);
		
		while(!GLFWWindow.closed()) {
			Time.beginFrame();
			GLFWWindow.update();
			//renderer.renderTexturedModel(model2);
			renderer.renderElements(batch);
			textRenderer.renderText(meshh);
			GLFWWindow.swapBuffer();
			Time.waitForNextFrame();
		}
		
		shader.remove();
	}

}
