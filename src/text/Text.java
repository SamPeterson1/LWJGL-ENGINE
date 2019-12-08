package text;

import GUI.AspectConstraint;
import GUI.Constraint;
import GUI.GUIComponent;
import GUI.RelativeConstraint;
import files.FontFile;
import math.Vector3f;
import models.Mesh;
import models.ModelBatch;
import rendering.Material;
import rendering.Texture;
import xml.XMLElement;

public class Text extends GUIComponent {

	private TextMeshGenerator meshGenerator;
	private String text;
	private Font f;
	private float relativeSize;
	
	public Text(XMLElement xml) {
		
		this.text = "default text";
		if(xml.hasAttribute("text")) {
			this.text = xml.getAttribute("text").getString();
		}
		
		String fontFile = "/assets/TestFont.fnt";
		if(xml.hasAttribute("font")) {
			fontFile = xml.getAttribute("font").getString();
		}
		
		Vector3f color = new Vector3f();
		if(xml.hasChildWithName("color")) {
			XMLElement colorElement = xml.anyChildWithName("color");
			color.setX(colorElement.getAttribute("r").getFloat());
			color.setY(colorElement.getAttribute("g").getFloat());
			color.setZ(colorElement.getAttribute("b").getFloat());
		}
		
		float size = 0.5f;
		if(xml.hasAttribute("size")) {
			size = xml.getAttribute("size").getFloat();
		}
		
		FontFile file = new FontFile(fontFile);
		this.f = file.read();
		
		this.meshGenerator = new TextMeshGenerator(f);
		this.model = this.meshGenerator.genMesh(this);
		super.setModel(model);
		
		Material material = new Material();
		material.setColor(color);
		material.setTexture(new Texture("/assets/TestFont.png"));
		super.setMaterial(material);
		this.relativeSize = 1f;
		super.setConstraint(new RelativeConstraint(size, Constraint.HEIGHT));
		super.setConstraint(new AspectConstraint(this.pixelWidth()/(float)this.pixelHeight(), Constraint.WIDTH));
		if(xml.hasChildWithName("constraints")) {
			super.loadConstraints(xml.anyChildWithName("constraints"));
		} else {
			super.setConstraint(new RelativeConstraint(0.5f, Constraint.X));
			super.setConstraint(new RelativeConstraint(0.5f, Constraint.Y));

		}
		ModelBatch.addEntity(super.getEntity());
		
	}
	
	public Text(String text, float relativeSize, Vector3f color, String fontFile, float depth) {
		
		super(Mesh.TEXT);
		
		this.text = text;
		this.relativeSize = 1f;
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
		this.meshGenerator = new TextMeshGenerator(f);
		this.text = text;
		this.meshGenerator.updateMesh(this);
		System.out.println((float)this.pixelWidth()/(float)this.pixelHeight());
		super.setConstraint(new AspectConstraint((float)this.pixelWidth()/(float)this.pixelHeight(), Constraint.WIDTH));
		super.calculateConstraints();
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
	
}