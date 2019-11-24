package files;

import java.util.ArrayList;
import java.util.List;

import math.Vector2f;
import math.Vector3f;
import models.Mesh;

public class OBJFile implements File {

	/*
	private TextFile file;
	private MTLFile mtlFile;
	private String texPath;
	
	private ArrayList<Float> verts = new ArrayList<Float>();
	private ArrayList<Float> textCoords = new ArrayList<Float>();
	private ArrayList<int[]> faceIndices = new ArrayList<int[]>();
	*/
	
	private TextFile file;
	
	public OBJFile(String path) {
		this.file = new TextFile(path);
	}
	
	@Override
	public Mesh read() {
		
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] vertsArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		
		String line;
		
		while((line = file.readLine()) != null) {
			
			String[] tokens = line.split(" ");
			
			if(tokens[0].equals("v")) {
				Vector3f vertex = new Vector3f(Float.parseFloat(tokens[1]), 
						Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
				vertices.add(vertex);
			} else if(tokens[0].equals("vt")) {
				Vector2f texture = new Vector2f(Float.parseFloat(tokens[1]), 
						Float.parseFloat(tokens[2]));
				textures.add(texture);
			} else if(tokens[0].equals("vn")) {
				Vector3f normal = new Vector3f(Float.parseFloat(tokens[1]), 
						Float.parseFloat(tokens[2]), Float.parseFloat(tokens[2]));
				normals.add(normal);
			} else if(tokens[0].equals("f")) {
				textureArray = new float[vertices.size()*2];
				normalsArray = new float[vertices.size()*3];
				break;
			}
			
		}
		
		while((line = file.readLine()) != null) {
			
			
			String[] tokens = line.split(" ");
			
			System.out.println(line);
			
			String[] vert1 = tokens[1].split("/");
			String[] vert2 = tokens[2].split("/");
			String[] vert3 = tokens[3].split("/");
			
			this.processVertex(vert1, indices, textures, normals, textureArray, normalsArray);
			this.processVertex(vert2, indices, textures, normals, textureArray, normalsArray);
			this.processVertex(vert3, indices, textures, normals, textureArray, normalsArray);

		}
		
		this.file.close();
		
		vertsArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex: vertices) {
			vertsArray[vertexPointer++] = vertex.getX();
			vertsArray[vertexPointer++] = vertex.getY();
			vertsArray[vertexPointer++] = vertex.getZ();
		}
		
		for(int i = 0; i < indices.size(); i ++) {
			indicesArray[i] = indices.get(i);
		}
		
		return new Mesh(vertsArray, textureArray, normalsArray, indicesArray, "/assets/stallTexture.png");
	}
	
	private void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, 
			List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
			
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);

		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.getX();
		textureArray[currentVertexPointer*2 + 1] = 1 - currentTex.getY();
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) -1);
		normalsArray[currentVertexPointer*3] = currentNorm.getX();
		normalsArray[currentVertexPointer*3 + 1] = currentNorm.getY();
		normalsArray[currentVertexPointer*3 + 2] = currentNorm.getZ();

	}

	@Override
	public void close() {
		this.file.close();
	}
	
	/*
	@Override
	public Mesh read() {
		this.loadData();
		this.texPath = mtlFile.read();
		float[] textCoords = new float[this.textCoords.size()];
		int[] indices = new int[faceIndices.size()];
		int index = 0;
		for(int[] i: this.faceIndices) {
			indices[index] = i[0]-1;
			int vertIndex = indices[index];
			int textIndex = (i[1] - 1)*2;
			textCoords[vertIndex*2] = this.textCoords.get(textIndex);
			textCoords[vertIndex*2+1] = this.textCoords.get(textIndex+ 1);
			System.out.print(indices[index] + ", ");
			index ++;
		}
		
		System.out.println("f");
		
		for(float f: textCoords) {
			System.out.print(f + ", ");
		}
		
		System.out.println("f");
		
		float[] verts = new float[this.verts.size()];
		for(int i = 0; i < verts.length; i ++) {
			verts[i] = this.verts.get(i);
			System.out.print(verts[i] + ", ");
		}
		
		
		System.out.println("f" + this.texPath);
		
		if(this.texPath.equals("")) {
			return new Mesh(verts, indices, new Vector3f(1f, 0f, 0f));
		} else {
			return new Mesh(verts, textCoords, indices, this.texPath);
		}
	}
	
	private void loadData() {
		String line;
		while((line = this.file.readLine()) != null) {
			String[] vals = line.split(" ");
			if(vals[0].equals("v")) {
				System.out.println(line);
				this.verts.add(Float.valueOf(vals[1]));
				this.verts.add(Float.valueOf(vals[2]));
				this.verts.add(Float.valueOf(vals[3]));
			} else if(vals[0].equals("vt")) {
				this.textCoords.add(Float.valueOf(vals[1]));
				this.textCoords.add(Float.valueOf(vals[2]));
			} else if(vals[0].equals("f")) {
				for(String s: vals) {
					if(!s.equals("f")) {
						String[] indices = s.split("/");
						int[] indicesInt = new int[] {
							Integer.valueOf(indices[0]),
							Integer.valueOf(indices[1]),
							Integer.valueOf(indices[2])
						};
						this.faceIndices.add(indicesInt);
					}
				}
			} else if(vals[0].equals("mtllib")) {
				System.out.println("HI" + "src/assets/" + vals[1]);
				this.mtlFile = new MTLFile("src/assets/" + vals[1]);
			}
		}
	}
	
	*/
}
