package GUI;

import math.Transform;
import rendering.GLFWWindow;

public class AspectConstraint extends Constraint {

	private float aspect;
	
	public AspectConstraint(float aspect, int constraintValue) {
		super(constraintValue);
		this.aspect = aspect;
	}

	@Override
	public void constrain(int width, int height, GUIComponent component, GUIComponent parent) {
		
		Transform t = component.getEntity().getTransform();
		float value = 0;
		
		if(super.constraintValue == Constraint.X) {
			value = ((t.getPos().getY()+1)/2f*GLFWWindow.getHeight()*this.aspect/GLFWWindow.getWidth())*2f - 1;
		} else if(super.constraintValue == Constraint.Y) {
			value = t.getPos().getX()*GLFWWindow.getHeight() * this.aspect/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = t.getScale().getY()*GLFWWindow.getHeight() * this.aspect/GLFWWindow.getWidth();
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = t.getScale().getX()*GLFWWindow.getWidth() * this.aspect/GLFWWindow.getHeight();
		}
		
		super.setValue(value, t);
		
	}

}
