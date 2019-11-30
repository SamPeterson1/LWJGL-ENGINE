package GUI;

import math.Transform;
import math.Vector2f;
import rendering.GLFWWindow;

public class PixelConstraint extends Constraint {

	int pixels;
	private float value;
	
	public PixelConstraint(int pixels, int value) {
		super(value);
		this.pixels = pixels;
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
			value = (float)(pixels*2-GLFWWindow.getWidth())/GLFWWindow.getWidth() + parentT.getPos().getX() - parentT.getScale().getX();
		} else if(super.constraintValue == Constraint.Y) {
			value = (float)(GLFWWindow.getHeight()-pixels*2)/GLFWWindow.getHeight() + parentT.getPos().getY() + parentT.getScale().getY();
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = (float) pixels/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = (float) pixels/GLFWWindow.getHeight();
		}
		
		super.setValue(value, t);
	}

}
