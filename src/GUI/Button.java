package GUI;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import events.EventListener;
import math.Utils;
import math.Vector3f;
import models.Text;
import rendering.GLFWWindow;
import rendering.Material;
import rendering.ModelBatch;
import xml.XMLElement;

public class Button extends GUIComponent {

	Vector3f color;
	Vector3f tintedColor;
	Vector3f normalScale;
	private boolean pressed;
	private boolean justPressed;
	
	public Button(XMLElement button) {
		
		Vector3f color = new Vector3f(0.0f, 1.0f, 0.0f);
		if(button.hasChildWithName("color")) {
			
			XMLElement colorElement = button.anyChildWithName("color");
			color.setX(colorElement.getAttribute("r").getFloat());
			color.setY(colorElement.getAttribute("g").getFloat());
			color.setZ(colorElement.getAttribute("b").getFloat());
			
		}
		
		super.loadConstraints(button.anyChildWithName("constraints"));
		
		super.material = new Material();
		super.material.setColor(color);
		
		this.color = color.copyOf();
		this.tintedColor = color.copyOf();
		this.tintedColor.multiply(new Vector3f(0.8f, 0.8f, 0.8f));
		
	}
	
	public Button(Element element) {
		Vector3f color = new Vector3f(0.0f, 1.0f, 0.0f); //default color
		System.out.println(element == null);
		NodeList colorElements = element.getChildNodes();
		for(int i = 0; i < colorElements.getLength(); i ++) {
			if(colorElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) colorElements.item(i);
				if(child.getNodeName().equals("color")) {
					color.setX(Float.valueOf(child.getAttribute("r")));
					color.setY(Float.valueOf(child.getAttribute("g")));
					color.setZ(Float.valueOf(child.getAttribute("b")));
				} else if(child.getNodeName().equals("text")) {
					Vector3f textColor = new Vector3f();
					float textSize = 0.5f;
					NodeList textChildNodes = child.getChildNodes();
					for(int ii = 0; ii < textChildNodes.getLength(); ii ++) {
						if(textChildNodes.item(ii).getNodeType() == Node.ELEMENT_NODE) {
							Element textElement = (Element) textChildNodes.item(ii);
							if(textElement.getNodeName().equals("color")) {
								textColor.setX(Float.valueOf(textElement.getAttribute("r")));
								textColor.setY(Float.valueOf(textElement.getAttribute("g")));
								textColor.setZ(Float.valueOf(textElement.getAttribute("b")));
							}
						} else if(textChildNodes.item(ii).getNodeType() == Node.ATTRIBUTE_NODE) {
							System.out.println("foot");
							textSize = Float.valueOf(textChildNodes.item(ii).getNodeValue());
						}
					}
					/*
					Text t = new Text(child.getAttribute("text"), textColor, "/assets/TestFont.fnt");
					this.addChild(t);
					t.setConstraint(new RelativeConstraint(textSize, Constraint.HEIGHT));
					t.setConstraint(new AspectConstraint(t.pixelWidth()/(float)t.pixelHeight(), Constraint.WIDTH));
					t.setConstraint(new RelativeConstraint(0.5f, Constraint.X));
					t.setConstraint(new RelativeConstraint(0.5f, Constraint.Y));
					ModelBatch.addEntity(t.getEntity());
					*/
				}
			}
		}

		super.material = new Material();
		super.material.setColor(color);
		
		this.color = color.copyOf();
		this.tintedColor = color.copyOf();
		this.tintedColor.multiply(new Vector3f(0.8f, 0.8f, 0.8f));
		
	}
	
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
	}
	
}
