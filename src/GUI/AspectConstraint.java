package GUI;

import math.Transform;

public class AspectConstraint extends Constraint {

	private float aspect;
	
	public AspectConstraint(float aspect, int constraintValue) {
		super(constraintValue);
		this.aspect = aspect;
	}

	@Override
	public void constrain(int width, int height, GUIComponent component) {
		
		Transform t = component.getEntity().getTransform();
		float value = 0;
		
		if(super.constraintValue == Constraint.X) {
			value = t.getPos().getY() * this.aspect;
		} else if(super.constraintValue == Constraint.Y) {
			value = t.getPos().getX() * this.aspect;
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = t.getScale().getY()*height * this.aspect/width;
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = t.getScale().getX()*height * this.aspect/width;
		}
		
		super.setValue(value, t);
		
	}

}
