package GUI;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.Model;
import models.ModelLoader;
import rendering.GLFWWindow;
import rendering.Material;

public class GUIComponent extends Mesh {
	
	protected Entity entity;
	protected GUIComponent parent;
	protected float depth;
	protected int centerX;
	protected int centerY;
	protected List<GUIComponent> children = new ArrayList<>();
	protected List<Constraint> constraints = new ArrayList<>();
	private String debugTag;
	
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
	
	private static final Model rectangle = ModelLoader.loadGUIModel(rectVerts, rectIndices);
	
	public GUIComponent() {
		super(Mesh.GUI);
	}
	
	public GUIComponent(Vector3f color, float depth, String debugTag) {
		
		super(Mesh.GUI);
		super.setModel(rectangle);
		Material material = new Material();
		material.setColor(color);
		super.setMaterial(material);
		this.depth = depth;
		this.debugTag = debugTag;
		
	}
	
	public void setParent(GUIComponent parent) {
		this.parent = parent;
	}
	
	public void addChild(GUIComponent child) {
		this.children.add(child);
		child.setParent(this);
	}
	
	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
	}
	
	public float getDepth() {
		return this.depth;
	}
	
	public void setEntity(Entity e) {
		this.entity = e;
	}
	
	public Entity getEntity() {
		return this.entity;
	}

	public void onWindowResize(int width, int height) {
		
		System.out.println("WIMNWAOED size" + width + " " + height + " " + debugTag);

		
		for(Constraint constraint: constraints) {
			constraint.constrain(width, height, this, this.parent);
		}
		
		Vector3f scale = this.entity.getTransform().getScale();
		for(GUIComponent child: this.children) {
			child.onWindowResize((int) (scale.getX() * GLFWWindow.getWidth()), (int) (scale.getY() * GLFWWindow.getHeight()));
		}
		
	}

}
