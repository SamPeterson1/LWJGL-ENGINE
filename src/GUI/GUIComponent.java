package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Vector2f;
import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.RawModel;
import models.ModelLoader;
import rendering.Material;
import rendering.Texture;
import window.GLFWWindow;
import xml.XMLElement;

public class GUIComponent extends Mesh {
	
	protected Entity entity;
	protected GUIComponent parent;
	protected float depth;
	protected int centerX;
	protected int centerY;
	protected float radius = 0;
	protected List<GUIComponent> children = new ArrayList<>();
	protected Map<Integer, Constraint> constraints = new HashMap<>();
	private String tag;
	
	private static final float[] rectVerts = new float[] {
			1, 1,
			1, -1,
			-1, -1,
			-1, 1
	};
	
	private static final int[] rectIndices = new int[] {
			0, 1, 2,
			0, 3, 2
	};
	
	private static final float[] rectTextCoords = new float[] {
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f,
			0.0f, 0.0f
	};
	
	private static final RawModel rectangle = ModelLoader.loadColoredGUIModel(rectVerts, rectIndices);
	private static final RawModel texturedRectangle = ModelLoader.load2DModel(rectVerts, rectTextCoords, rectIndices, true);
	
	public GUIComponent(int meshType) {
		super(meshType);
		this.entity = new Entity(this);
		super.setModel(texturedRectangle);
	}
	
	public GUIComponent() {
		super(Mesh.GUI_COLORED);
		super.setModel(texturedRectangle);
		this.entity = new Entity(this);
	}
	
	public GUIComponent(String texturePath, float depth) {
		
		super(Mesh.GUI_TEXTURED);
		super.setModel(texturedRectangle);
		this.entity = new Entity(this);
		Material material = new Material();
		material.setTexture(new Texture(texturePath));
		super.setMaterial(material);
		this.depth = depth;
		
	}
	
	public GUIComponent(String texturePath, RawModel model, int meshType) {
		
		super(meshType);
		super.setModel(model);
		this.entity = new Entity(this);
		Material material = new Material();
		material.setTexture(new Texture(texturePath));
		
	}
	
	public GUIComponent(Vector3f color, float depth) {
		
		super(Mesh.GUI_COLORED);
		super.setModel(rectangle);
		this.entity = new Entity(this);
		Material material = new Material();
		System.out.println(color.getX());
		material.setColor(color);
		super.setMaterial(material);
		this.depth = depth;
		
	}
	
	public GUIComponent(XMLElement xml) {
		
		Vector3f color = new Vector3f(0f, 1f, 0f);
		Material material = new Material();
		this.entity = new Entity(this);
		
		if(xml.hasChildWithName("color")) {
			
			XMLElement colorElement = xml.anyChildWithName("color");
			color.setX(colorElement.getAttribute("r").getFloat());
			color.setY(colorElement.getAttribute("g").getFloat());
			color.setZ(colorElement.getAttribute("b").getFloat());
			material.setColor(color);
			
		}
		
		if(xml.hasAttribute("texture")) {
			System.out.println(xml.getAttribute("texture").getString());
			material.setTexture(new Texture(xml.getAttribute("texture").getString()));
			super.setModel(texturedRectangle);
			super.type = Mesh.GUI_TEXTURED;		
		} else {
			super.setModel(rectangle);
			super.type = Mesh.GUI_COLORED;
		}
		
		
		float depth = 0.9f;
		super.setMaterial(material);
		
		if(xml.hasAttribute("depth")) {
			depth = xml.getAttribute("depth").getFloat();
		}
		
		this.depth = depth;
		
		if(xml.hasChildWithName("constraints")) {
			this.loadConstraints(xml.anyChildWithName("constraints"));
		}
		
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public float getRadius() {
		return this.radius;
	}
	
	public Vector2f getSize() {
		return new Vector2f(this.entity.getTransform().getScale().getX()*2f, this.entity.getTransform().getScale().getY()*2f);
	}
	
	public Vector2f getCenter() {
		return new Vector2f(this.entity.getTransform().getPos().getX(), this.entity.getTransform().getPos().getY());
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public void enableChildren() {
		for(GUIComponent child: this.children) {
			child.enable();
		}
	}
	
	public void disableChildren() {
		for(GUIComponent child: this.children) {
			child.disable();
		}
	}
	
	public void enable() {
		super.enabled = true;
		for(GUIComponent child: this.children) {
			child.enable();
		}
	}
	
	public void disable() {
		super.enabled = false;
		for(GUIComponent child: this.children) {
			child.disable();
		}
	}
	
	public GUIComponent getParent() {
		return this.parent;
	}
	
	public void setParent(GUIComponent parent) {
		this.parent = parent;
	}
	
	public void addChild(GUIComponent child) {
		this.children.add(child);
		child.setParent(this);
	}
	
	public void addConstraint(Constraint constraint) {
		this.constraints.put(constraint.getValue(), constraint);
	}
	
	public float getDepth() {
		return this.depth;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public float getConstrainedValue(int constraintType) {
		return this.constraints.get(constraintType).getConstrainedValue();
	}
	
	public void onWindowResizeOverride(int width, int height) {}
	
	public void calculateConstraints() {
		Vector3f size = parent.getEntity().getTransform().getScale();
		for(Constraint constraint: constraints.values()) {
			constraint.constrain((int) (GLFWWindow.getWidth() * size.getX()), (int) (GLFWWindow.getHeight() * size.getY()), this);
		}
	}
	
	protected void setConstraint(Constraint constraint) {
		this.constraints.put(constraint.getValue(), constraint);
	}
	
	protected void updateChildren() {
		
		Vector3f scale = this.entity.getTransform().getScale();
		for(GUIComponent child: this.children) {
			child.onParentResize((int) (scale.getX() * GLFWWindow.getWidth()), (int) (scale.getY() * GLFWWindow.getHeight()));
		}
		
	}
	
	public void onParentResize(int width, int height) {
		
		System.out.println("window resized" + width + " " + height);
		this.onWindowResizeOverride(width, height);
		
		for(Constraint constraint: constraints.values()) {
			constraint.constrain(width, height, this);
		}
		this.updateChildren();
		
	}
	
	public void loadConstraints(XMLElement constraints) {
		
		for(XMLElement child: constraints.getChildren()) {
			String name = child.getName();
			int axis = 0;
			if(name.equals("x")) axis = Constraint.X;
			else if(name.equals("y")) axis = Constraint.Y;
			else if(name.equals("width")) axis = Constraint.WIDTH;
			else if(name.equals("height")) axis = Constraint.HEIGHT;
			
			float value = child.getAttribute("value").getFloat();
			String type = child.getAttribute("type").getString();
			int reference = Constraint.REF_CENTER;
			
			if(child.hasAttribute("reference")) {
				String attrib = child.getAttribute("reference").getString();
				if(attrib.equals("center")) {
					reference = Constraint.REF_CENTER;
				} else if(attrib.equals("corner")){
					reference = Constraint.REF_CORNER;
				}
			}
			
			if(type.equals("pixel")) {
				this.addConstraint(new PixelConstraint((int) value, axis, reference));
			} else if(type.equals("relative")) {
				this.addConstraint(new RelativeConstraint(value, axis, reference));
			} else if(type.equals("aspect")) {
				this.addConstraint(new AspectConstraint(value, axis));
			}
			
		}
		
	}
	
}
