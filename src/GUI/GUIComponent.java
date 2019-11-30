package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.Model;
import models.ModelLoader;
import rendering.GLFWWindow;
import rendering.Material;
import rendering.Texture;

public class GUIComponent extends Mesh {
	
	protected Entity entity;
	protected GUIComponent parent;
	protected float depth;
	protected int centerX;
	protected int centerY;
	protected List<GUIComponent> children = new ArrayList<>();
	protected Map<Integer, Constraint> constraints = new HashMap<>();
	
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
	
	private static final Model rectangle = ModelLoader.loadColoredGUIModel(rectVerts, rectIndices);
	private static final Model texturedRectangle = ModelLoader.load2DModel(rectVerts, rectTextCoords, rectIndices);
	
	public GUIComponent(int meshType) {
		super(meshType);
		this.entity = new Entity(this);
	}
	
	public GUIComponent() {
		super(Mesh.GUI_COLORED);
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
	
	public GUIComponent(String texturePath, Model model, int meshType) {
		
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
		material.setColor(color);
		super.setMaterial(material);
		this.depth = depth;
		
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
	
	protected void updateChildren() {
		
		Vector3f scale = this.entity.getTransform().getScale();
		for(GUIComponent child: this.children) {
			child.onWindowResize((int) (scale.getX() * GLFWWindow.getWidth()), (int) (scale.getY() * GLFWWindow.getHeight()));
		}
		
	}
	
	public void onWindowResize(int width, int height) {
		
		for(Constraint constraint: constraints.values()) {
			constraint.constrain(width, height, this);
		}
		this.updateChildren();
		
		
	}

}
