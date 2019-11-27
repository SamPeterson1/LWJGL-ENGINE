package shaders;

import math.Matrix;
import math.Vector3f;
import rendering.Light;

public class TerrainShader extends Shader{

	private static final String VERTEX_FILE = "src/shaders/TerrainVertShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/TerrainFragShader.glsl";
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "vertices");
		super.bindAttribute(1, "normals");
		super.bindAttribute(2, "textCoords");
	}

	@Override
	public void createUniforms() {
		super.createUniform("p");
		super.createUniform("v");
		super.createUniform("t");
		super.createUniform("textured");
		super.createUniform("color");
		super.createUniform("lightPosition");
		super.createUniform("lightColor");
		super.createUniform("sampler");
		super.createUniform("reflectivity");
		super.createUniform("shineDamping");
		super.createUniform("skyColor");
	}

	public void setMaterialReflectivity(float reflectivity, float shineDamper) {
		super.setUniform("reflectivity", reflectivity);
		super.setUniform("shineDamping", shineDamper);
	}
	
	public void setSkyColor(Vector3f skyColor) {
		super.setUniform3f("skyColor", skyColor);
	}

	public void setSampler(int index) {
		super.setUniformi("sampler", index);
	}
	
	public void setTextured(boolean textured) {
		super.setUniformi("textured", textured ? 1 : 0);
	}
	
	public void setColor(Vector3f color) {
		super.setUniform3f("color", color);
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
	
	public void loadLight(Light light) {
		super.setUniform3f("lightPosition", light.getPosition());
		super.setUniform3f("lightColor", light.getColor());
	}

}