package GUI;

import events.EventListener;
import math.Utils;
import math.Vector3f;
import models.Text;
import rendering.GLFWWindow;
import rendering.ModelBatch;

public class Button extends GUIComponent {

	Vector3f color;
	Vector3f tintedColor;
	Vector3f normalScale;
	private boolean pressed;
	private boolean justPressed;
	
	public Button(Vector3f buttonColor, float depth) {
		super(buttonColor, depth);
		this.color = buttonColor.copyOf();
		this.tintedColor = buttonColor.copyOf();
		this.tintedColor.multiply(new Vector3f(0.8f, 0.8f, 0.8f));
	}
	
	public boolean justPressed() {
		//returns true for 1 frame only
		return this.justPressed;
	}
	
	public boolean pressed() {
		return this.pressed;
	}
	
	@Override
	public void update() {
		
		Vector3f pos = super.entity.getTransform().getPos();
		Vector3f scale = super.entity.getTransform().getScale();
		int width = (int) (scale.getX() * GLFWWindow.getWidth());
		int height = (int) (scale.getY() * GLFWWindow.getHeight());
		int x = (int) ((pos.getX() + 1)/2 * GLFWWindow.getWidth()) - width/2;
		int y = (int) ((-pos.getY() + 1)/2 * GLFWWindow.getHeight()) - height/2;
		if(Utils.inBounds(x, y, width, height, (int) GLFWWindow.getCursorX(), (int) GLFWWindow.getCursorY())) {
			super.material.setColor(color);
			
			this.pressed = EventListener.leftMouseDown();
			this.justPressed = EventListener.leftMouseJustDown();
			if(this.pressed) {
				super.entity.getTransform().setScaleX(super.getConstrainedValue(Constraint.WIDTH));
				super.entity.getTransform().setScaleY(super.getConstrainedValue(Constraint.HEIGHT));
			} else {
				super.entity.getTransform().setScaleX(super.getConstrainedValue(Constraint.WIDTH) * 1.1f);
				super.entity.getTransform().setScaleY(super.getConstrainedValue(Constraint.HEIGHT) * 1.1f);
			}
		} else {
			super.entity.getTransform().setScaleX(super.getConstrainedValue(Constraint.WIDTH));
			super.entity.getTransform().setScaleY(super.getConstrainedValue(Constraint.HEIGHT));
			super.material.setColor(tintedColor);
			this.pressed = false;
		}
		
		super.updateChildren();
		if(this.pressed) super.enable();
		else super.disable();
	}
	
}
