package models;

import math.Vector3f;
import rendering.Material;
import rendering.Texture;

public class Mesh {
	
	public static final int TEXT = 0;
	public static final int TEXTURED = 1;
	public static final int UNTEXTURED = 2;
	public static final int TERRAIN = 3;
	public static final int GUI_COLORED = 4;
	public static final int GUI_TEXTURED = 5;
	public static final int PARTICLE = 6;
	public static final int SKYBOX = 7;
	public static final int PLANET = 8;
	
	protected int type;
	protected Material material;
	protected RawModel model;
	protected boolean castsShadow = false;
	protected boolean enabled = true;
	
	public Mesh() {
	}
	
	public Mesh(int type) {
		this.type = type;
	}
	
	public Mesh(int type, RawModel model) {
		this.material = new Material();
		this.material.setColor(new Vector3f(1f, 0f, 0f));
		this.type = type;
		this.model = model;
	}
	
	public Mesh(RawModel model, Texture t) {
		this.model = model;
		this.material = new Material();
		this.material.setTexture(t);
	}

	public boolean castsShadow() {
		return this.castsShadow;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
	public void enable() {
		this.enabled = true;
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
