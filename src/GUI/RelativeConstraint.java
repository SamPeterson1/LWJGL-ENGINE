package GUI;

import math.Transform;
import rendering.GLFWWindow;

public class RelativeConstraint extends Constraint {

	private float relativePixels;
	private float value;
	
	public RelativeConstraint(float relativePixels, int constraintValue) {
		super(constraintValue);
		this.relativePixels = relativePixels;
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
			float newX = (parentT.getPos().getX() + 1)/2f;
			newX -= parentT.getScale().getX()/2;
			newX += relativePixels*parentT.getScale().getX();
			value = (newX*2f)-1;
		} else if(super.constraintValue == Constraint.Y){
			float newY = (parentT.getPos().getY() + 1)/2f;
			newY -= parentT.getScale().getY()/2f;
			newY += relativePixels*parentT.getScale().getY();
			value = (newY*2f)-1;
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = relativePixels * width/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = relativePixels * height/GLFWWindow.getHeight();
		}
		
		super.setValue(value, t);
		
	}
	
	
	
}
