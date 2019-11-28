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
import rendering.WindowListener;

public class GUIComponent extends Mesh implements WindowListener {
	
	Entity entity;
	float depth;
	int centerX;
	int centerY;
	List<Constraint> constraints = new ArrayList<>();
	
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
	
	public GUIComponent(Vector3f color, int centerX, int centerY, float depth) {
		
		super(Mesh.GUI);
		super.setModel(rectangle);
		Material material = new Material();
		material.setColor(color);
		super.setMaterial(material);
		this.entity = new Entity(this);
		this.entity.getTransform().setTranslation(new Vector3f(centerX, centerY, 0));
		this.centerX = centerX;
		this.centerY = centerY;
		GLFWWindow.addListener(this);
		this.depth = depth;
		this.constraints.add(new RelativeConstraint(0.5f, Constraint.X));
		//this.constraints.add(new PixelConstraint(0, Constraint.X));
		this.constraints.add(new RelativeConstraint(0.7f, Constraint.HEIGHT));
		this.constraints.add(new AspectConstraint(0.5f, Constraint.WIDTH));
		
	}
	
	public float getDepth() {
		return this.depth;
	}
	
	@Override
	public void update() {

	}
	
	public Entity getEntity() {
		return this.entity;
	}

	@Override
	public void onResize(int width, int height) {
		for(Constraint constraint: constraints) {
			constraint.constrain(width, height, this);
		}
	}

}
