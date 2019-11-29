package GUI;

import math.Transform;
import rendering.GLFWWindow;

public class PixelConstraint extends Constraint {

	int pixels;
	int constraintValue;
	
	public PixelConstraint(int pixels, int value) {
		super(value);
		this.pixels = pixels;
	}
	
	@Override
	public void constrain(int width, int height, GUIComponent component, GUIComponent parent) {
		
		Transform t = component.getEntity().getTransform();
		Transform parentT = parent.getEntity().getTransform();
		float value = 0;
		
		System.out.println("PArent" + (1+parentT.getPos().getY()) + " " + parentT.getPos().getY());
		
		if(super.constraintValue == Constraint.X) {
			value = (float)(pixels*2-GLFWWindow.getWidth())/GLFWWindow.getWidth() + parentT.getPos().getX() - parentT.getScale().getX();
		} else if(super.constraintValue == Constraint.Y) {
			value = (float)(GLFWWindow.getHeight()-pixels*2)/GLFWWindow.getHeight() + parentT.getPos().getY() + parentT.getScale().getY();
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = (float) pixels/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = (float) pixels/GLFWWindow.getHeight();
		}
		
		super.setValue(value, t);
		
		System.out.println("FOOOOOOOOT" + t.getPos().getX());
	}

}
