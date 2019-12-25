package shaders;

import math.Matrix4f;

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
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.setUniform("projectionMatrix", projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		super.setUniform("viewMatrix", viewMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.setUniform("transformationMatrix", transformationMatrix);
	}

}
