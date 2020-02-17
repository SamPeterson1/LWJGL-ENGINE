package terrain;

import math.Noise;
import math.Vector3f;

public class TerrainFace {
	
	private int[] indices;
	private float[] vertices;
	private float[] textureCoords;
	
	public TerrainFace(Vector3f localUp, int res) {
		
		Noise noise = new Noise(130);
		this.vertices = new float[res*res*3];
		this.indices = new int[(res-1)*(res-1)*6];
		this.textureCoords = new float[res*res*2];
		
		Vector3f axisA = new Vector3f(localUp.getY(), localUp.getZ(), localUp.getX());
		Vector3f axisB = Vector3f.cross(localUp, axisA);
		System.out.println(axisA.getX() + " " + axisA.getY() + " " + axisA.getZ());
		System.out.println(axisB.getX() + " " + axisB.getY() + " " + axisB.getZ());
		int index = 0;
		int texIndex = 0;
		for(int y = 0; y < res; y ++) {
			for(int x = 0; x < res; x ++) {
				Vector3f pointOnCube = Vector3f.add(localUp, Vector3f.scale(axisA, ((float)x/(res-1)-0.5f)*2));
				textureCoords[texIndex] = (float)x/(res-1);
				textureCoords[texIndex+1] = (float)y/(res-1);
				texIndex += 2;
				System.out.println(textureCoords[texIndex-2] + " COORDS " + textureCoords[texIndex-1]);
				pointOnCube.add(Vector3f.scale(axisB, ((float)y/(res-1)-0.5f)*2));
				//pointOnCube.normalize();
				float foo = noise.interpolateNoise2D(x/4f, y/4f) * 10;
				pointOnCube.multiplyScalar(200);
				//pointOnCube.add(Vector3f.scale(localUp, 0.01f));
				int i = 3*(y*res + x);
				
				vertices[i] = pointOnCube.getX();
				vertices[i+1] = pointOnCube.getY();
				vertices[i+2] = pointOnCube.getZ();
				
				if(y != res-1 && x != res-1 ) {
					int vertex = i/3;
					indices[index] = vertex;
					indices[index+1] = vertex + 1;
					indices[index+2] = vertex + res + 1; 
					
					indices[index+3] = vertex;
					indices[index+4] = vertex + res + 1;
					indices[index+5] = vertex + res;
					
					index += 6;
				}
			}
		}
	}
	
	public float[] getTextureCoords() {
		return this.textureCoords;
	}
	
	public int[] getIndices() {
		return this.indices;
	}
	
	public float[] getVertices() {
		return this.vertices;
	}
	
}
