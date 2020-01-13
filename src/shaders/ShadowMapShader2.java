package shaders;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

import math.Matrix4f;

public class ShadowMapShader2 extends Shader {

	private static final String VERTEX_FILE = "/shaders/ShadowVertShader2.glsl";
	private static final String FRAGMENT_FILE = "/shaders/ShadowFragShader2.glsl";
	
	public ShadowMapShader2() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	public void createUniforms() {
		super.createUniform("projectionMatrix");
		super.createUniform("viewMatrix");
		super.createUniform("transformationMatrix");
		super.createUniform("modelTexture");
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.setUniform("projectionMatrix", projectionMatrix);
	}
	
	public void setSampler(int index) {
		super.setUniformi("modelTexture", GL_TEXTURE0);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		super.setUniform("viewMatrix", viewMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.setUniform("transformationMatrix", transformationMatrix);
	}

}
