package models;

import rendering.Material;
import rendering.Texture;

public class Skybox extends Mesh {
	
	private int size;
	private static String[] TEXTURE_FILES = {"rt", "lf", "up", "dn", "bk", "ft"};
	
	public Skybox(int size, String name) {
		super(Mesh.SKYBOX);
		this.size = size;
		this.createModel(size);
		String[] fileNames = new String[6];
		int index = 0;
		for(String direction: TEXTURE_FILES) {
			String fileName = "/assets/" + name + "_" + direction + ".png";
			fileNames[index++] = fileName;
		}
		
		Material material = new Material();
		Texture t = new Texture();
		t.loadCubemap(fileNames);
		material.setTexture(t);
		super.setMaterial(material);
		
	}
	
	private void createModel(int size) {
		
		float[] vertices = {
				-size,  size, -size,
			    -size, -size, -size,
			    size, -size, -size,
			     size, -size, -size,
			     size,  size, -size,
			    -size,  size, -size,

			    -size, -size,  size,
			    -size, -size, -size,
			    -size,  size, -size,
			    -size,  size, -size,
			    -size,  size,  size,
			    -size, -size,  size,

			     size, -size, -size,
			     size, -size,  size,
			     size,  size,  size,
			     size,  size,  size,
			     size,  size, -size,
			     size, -size, -size,

			    -size, -size,  size,
			    -size,  size,  size,
			     size,  size,  size,
			     size,  size,  size,
			     size, -size,  size,
			    -size, -size,  size,

			    -size,  size, -size,
			     size,  size, -size,
			     size,  size,  size,
			     size,  size,  size,
			    -size,  size,  size,
			    -size,  size, -size,

			    -size, -size, -size,
			    -size, -size,  size,
			     size, -size, -size,
			     size, -size, -size,
			    -size, -size,  size,
			     size, -size,  size
		};
		
		super.setModel(ModelLoader.load3DModel(vertices));
		
	}
	
}
