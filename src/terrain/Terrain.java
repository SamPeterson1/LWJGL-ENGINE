package terrain;

import math.Noise;
import math.Vector2f;
import math.Vector3f;
import misc.Utils;
import models.Entity;

public class Terrain extends Entity {
	
	TerrainMesh mesh;
	
	public Terrain(String texturePath, Noise noise) {
		
		this.mesh = new TerrainMesh(texturePath, noise);
		super.setMesh(mesh);
		
	}
	
	public float getHeight(float x, float z) {
		System.out.println(x + "foo");
		System.out.println(z + "foo");
		float vertSpacing = (float)TerrainMesh.SIZE/TerrainMesh.VERTEX_COUNT;
		int gridX = (int) (x/vertSpacing);
		int gridZ = (int) (z/vertSpacing);
		
		float xCoord = (x/vertSpacing) - gridX;
		float zCoord = (z/vertSpacing) - gridZ;

		float answer;
		
		if (xCoord <= (1-zCoord)) {
			answer = Utils
					.barryCentric(new Vector3f(0, mesh.heights[gridX][gridZ], 0), new Vector3f(1,
							mesh.heights[gridX + 1][gridZ], 0), new Vector3f(0,
									mesh.heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			answer = Utils
					.barryCentric(new Vector3f(1, mesh.heights[gridX + 1][gridZ], 0), new Vector3f(1,
							mesh.heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
									mesh.heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		
		return answer;
	}
	
}
