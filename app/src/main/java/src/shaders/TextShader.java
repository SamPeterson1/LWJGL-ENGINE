package shaders;

import math.Matrix4f;
import math.Vector3f;

public class TextShader extends Shader {
	
	private static final String VERTEX_FILE = "shaders/TextVertexShader.glsl";
	private static final String FRAGMENT_FILE = "shaders/TextFragmentShader.glsl";
	
	public TextShader() {
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
		super.createUniform("sampler");
		super.createUniform("t");
	}
	
	public void setTransformationMatrix(Matrix4f matrix) {
		super.setUniform("t", matrix);
	}
	
	public void setTextColor(Vector3f color) {
		super.setUniform3f("color", color);
	}
	
	public void setSampler(int index) {
		super.setUniformi("sampler", index);
	}

}
