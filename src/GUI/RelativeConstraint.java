package GUI;

import math.Transform;

public class RelativeConstraint extends Constraint {

	private float relativePixels;
	
	public RelativeConstraint(float relativePixels, int constraintValue) {
		super(constraintValue);
		this.relativePixels = relativePixels;
	}

	@Override
	public void constrain(int width, int height, GUIComponent component) {
		
		Transform t = component.getEntity().getTransform();
		float value = 0;
		if(super.isPosition()) {
			value = (float)relativePixels - 0.5f;
		} else {
			value = relativePixels;
		}
		
		System.out.println(value + "VALUE" + super.constraintValue);
		super.setValue(value, t);
		
	}
	
	
	
}
