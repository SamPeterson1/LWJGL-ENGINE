package shaders;

import math.Matrix4f;
import math.Vector3f;

public class SkyboxShader extends Shader {

	private static final String VERTEX_FILE = "/shaders/SkyboxVertShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/SkyboxFragShader.glsl";
	
	public SkyboxShader() {
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
		super.createUniform("sampler");
		super.createUniform("fogColor");
	}
	
	public void loadFogColor(Vector3f fogColor) {
		super.setUniform3f("fogColor", fogColor);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.setUniform("projectionMatrix", projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		super.setUniform("viewMatrix", viewMatrix);
	}
	
	public void setDepth(float depth) {
		super.setUniform("depth", depth);
	}
	
	public void setColor(Vector3f color) {
		super.setUniform3f("color", color);
	}
	
	public void setSampler(int index) {
		super.setUniformi("sampler", index);
	}

}
