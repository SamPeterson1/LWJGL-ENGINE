package terrain;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;
import models.Mesh;
import models.ModelLoader;
import rendering.Material;
import rendering.Texture;

public class TerrainSphere extends Mesh {
	
	private int res;
	private float[] facesArr = new float[0];
	private int[] indicesOut = new int[0];
	private float[] texCoordsOut = new float[0];
	private float[] vertsOut = new float[0];
	private List<TerrainFace> faces = new ArrayList<>();
	private Vector3f[] directions = {new Vector3f(1f, 0f, 0f), new Vector3f(0f, 1f, 0f),
			new Vector3f(0f, 0f, 1f), new Vector3f(-1f, 0f, 0f), new Vector3f(0f, -1f, 0f), new Vector3f(0f, 0f, -1f)};
	
	public TerrainSphere(int res) {
		
		super(Mesh.PLANET);

		super.material = new Material();
		super.material.setTexture(new Texture("grassTexture.png"));
		this.res = res;
		for(Vector3f direction: directions) {
			this.faces.add(new TerrainFace(direction, res));
		}
		
		//merge the meshes and organize info
		/*
		List<Vector3f> verts = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		List<Integer> duplicateIndices = new ArrayList<>();
		List<Vector3f> duplicateVerts = new ArrayList<>();
		Map<Integer, Integer> indicesDecrement = new HashMap<>();
		int indicesOff = 0;
		int next = 0;
		*/
		
		int vertex = 0;
		int texCoord = 0;
		int index = 0;
		int indicesOff = 0;
		
		
		float[] verts = new float[res*res*18];
		int[] indices = new int[(res-1)*(res-1)*36];
		float[] texCoords = new float[res*res*12];
		
		
		for(int i = 0; i < 6; i ++) {
			TerrainFace face = faces.get(i);
			float[] verticesPart = face.getVertices();
			int[] indicesPart = face.getIndices();
			float[] texCoordsPart = face.getTextureCoords();
			indicesOff = i*res*res;
			
			this.mergeV2(verticesPart, indicesPart, texCoordsPart, vertsOut, indicesOut, texCoordsOut);
			
			
			for(float f: verticesPart) {
				verts[vertex] = f;
				vertex ++;
			}
			
			for(int ii: indicesPart) {
				indices[index] = ii + indicesOff;
				index ++;
			}
			
			for(float f: texCoordsPart) {
				texCoords[texCoord] = f;
				texCoord ++;
			}
			
			
			/*
			indicesDecrement = new HashMap<>();
			indicesOff += next;
			next = 0;
			for(int ii = 0; ii < verticesPart.length; ii += 3) {
				Vector3f vertex = new Vector3f(verticesPart[ii], verticesPart[ii+1], verticesPart[ii+2]);
				if(!verts.contains(vertex)) {
					verts.add(vertex);
					next ++;
				} else {
					duplicateVerts.add(vertex);
					//index of vertex in verts list
					duplicateIndices.add(verts.lastIndexOf(vertex));
				}
			}
			
			for(int ii = 0; ii < indicesPart.length; ii ++) {
				int off2 = 0;
				for(int iii: indicesDecrement.keySet()) {
					if(indicesPart[ii] >= iii) off2 = indicesDecrement.get(iii);
				}
				indices.add(indicesPart[ii] + indicesOff - off2);
				System.out.println("INDICES" + indices.get(indices.size()-1));
			}
			*/
		}
		
		//time to get rid of the lighting glitches!
		//find references to the duplicate indices and replace them with the correct thing
		
		/*
		int count = 0;
		for(int i: duplicateIndices) {
			//find all occurrences of index
			List<Integer> incorrectIndicesIndices = new ArrayList<>();
			int index = 0;
			for(int ii: indices) {
				if(ii == i) incorrectIndicesIndices.add(index);
				index ++;
			}
			System.out.println(incorrectIndicesIndices.size());
			
			//find correct value
			//find corresponding duplicate vertex
			Vector3f duplicateVert = duplicateVerts.get(count);
			count ++;
			int correctIndex = verts.indexOf(duplicateVert);
			//replace all incorrect occurrences with the right index
			for(int ii: incorrectIndicesIndices) {
				System.out.println(indices.get(correctIndex) == ii);
				indices.set(ii, correctIndex);
			}
		}
		
		
		
		float[] vertices = new float[verts.size()*3];
		int index = 0;
		for(Vector3f vertex: verts) {
			vertices[index] = vertex.getX();
			vertices[index+1] = vertex.getY();
			vertices[index+2] = vertex.getZ();
			index += 3;
		}
		
		int[] indicesArr = new int[indices.size()];
		index = 0;
		for(int i: indices) {
			indicesArr[index] = i;
			index ++;
		}
		*/
		
		super.model = ModelLoader.load3DModel(vertsOut, texCoordsOut, this.getVertexNormals(vertsOut, indicesOut), indicesOut);
	}
	
	private void mergeV2(float[] verts1, int[] indices1, float[] texCoords1, float[] verts2, int[] indices2, float[] texCoords2) {
		//merge vertices
		
		List<Vector3f> verts = new ArrayList<>();
		List<Integer> wrongIndices = new ArrayList<>();
		List<Integer> correctIndices = new ArrayList<>();
		List<Vector3f> correspondingVertices = new ArrayList<>();
		List<Float> texCoords = new ArrayList<>();
		
		for(float f: texCoords1) {
			texCoords.add(f);
		}
		
		for(int i = 0; i < verts1.length; i += 3) {
			verts.add(new Vector3f(verts1[i], verts1[i+1], verts1[i+2]));
		}
		
		for(int i = 0; i < verts2.length; i +=3) {
			Vector3f vertex = new Vector3f(verts2[i], verts2[i+1], verts2[i+2]);
			if(!verts.contains(vertex)) {
				verts.add(vertex);
				int texIndex = i*2/3;
				texCoords.add(texCoords2[texIndex]);
				texCoords.add(texCoords2[texIndex+1]);
			} else {
				wrongIndices.add(i/3);
				int texIndex = i*2/3;
				texCoords.add(texCoords2[texIndex]);
				texCoords.add(texCoords2[texIndex+1]);
				correspondingVertices.add(vertex);
			}
		}
		
		for(int i: indices1) {
			correctIndices.add(i);
		}

		for(int i = 0; i < indices2.length; i ++) {
			int index = indices2[i]*3;
			Vector3f vertex = new Vector3f(verts2[index], verts2[index+1], verts2[index+2]);
			int correctIndex = verts.indexOf(vertex);
			correctIndices.add(correctIndex);
		}

		this.vertsOut = new float[verts.size()*3];
		this.indicesOut = new int[correctIndices.size()];
		this.texCoordsOut = new float[texCoords.size()];
		
		int i = 0;
		for(Vector3f vertex: verts) {
			vertsOut[i] = vertex.getX();
			vertsOut[i+1] = vertex.getY();
			vertsOut[i+2] = vertex.getZ();
			i += 3;
		}
		
		i = 0;
		for(float f: texCoords) {
			texCoordsOut[i] = f;
			i ++;
		}
		
		i = 0;
		for(int index: correctIndices) {
			indicesOut[i] = index;
			i ++;
		}
	}
	
	private void mergeMeshes(float[] verts1, int[] indices1, float[] verts2, int[] indices2) {
		
		//remove overlapping vertices in 2nd list
		List<Vector3f> verts1List = new ArrayList<>();
		for(int i = 0; i < verts1.length; i += 3) {
			verts1List.add(new Vector3f(verts1[i], verts1[i+1], verts1[i+2]));
		}
		
		List<Vector3f> verts2List = new ArrayList<>();
		for(int i = 0; i < verts2.length; i += 3) {
			verts2List.add(new Vector3f(verts2[i], verts2[i+1], verts2[i+2]));
		}
		
		List<Integer> indices1List = new ArrayList<>();
		for(int i: indices1) {
			indices1List.add(i);
		}
		
		List<Integer> indices2List = new ArrayList<>();
		for(int i: indices2) {
			indices2List.add(i);
		}
		
		List<Float> verts2RemovedOverlap = new ArrayList<>();
		List<Vector3f> verts2RemovedOverlapVector = new ArrayList<>();
		List<Integer> toBeDecreased = new ArrayList<>();
		int largestNonOverlappingIndex = 0;
		for(int i = 0; i < verts2.length; i += 3) {
			Vector3f vertex = new Vector3f(verts2[i], verts2[i+1], verts2[i+2]);
			if(!verts1List.contains(vertex)) {
				verts2RemovedOverlap.add(verts2[i]);
				verts2RemovedOverlap.add(verts2[i+1]);
				verts2RemovedOverlap.add(verts2[i+2]);
				verts2RemovedOverlapVector.add(vertex);
				int nonOverlappingIndex = verts2List.indexOf(vertex);
				if(nonOverlappingIndex > largestNonOverlappingIndex) {
					largestNonOverlappingIndex = nonOverlappingIndex;
				}
			} else {

				//overlapping vertex, find number corresponding to vertex in indices list 2 (a), and replace all occurances of (a) with a number (b) corresponding to vertex in indices list 1
				int a = verts2List.indexOf(vertex);
				int b = verts1List.indexOf(vertex);
				for(int ii = 0; ii < indices2.length; ii ++) {
					if(indices2[ii] == a) {
						toBeDecreased.add(ii);
						indices2[ii] = b;
					}
				
				}
			}
		}
		
		int decrement = verts1.length/3 + verts2RemovedOverlap.size()/3 - 1 - largestNonOverlappingIndex;
		System.out.println(decrement + " " + largestNonOverlappingIndex + " " + verts2RemovedOverlap.size() + " " + verts1.length/3);
		for(int i: toBeDecreased) {
			indices2[i] -= decrement;
		}
		
		//merge vertices
		vertsOut = new float[verts1.length + verts2RemovedOverlap.size()];
		int vertex = 0;
		for(float f: verts1) {
			vertsOut[vertex] = f;
			vertex ++;
		}
		for(float f: verts2RemovedOverlap) {
			vertsOut[vertex] = f;
			vertex ++;
		}
		
		//merge indices
		indicesOut = new int[indices1.length + indices2.length];
		int index = 0;
		for(int i: indices1) {
			indicesOut[index] = i;
			index ++;
		}
		for(int i: indices2) {
			indicesOut[index] = i + decrement;
			index ++;
		}
	}
	
	public float[] getVertexNormals(float[] vertices, int[] indices) {
		
		List<Vertex> verticesList = new ArrayList<>();
		List<Face> faces = new ArrayList<>();
		for(int i = 0; i < vertices.length; i += 3) {
			Vector3f pos = new Vector3f(vertices[i], vertices[i+1], vertices[i+2]);
			pos.normalize();
			verticesList.add(new Vertex(pos));
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
	
	public int getRes() {
		return this.res;
	}
}
