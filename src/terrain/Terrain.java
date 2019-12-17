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
		Vector3f pos = transform.getPos();
		x -= pos.getX();
		z -= pos.getZ();
		int gridX = (int) (x/vertSpacing);
		int gridZ = (int) (z/vertSpacing);
		
		if(gridX >= 0 && gridZ >= 0) {
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
		
		return 0;
	}
	
}
