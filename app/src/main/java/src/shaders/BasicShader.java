package shaders;

import java.util.List;

import lights.PointLight;
import math.Matrix4f;
import math.Vector3f;
import rendering.Environment;
import rendering.Light;

public class BasicShader extends Shader {

	private static final String VERTEX_FILE = "shaders/BaseVertShader2.glsl";
	private static final String FRAGMENT_FILE = "shaders/BaseFragShader2.glsl";
	
	public BasicShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);	
	}

	@Override
	public void bindAllAttributes() {
		super.bindAttribute(0, "vertices");
		super.bindAttribute(1, "textCoords");
		super.bindAttribute(2, "normals");
	}

	@Override
	public void createUniforms() {
		super.createUniform("p");
		super.createUniform("v");
		super.createUniform("t");
		super.createUniform("textured");
		super.createUniform("color");
		super.createUniform("lightPosition", 4);
		super.createUniform("lightColor", 4);
		super.createUniform("attenuation", 4);
		super.createUniform("sampler");
		super.createUniform("reflectivity");
		super.createUniform("shineDamping");
		super.createUniform("useFakeLighting");
		super.createUniform("skyColor");
		super.createUniform("sunColor");
		super.createUniform("sunDirection");
	}
	
	public void setSkyColor(Vector3f skyColor) {
		super.setUniform3f("skyColor", skyColor);
	}
	
	public void loadLights() {
		
		super.setUniform3f("sunDirection", Environment.sunDirection);
		super.setUniform3f("sunColor", Environment.sunColor);
		List<PointLight> pointLights = Environment.pointLights;
		Vector3f[] colors = new Vector3f[4];
		Vector3f[] positions = new Vector3f[4];
		Vector3f[] attenuations = new Vector3f[4];
		int index = 0;
		for(PointLight light: pointLights) {
			if(light.enabled()) {
				colors[index] = light.getColor();
				positions[index] = light.getPosition();
				attenuations[index] = light.getAttenuation();
				index ++;
			}
		}
		
		for(int i = index; i < 4; i ++) {
			colors[i] = new Vector3f();
			positions[i] = new Vector3f();
			attenuations[i] = new Vector3f();
		}
		
		super.setUniform3farr("lightColor", colors);
		super.setUniform3farr("lightPosition", positions);
		super.setUniform3farr("attenuation", attenuations);
		
	}
		
	public void setUseFakeLighting(boolean useFakeLighting) {
		super.setUniformi("useFakeLighting", useFakeLighting ? 1 : 0);
	}
	
	public void setMaterialReflectivity(float reflectivity, float shineDamper) {
		super.setUniform("reflectivity", reflectivity);
		super.setUniform("shineDamping", shineDamper);
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
	
	public void setProjectionMatrix(Matrix4f projection) {
		super.setUniform("p", projection);
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		super.setUniform("v", viewMatrix);
	}
	
	public void setTransformationMatrix(Matrix4f transformationMatrix) {
		super.setUniform("t", transformationMatrix);
	}
	
}