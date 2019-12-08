package shaders;

import math.Matrix;
import math.Vector3f;
import rendering.Light;

public class ParticleShader extends Shader {

	private static final String VERTEX_FILE = "/shaders/ParticleVertShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/ParticleFragShader.glsl";
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	public void createUniforms() {
		super.createUniform("p");
		super.createUniform("v");
		super.createUniform("t");
	}
	
	public void setProjectionMatrix(Matrix projection) {
		super.setUniform("p", projection);
	}

	public void setViewMatrix(Matrix viewMatrix) {
		super.setUniform("v", viewMatrix);
	}
	
	public void setTransformationMatrix(Matrix transformationMatrix) {
		super.setUniform("t", transformationMatrix);
	}
	
}
