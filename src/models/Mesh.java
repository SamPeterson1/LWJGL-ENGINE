package models;

import rendering.Material;

public class Mesh {
	
	public static final int TEXT = 0;
	public static final int TEXTURED = 1;
	public static final int UNTEXTURED = 2;
	public static final int TERRAIN = 3;
	public static final int GUI = 4;
	
	protected int type;
	protected Material material;
	protected Model model;
	
	public Mesh(int type) {
		this.type = type;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public void update() {
		
	}

	public int getType() {
		return type;
	}

	public Material getMaterial() {
		return material;
	}

	public Model getModel() {
		return model;
	}
	
}
