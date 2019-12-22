package shaders;

import math.Matrix;
import math.Vector3f;

public class ShadowMapShader extends Shader {

	private static final String VERTEX_FILE = "/shaders/ShadowVertShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/ShadowFragShader.glsl";
	
	public ShadowMapShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	public void createUniforms() {
		super.createUniform("projectionMatrix");
		super.createUniform("viewMatrix");
		super.createUniform("transformationMatrix");
	}
	
	public void loadProjectionMatrix(Matrix projectionMatrix) {
		super.setUniform("projectionMatrix", projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix viewMatrix) {
		super.setUniform("viewMatrix", viewMatrix);
	}
	
	public void loadTransformationMatrix(Matrix transformationMatrix) {
		super.setUniform("transformationMatrix", transformationMatrix);
	}

}
