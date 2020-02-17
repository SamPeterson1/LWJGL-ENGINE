package shaders;

import math.Matrix4f;
import math.Vector4f;

public class GUIShader extends Shader {

	private static final String VERTEX_FILE = "/shaders/GUIVertShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/GUIFragShader.glsl";
	
	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textCoords");
	}

	@Override
	public void createUniforms() {
		super.createUniform("color");
		super.createUniform("transformationMatrix");
		super.createUniform("depth");
		super.createUniform("textured");
		super.createUniform("sampler");
		super.createUniform("hasBackground");
	}
	
	public void setTextured(boolean textured) {
		super.setUniformi("textured", textured ? 1 : 0);
	}
	
	public void setHasBackground(boolean hasBackground) {
		super.setUniformi("hasBackground", hasBackground ? 1 : 0);
	}
	
	public void setSampler(int index) {
		super.setUniformi("sampler", index);
	}
	
	public void setDepth(float depth) {
		super.setUniform("depth", depth);
	}
	
	public void setTransformationMatrix(Matrix4f matrix) {
		super.setUniform("transformationMatrix", matrix);
	}
	
	public void setColor(Vector4f color) {
		super.setUniform4f("color", color);
	}

}
