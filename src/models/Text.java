package models;

import GUI.Font;
import GUI.GUIComponent;
import GUI.TextMeshGenerator;
import files.FontFile;
import math.Vector3f;
import rendering.Material;
import rendering.Texture;

public class Text extends GUIComponent {

	private TextMeshGenerator meshGenerator;
	private String text;
	private Font f;
	private float relativeSize;
	
	public Text(String text, float relativeSize, Vector3f color, String fontFile, float depth) {
		
		super(Mesh.TEXT);
		
		this.text = text;
		this.relativeSize = relativeSize;
		FontFile file = new FontFile(fontFile);
		this.f = file.read();
		this.meshGenerator = new TextMeshGenerator(f);
		this.model = this.meshGenerator.genMesh(this);
		super.setModel(model);
		Material material = new Material();
		material.setColor(color);
		material.setTexture(new Texture("/assets/TestFont.png"));
		super.setMaterial(material);
		super.depth = depth;
	}
	
	public int pixelWidth() {
		 return this.f.pixelWidth(text, relativeSize);
	}
	
	public int pixelHeight() {
		 return this.f.pixelHeight(text, relativeSize);
	}
	
	public void setText(String text) {
		this.text = text;
		this.meshGenerator.updateMesh(this);
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
