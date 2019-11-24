package main;

import camera.Camera;
import camera.CameraSpecs;
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

public class Main {
	
	public static void main(String[] args) {
		
		GLFWWindow.init(1440, 1080, "Test");
		
		OBJFile test = new OBJFile("src/assets/dragon.obj");
		Mesh foot = test.read();
		Light light = new Light(new Vector3f(1, 1, 0), new Vector3f(0, 0, 10f));
		
		BasicShader shader = new BasicShader();

		CameraSpecs specs = new CameraSpecs();
		specs.setAspect(1440f/1080f);
		specs.setFov(50f);
		specs.setzFar(1000f);
		specs.setzNear(0.01f);
		
		Camera cam = new Camera(specs);
		shader.create();
		Renderer renderer = new Renderer(shader, cam, light);
		
		Mesh model = new Mesh(new float[] {
				-0.5f, 0.5f, 0.5f,
	            // V1
	            -0.5f, -0.5f, 0.5f,
	            // V2
	            0.5f, -0.5f, 0.5f,
	            // V3
	            0.5f, 0.5f, 0.5f,
	            // V4
	            -0.5f, 0.5f, -0.5f,
	            // V5
	            0.5f, 0.5f, -0.5f,
	            // V6
	            -0.5f, -0.5f, -0.5f,
	            // V7
	            0.5f, -0.5f, -0.5f,
	            // For text coords in top face
	            // V8: V4 repeated
	            -0.5f, 0.5f, -0.5f,
	            // V9: V5 repeated
	            0.5f, 0.5f, -0.5f,
	            // V10: V0 repeated
	            -0.5f, 0.5f, 0.5f,
	            // V11: V3 repeated
	            0.5f, 0.5f, 0.5f,
	            // For text coords in right face
	            // V12: V3 repeated
	            0.5f, 0.5f, 0.5f,
	            // V13: V2 repeated
	            0.5f, -0.5f, 0.5f,
	            // For text coords in left face
	            // V14: V0 repeated
	            -0.5f, 0.5f, 0.5f,
	            // V15: V1 repeated
	            -0.5f, -0.5f, 0.5f,
	            // For text coords in bottom face
	            // V16: V6 repeated
	            -0.5f, -0.5f, -0.5f,
	            // V17: V7 repeated
	            0.5f, -0.5f, -0.5f,
	            // V18: V1 repeated
	            -0.5f, -0.5f, 0.5f,
	            // V19: V2 repeated
	            0.5f, -0.5f, 0.5f
		},
		new int[] {
				// Front face
	            0, 1, 3, 3, 1, 2,
	            // Top Face
	            4, 10, 11, 9, 8, 11,
	            // Right face
	            12, 13, 7, 5, 12, 7,
	            // Left face
	            14, 15, 6, 4, 14, 6,
	            // Bottom face
	            16, 18, 19, 17, 16, 19,
	            // Back face
	            4, 6, 7, 5, 4, 7,
		}, new Vector3f(1f, 0f, 1f));
		
		/*
		Mesh model2 = new Mesh(new float[] {
				-0.5f, 0.5f, 0.5f,
	            // V1
	            -0.5f, -0.5f, 0.5f,
	            // V2
	            0.5f, -0.5f, 0.5f,
	            // V3
	            0.5f, 0.5f, 0.5f,
	            // V4
	            -0.5f, 0.5f, -0.5f,
	            // V5
	            0.5f, 0.5f, -0.5f,
	            // V6
	            -0.5f, -0.5f, -0.5f,
	            // V7
	            0.5f, -0.5f, -0.5f,
	            // For text coords in top face
	            // V8: V4 repeated
	            -0.5f, 0.5f, -0.5f,
	            // V9: V5 repeated
	            0.5f, 0.5f, -0.5f,
	            // V10: V0 repeated
	            -0.5f, 0.5f, 0.5f,
	            // V11: V3 repeated
	            0.5f, 0.5f, 0.5f,
	            // For text coords in right face
	            // V12: V3 repeated
	            0.5f, 0.5f, 0.5f,
	            // V13: V2 repeated
	            0.5f, -0.5f, 0.5f,
	            // For text coords in left face
	            // V14: V0 repeated
	            -0.5f, 0.5f, 0.5f,
	            // V15: V1 repeated
	            -0.5f, -0.5f, 0.5f,
	            // For text coords in bottom face
	            // V16: V6 repeated
	            -0.5f, -0.5f, -0.5f,
	            // V17: V7 repeated
	            0.5f, -0.5f, -0.5f,
	            // V18: V1 repeated
	            -0.5f, -0.5f, 0.5f,
	            // V19: V2 repeated
	            0.5f, -0.5f, 0.5f
		},
		new float[] {
				 0.0f, 0.0f,
		            0.0f, 1f,
		            1f, 1f,
		            1f, 0.0f,
		            0.0f, 0.0f,
		            1f, 0.0f,
		            0.0f, 1f,
		            1f, 1f,
		            // For text coords in top face
		            0.0f, 1f,
		            1f, 1f,
		            0.0f, 2.0f,
		            1f, 2.0f,
		            // For text coords in right face
		            0.0f, 0.0f,
		            0.0f, 1f,
		            // For text coords in left face
		            1f, 0.0f,
		            1f, 1f,
		            // For text coords in bottom face
		            1f, 0.0f,
		            2.0f, 0.0f,
		            1f, 1f,
		            2.0f, 1f	
		},
		new int[] {
				// Front face
	            0, 1, 3, 3, 1, 2,
	            // Top Face
	            8, 10, 11, 9, 8, 11,
	            // Right face
	            12, 13, 7, 5, 12, 7,
	            // Left face
	            14, 15, 6, 4, 14, 6,
	            // Bottom face
	            16, 18, 19, 17, 16, 19,
	            // Back face
	            4, 6, 7, 5, 4, 7,
		}, "/assets/booLoos2.png");
		*/
		
		Time.setCap(100);
		
		//GameElement element1 = new GameElement(model);
		GameElement element2 = new GameElement(foot);
		
		//element1.getTransform().setTranslationZ(-2f);
		//element2.getMesh().setColor(new Vector3f(1f, 0.5f, 0.2f));
		//element2.getTransform().setTranslationZ(-2f);
		//element1.getTransform().setTranslationX(-0.7f);
		//element2.getTransform().setTranslationX(0.7f);
		
		ModelBatch batch = new ModelBatch(element2);
		
		while(!GLFWWindow.closed()) {
			Time.beginFrame();
			GLFWWindow.update();
			renderer.renderElements(batch);
			GLFWWindow.swapBuffer();
			Time.waitForNextFrame();
		}
		
		shader.remove();
		model.remove();
	}

}
