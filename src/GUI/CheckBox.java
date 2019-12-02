package GUI;

import events.EventListener;
import math.Utils;
import math.Vector3f;
import models.Mesh;
import models.Text;
import rendering.GLFWWindow;
import rendering.Material;
import rendering.ModelBatch;
import rendering.Texture;

public class CheckBox extends GUIComponent {
	
	Texture checkedHover;
	Texture uncheckedHover;
	Texture checked;
	Texture unchecked;
	boolean isChecked = false;
	
	public CheckBox(float depth, String labelStr) {
		
		super(Mesh.GUI_TEXTURED);
		
		this.checkedHover = new Texture("/assets/CheckedHover.png");
		this.uncheckedHover = new Texture("/assets/UncheckedHover.png");
		this.checked = new Texture("/assets/Checked.png");
		this.unchecked = new Texture("/assets/Unchecked.png");
		
		Material material = new Material();
		material.setTexture(unchecked);
		material.setTexture(unchecked);
		super.setMaterial(material);
		this.addConstraint(new RelativeConstraint(0.025f, Constraint.WIDTH));
		this.addConstraint(new AspectConstraint(1f, Constraint.HEIGHT));
		MasterGUI.addComponent(this);
		this.calculateConstraints();
		
		Text label = new Text(labelStr, 1f, new Vector3f(), "/assets/TestFont.fnt", 0.5f);
		this.addChild(label);
		label.addConstraint(new RelativeConstraint(1f, Constraint.HEIGHT));
		label.addConstraint(new AspectConstraint(label.pixelWidth()/(float)label.pixelHeight(), Constraint.WIDTH));
		label.addConstraint(new PixelConstraint((int) (GLFWWindow.getWidth() * this.getConstrainedValue(Constraint.WIDTH) + 10), Constraint.X, Constraint.REF_CORNER));
		label.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		ModelBatch.addEntity(label.getEntity());
		
	}
	
	public boolean isChecked() {
		return this.isChecked;
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
			if(this.isChecked) {
				super.material.setTexture(checkedHover);
			} else {
				super.material.setTexture(uncheckedHover);
			}
			if(EventListener.leftMouseJustDown()) {
				this.isChecked = !this.isChecked;
			}
		} else {
			if(this.isChecked) {
				super.material.setTexture(checked);
			} else {
				super.material.setTexture(unchecked);
			}
		}
		
	}
	
}
