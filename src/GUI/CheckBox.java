package GUI;

import events.EventListener;
import math.Vector3f;
import misc.Utils;
import models.Mesh;
import models.ModelBatch;
import rendering.Material;
import rendering.Texture;
import text.Text;
import window.GLFWWindow;
import xml.XMLElement;

public class CheckBox extends GUIComponent {
	
	Texture checkedHover;
	Texture uncheckedHover;
	Texture checked;
	Texture unchecked;
	boolean isChecked = false;
	
	public CheckBox(XMLElement element) {
		
		super(Mesh.GUI_TEXTURED);
		
		float depth = 0.9f;
		if(element.hasAttribute("depth")) {
			depth = element.getAttribute("depth").getFloat();
		}
		super.depth = depth;
		
		String label = "";
		float textSize = 1f;
		Vector3f textColor = new Vector3f();
		int xOff = 10;
		
		if(element.hasChildWithName("label")) {
			
			XMLElement text = element.anyChildWithName("label");
			label = text.getAttribute("text").getString();
			textSize = text.getAttribute("size").getFloat();
			
			if(text.hasAttribute("xOff")) {
				xOff = text.getAttribute("xOff").getInt();
			}
			
			if(text.hasChildWithName("color")) {
				
				XMLElement color = text.anyChildWithName("color");
				textColor.setX(color.getAttribute("r").getFloat());
				textColor.setY(color.getAttribute("g").getFloat());
				textColor.setZ(color.getAttribute("b").getFloat());
			
			}
			
		}
		
		if(element.hasChildWithName("constraints")) {
			super.loadConstraints(element.anyChildWithName("constraints"));
		}
		
		this.loadCheckbox(textColor, textSize, xOff, label);
	}
	
	private void loadCheckbox(Vector3f textColor, float textSize, int xOff, String labelStr) {
		
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
		
		Text label = new Text(labelStr, 1f, textColor, "/assets/TestFont.fnt", 0.5f);
		this.addChild(label);
		label.addConstraint(new RelativeConstraint(textSize, Constraint.HEIGHT));
		label.addConstraint(new AspectConstraint(label.pixelWidth()/(float)label.pixelHeight(), Constraint.WIDTH));
		label.addConstraint(new PixelConstraint((int) (GLFWWindow.getWidth() * this.getConstrainedValue(Constraint.WIDTH) + xOff), Constraint.X, Constraint.REF_CORNER));
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
