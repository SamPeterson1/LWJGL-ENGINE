package models;

import rendering.Material;

public class Mesh {
	
	public static final int TEXT = 0;
	public static final int TEXTURED = 1;
	public static final int UNTEXTURED = 2;
	public static final int TERRAIN = 3;
	public static final int GUI_COLORED = 4;
	public static final int GUI_TEXTURED = 5;
	public static final int PARTICLE = 6;
	
	protected int type;
	protected Material material;
	protected RawModel model;
	protected boolean enabled = true;
	
	public Mesh() {}
	
	public Mesh(int type) {
		this.type = type;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setModel(RawModel model) {
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

	public RawModel getModel() {
		return model;
	}
	
	public void remove() {
		this.model.remove();
	}
	
}
