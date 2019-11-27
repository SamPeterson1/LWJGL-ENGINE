package shaders;

import math.Vector3f;

public class TextShader extends Shader {
	
	private static final String VERTEX_FILE = "src/shaders/TextVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/TextFragmentShader.glsl";
	
	public TextShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "tesa xtCoords");
	}

	@Override
	public void createUniforms() {
		super.createUniform("color");
		super.createUniform("sampler");
	}
	
	public void setTextColor(Vector3f color) {
		super.setUniform3f("color", color);
	}
	
	public void setSampler(int index) {
		super.setUniformi("sampler", index);
	}

}
