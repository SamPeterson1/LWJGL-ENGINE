package shaders;

import math.Matrix;
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
	}
	
	public void loadProjectionMatrix(Matrix projectionMatrix) {
		super.setUniform("projectionMatrix", projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix viewMatrix) {
		
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
