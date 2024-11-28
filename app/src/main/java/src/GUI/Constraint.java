package GUI;

import math.Transform;

public abstract class Constraint {
	
	public static int REF_CENTER = 0;
	public static int REF_CORNER = 1;
	
	public static int WIDTH = 0;
	public static int HEIGHT = 1;
	public static int X = 2;
	public static int Y = 3;
	
	protected int constraintValue;
	protected float value;
	
	public Constraint(int constraintValue) {
		this.constraintValue = constraintValue;
	}
	
	public int getValue() {
		return this.constraintValue;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	protected boolean usesWidth() {
		return constraintValue == Constraint.WIDTH || constraintValue == Constraint.X;
	}
	
	protected boolean usesHeight() {
		return constraintValue == Constraint.HEIGHT || constraintValue == Constraint.Y;
	}
	
	protected boolean isPosition() {
		return constraintValue == Constraint.X || constraintValue == Constraint.Y;
	}
	
	protected void setValue(float value, Transform t) {
		
		if(this.constraintValue == Constraint.X) {
			t.setTranslationX(value);
		} else if(this.constraintValue == Constraint.Y) {
			t.setTranslationY(value);
		} else if(this.constraintValue == Constraint.WIDTH) {
			t.setScaleX(value);
		} else if(this.constraintValue == Constraint.HEIGHT) {
			t.setScaleY(value);
		}
		
	}
	
	public abstract float getConstrainedValue();
	public abstract void constrain(int width, int height, GUIComponent component);
	
}
