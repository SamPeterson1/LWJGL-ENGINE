package particles;

import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.ModelLoader;
import models.RawModel;
import rendering.Material;
import rendering.Texture;

public class ParticleMesh extends Mesh {

	private static final float[] rectVerts = new float[] {
			1, 1,
			1, -1,
			-1, -1,
			-1, 1
	};
	
	private static final int[] rectIndices = new int[] {
			0, 1, 2,
			0, 3, 2
	};
	
	private static final RawModel rectangle = ModelLoader.loadColoredGUIModel(rectVerts, rectIndices);
	
	public ParticleMesh(Texture t) {
		
		super(Mesh.PARTICLE);
		super.setModel(rectangle);
		Material material = new Material();
		material.setTexture(t);
		super.setMaterial(material);
		
	}

}
