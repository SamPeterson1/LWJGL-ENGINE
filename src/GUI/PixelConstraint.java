package GUI;

import math.Transform;

public class PixelConstraint extends Constraint {

	int pixels;
	int constraintValue;
	
	public PixelConstraint(int pixels, int value) {
		super(value);
		this.pixels = pixels;
	}
	
	@Override
	public void constrain(int width, int height, GUIComponent component) {
		
		Transform t = component.getEntity().getTransform();
		float value = 0;
		
		if(super.constraintValue == Constraint.X) {
			value = (float)(pixels-width)/width;
		} else if(super.constraintValue == Constraint.Y) {
			value = (float)(pixels-height)/height;
		} else if(super.constraintValue == Constraint.WIDTH) {
			value = (float) pixels/width;
		} else if(super.constraintValue == Constraint.HEIGHT) {
			value = (float) pixels/height;
		}
		
		super.setValue(value, t);
	}

}
