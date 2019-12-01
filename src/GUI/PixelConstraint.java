package GUI;

import math.Transform;
import rendering.GLFWWindow;

public class PixelConstraint extends Constraint {

	int pixels;
	private int reference;
	private float value;
	
	public PixelConstraint(int pixels, int value) {
		super(value);
		this.pixels = pixels;
		this.reference = Constraint.REF_CENTER;
	}
	
	public PixelConstraint(int pixels, int value, int reference) {
		super(value);
		this.pixels = pixels;
		this.reference = reference;
	}
	
	@Override
	public float getConstrainedValue() {
		return this.value;
	}
	
	@Override
	public void constrain(int width, int height, GUIComponent component) {
		
		Transform t = component.getEntity().getTransform();
		Transform parentT = component.getParent().getEntity().getTransform();
		
		if(super.constraintValue == Constraint.X) {
			value = (((((parentT.getPos().getX() + 1)/2f) * GLFWWindow.getWidth() + pixels)/GLFWWindow.getWidth())*2) -1;
			if(this.reference == Constraint.REF_CORNER) {
				value -= parentT.getScale().getX();
				value += t.getScale().getX();
			}
		} else if(super.constraintValue == Constraint.Y) {
			value = (((((parentT.getPos().getY() + 1)/2f) * GLFWWindow.getHeight() + pixels)/GLFWWindow.getHeight())*2) -1;
			if(this.reference == Constraint.REF_CORNER) {
				value += parentT.getScale().getY();
				value -= t.getScale().getY();
			}
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = (float) pixels/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = (float) pixels/GLFWWindow.getHeight();
		}
		
		super.setValue(value, t);
	}

}
