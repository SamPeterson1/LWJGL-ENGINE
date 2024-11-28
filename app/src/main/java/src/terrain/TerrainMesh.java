package terrain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import math.Noise;
import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.ModelLoader;
import models.RawModel;
import models.TexturedMesh;
import rendering.Material;
import rendering.Texture;

public class TerrainMesh extends Mesh {

	public static final float SIZE = 800;
	public static final int VERTEX_COUNT = 256;
	
	private Noise noise;
	private TexturedMesh tree = new TexturedMesh("tree.obj", "tree.png");
	TexturedMesh fern = new TexturedMesh("fern.obj", "fern.png");
	TexturedMesh grass = new TexturedMesh("fern.obj", "grassTexture.png");
	private SecureRandom rand;
	public float[][] heights = new float[VERTEX_COUNT][VERTEX_COUNT];
	
	public TerrainMesh(String texturePath, Noise noise) {
		super(Mesh.TERRAIN);
		this.noise = noise;
		this.rand = new SecureRandom();
		super.setModel(this.generateTerrain());
		Material material = new Material();
		material.setTexture(new Texture(texturePath));
		material.setCastsShadow(true);
		material.setRecievesShadow(true);
		this.setMaterial(material);	
		fern.setCullFace(false);
		grass.setCullFace(false);
		grass.setUseFakeLighting(true);
	}
	
	private RawModel generateTerrain() {
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float x = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float y = noise.interpolateNoise2D(i/4f, j/4f) * 10;;
				float z = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				
				vertices[vertexPointer*3] = x;
				vertices[vertexPointer*3+1] = y;
				vertices[vertexPointer*3+2] = z;
				this.heights[j][i] = y;
				float xOff = rand.nextFloat()/2f - 1;
				float zOff = rand.nextFloat()/2f - 1;
				float x2 = (float)(j+xOff)/((float)VERTEX_COUNT - 1) * SIZE;
				float z2 = (float)(i+zOff)/((float)VERTEX_COUNT - 1) * SIZE;

				if(rand.nextInt(50) == 0)
					this.randomDecoration(x2, y-0.1f, z2);
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		return ModelLoader.load3DModel(vertices, textureCoords, getVertexNormals(vertices, indices), indices);
	}
	
	private void randomDecoration(float x, float y, float z) {
		Mesh decoration = null;
		float scale = 1f;
		int random = rand.nextInt(2);
		if(random == 0) {
			scale = 2f;
			decoration = tree;
		} else if(random == 1) {
			scale = 1f;
			decoration = fern;
		}
		decoration.getMaterial().setCastsShadow(true);
		decoration.getMaterial().setRecievesShadow(true);
		Entity e = new Entity(decoration);
		e.getTransform().setTranslation(new Vector3f(x, y, z));
		e.getTransform().setScale(new Vector3f(scale, scale, scale));
		if(decoration.equals(tree)) {
			
		}
		ModelBatch.addEntity(e);
	}
	
	public float[] getVertexNormals(float[] vertices, int[] indices) {
		
		List<Vertex> verticesList = new ArrayList<>();
		List<Face> faces = new ArrayList<>();
		
		for(int i = 0; i < vertices.length; i += 3) {
			verticesList.add(new Vertex(vertices[i], vertices[i+1], vertices[i+2]));
		}
		
		for(int i = 0; i < indices.length; i += 3) {
			
			Face face = new Face();
			face.addVertex(verticesList.get(indices[i]));
			verticesList.get(indices[i]).addFace(face);
			face.addVertex(verticesList.get(indices[i+1]));
			verticesList.get(indices[i+1]).addFace(face);
			face.addVertex(verticesList.get(indices[i+2]));
			verticesList.get(indices[i+2]).addFace(face);
			faces.add(face);
			
		}
		
		List<Vector3f> vertexNormals = new ArrayList<>();
		for(Vertex vertex: verticesList) {
			vertexNormals.add(vertex.getNormal());
		}
		
		float[] normals = new float[vertexNormals.size()*3];
		int pointer = 0;
		for(Vector3f vec: vertexNormals) {
			normals[pointer++] = vec.getX();
			normals[pointer++] = vec.getY();
			normals[pointer++] = vec.getZ();
		}
		return normals;
	}

}
