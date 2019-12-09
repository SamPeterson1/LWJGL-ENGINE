package shaders;

import math.Matrix;
import particles.ParticleAnimation;

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
		super.createUniform("sampler");
		super.createUniform("textCoordInfo");
		super.createUniform("textOffset1");
		super.createUniform("textOffset2");
		super.createUniform("p");
		super.createUniform("v");
		super.createUniform("t");
	}
	
	public void updateAnimation(ParticleAnimation animation) {
		super.setUniform2f("textOffset1", animation.getTexOffset1());
		super.setUniform2f("textOffset2", animation.getTexOffset2());
		super.setUniform3f("textCoordInfo", animation.getTextureData());
	}
	
	public void setSampler(int index) {
		super.setUniformi("sampler", index);
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
