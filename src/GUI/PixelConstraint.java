package GUI;

import math.Transform;
import window.GLFWWindow;

public class PixelConstraint extends Constraint {

	private int reference;
	private float value;
	
	public PixelConstraint(int pixels, int value) {
		super(value);
		super.value = pixels;
		this.reference = Constraint.REF_CENTER;
	}
	
	public PixelConstraint(int pixels, int value, int reference) {
		super(value);
		super.value = pixels;
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
			value = (((((parentT.getPos().getX() + 1)/2f) * GLFWWindow.getWidth() + super.value)/GLFWWindow.getWidth())*2) -1;
			if(this.reference == Constraint.REF_CORNER) {
				value -= parentT.getScale().getX();
				value += t.getScale().getX();
			}
		} else if(super.constraintValue == Constraint.Y) {
			value = (((((parentT.getPos().getY() + 1)/2f) * GLFWWindow.getHeight() + super.value)/GLFWWindow.getHeight())*2) -1;
			if(this.reference == Constraint.REF_CORNER) {
				value += parentT.getScale().getY();
				value -= t.getScale().getY();
			}
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = (float) super.value/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = (float) super.value/GLFWWindow.getHeight();
		}
		
		super.setValue(value, t);
	}

}
