package GUI;

import math.Transform;

public abstract class Constraint {
	
	public static int WIDTH = 0;
	public static int HEIGHT = 1;
	public static int X = 2;
	public static int Y = 3;
	
	protected int constraintValue;
	
	public Constraint(int constraintValue) {
		this.constraintValue = constraintValue;
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
	
	public abstract void constrain(int width, int height, GUIComponent component, GUIComponent parent);
	
}
