package shaders;

import math.Matrix4f;
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
		super.bindAttribute(1, "t");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");
		super.bindAttribute(7, "fadeOut");
	}
	
	@Override
	public void createUniforms() {
		super.createUniform("sampler");
		super.createUniform("atlasRows");
		super.createUniform("p");
		super.createUniform("v");
	}
	
	public void setAtlasRows(ParticleAnimation animation) {
		super.setUniformi("atlasRows", animation.getAtlasRows());
	}
	
	public void setSampler(int index) {
		super.setUniformi("sampler", index);
	}
	
	public void setProjectionMatrix(Matrix4f projection) {
		super.setUniform("p", projection);
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		super.setUniform("v", viewMatrix);
	}
	
	public void setTransformationMatrix(Matrix4f transformationMatrix) {
		super.setUniform("t2", transformationMatrix);
	}
	
}
