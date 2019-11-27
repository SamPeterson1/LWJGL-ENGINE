package models;

import GUI.TextMeshGenerator;
import math.Vector3f;
import rendering.Texture;

public class TextMesh implements Mesh {

	private TextMeshGenerator meshGenerator;
	private Vector3f color;
	private String text;
	private Model model;
	private Texture texture;
	private int x;
	private int y;
	private float relativeSize;
	
	public TextMesh(String text, int x, int y, float relativeSize, Vector3f color, String fontFile) {
		
		this.color = color;
		this.x = x;
		this.y = y;
		this.text = text;
		this.relativeSize = relativeSize;
		this.texture = new Texture("/assets/TestFont.png");
		this.meshGenerator = new TextMeshGenerator(fontFile);
		this.model = this.meshGenerator.genMesh(this);
		
	}
	
	@Override
	public Model getModel() {
		return this.model;
	}
	
	public Vector3f getColor() {
		return this.color;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public float getRelativeSize() {
		return this.relativeSize;
	}
	
	public String getText() {
		return this.text;
	}

	@Override
	public int getType() {
		return Mesh.TEXT;
	}
	
}
