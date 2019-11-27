package models;

import GUI.TextMeshGenerator;
import math.Vector3f;
import rendering.Material;
import rendering.Texture;

public class TextMesh extends Mesh {

	private TextMeshGenerator meshGenerator;
	private String text;
	private int x;
	private int y;
	private float relativeSize;
	
	public TextMesh(String text, int x, int y, float relativeSize, Vector3f color, String fontFile) {
		
		super(Mesh.TEXT);
		
		this.x = x;
		this.y = y;
		this.text = text;
		this.relativeSize = relativeSize;
		this.meshGenerator = new TextMeshGenerator(fontFile);
		this.model = this.meshGenerator.genMesh(this);
		super.setModel(model);
		Material material = new Material();
		material.setColor(color);
		material.setTexture(new Texture("/assets/TestFont.png"));
		super.setMaterial(material);
		
	}

	public Vector3f getColor() {
		return this.material.getColor();
	}
	
	public Texture getTexture() {
		return this.material.getTexture();
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
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
