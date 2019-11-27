package main;

import GUI.TextRenderer;
import camera.Camera;
import camera.CameraSpecs;
import math.Vector3f;
import models.ColoredMesh;
import models.GameElement;
import models.Renderer;
import models.TextMesh;
import models.TexturedMesh;
import rendering.GLFWWindow;
import rendering.Light;
import rendering.ModelBatch;
import rendering.Time;
import shaders.BasicShader;
import shaders.TextShader;

public class Main {
	
	public static void main(String[] args) {
		
		GLFWWindow.init(1440, 1080, "Test");

		Light light = new Light(new Vector3f(1f, 1f, 1f), new Vector3f(100f, 0, 100f));
		
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
 
		GameElement element1 = new GameElement(new TexturedMesh("src/assets/stall.obj", "/assets/stallTexture.png"));
		GameElement element2 = new GameElement(new ColoredMesh("src/assets/dragon.obj", new Vector3f(1f, 0f, 0f)));
		GameElement element3= new GameElement(new ColoredMesh("src/assets/dragon.obj", new Vector3f(0f, 1f, 0f)));
		element2.getTransform().setTranslationZ(-5);
		element2.getTransform().setRotationY(90);
		element2.getTransform().setScale(new Vector3f(0.5f, 0.5f, 0.5f));
		element3.getTransform().setScale(new Vector3f(0.5f, 0.5f, 0.5f));
		element3.getTransform().setTranslationZ(3);

		TextMesh mesh = new TextMesh("testing123", 50, 50, 0.3f, new Vector3f(1f, 0f, 0f), "src/assets/TestFont.fnt");
		TextShader textShader = new TextShader();
		textShader.create();
		TextRenderer textRenderer = new TextRenderer(textShader);
		
		ModelBatch batch = new ModelBatch(element1, element2, element3);
		
		while(!GLFWWindow.closed()) {
			Time.beginFrame();
			GLFWWindow.update();
			renderer.renderElements(batch);
			textRenderer.renderText(mesh);
			GLFWWindow.swapBuffer();
			Time.waitForNextFrame();
		}
		
		shader.remove();
	}

}
