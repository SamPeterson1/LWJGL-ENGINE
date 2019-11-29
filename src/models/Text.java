package models;

import GUI.GUIComponent;
import GUI.TextMeshGenerator;
import math.Vector3f;
import rendering.Material;
import rendering.Texture;

public class Text extends GUIComponent {

	private TextMeshGenerator meshGenerator;
	private String text;
	private float relativeSize;
	
	public Text(String text, int x, int y, float relativeSize, Vector3f color, String fontFile) {
		
		super(Mesh.TEXT);
		
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
