package models;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class TerrainGenerator {
	
	private static HeightGenerator height = new HeightGenerator(70);
	
	public static Mesh generateMesh() {
		int width = 200;
		int height = 200;
		float[] vertices = new float[width*height*3];
		float[] normals = new float[width*height*3];
		List<Integer> indices = new ArrayList<Integer>();
		
		int vertexPointer = 0;
		for(int x = 0; x < width; x ++) {
			for(int y = 0; y < height; y ++) {
				vertices[vertexPointer++] = x;
				vertices[vertexPointer++] = TerrainGenerator.height.getNoise(x, y);
				vertices[vertexPointer++] = y;
			}
		}
		
		int index = 0;
		for(int i = 0; i < (width-1) * (height-1); i ++) {
			int[] top = TerrainGenerator.genTopTriIndices(i, width);
			int[] bottom = TerrainGenerator.genBottomTriIndices(i, width);
			indices.add(top[0]);
			indices.add(top[1]);
			indices.add(top[2]);
			indices.add(bottom[0]);
			indices.add(bottom[1]);
			indices.add(bottom[2]);
		}
		
		for(int i = 0; i < normals.length; i += 3) {
			normals[i] = (float) Math.random();
			normals[i+1] = (float) Math.random();
		    normals[i+2] = (float) Math.random();
		}
		
		int[] indicesArr = new int[indices.size()];
		index = 0;
		for(int i: indices) {
			indicesArr[index++] = i;
		}
		
		return new Mesh(vertices, normals, indicesArr, new Vector3f(0, 0, 1));
	}
	
	private static int[] genTopTriIndices(int cornerIndex, int terrainWidth) {
		return new int[] {
				cornerIndex, cornerIndex + 1,
				cornerIndex + 1 + terrainWidth
		};
	}
	
	private static int[] genBottomTriIndices(int cornerIndex, int terrainWidth) {
		return new int[] {
				cornerIndex, cornerIndex + terrainWidth,
				cornerIndex + 1 + terrainWidth
		};
	}
	
}
