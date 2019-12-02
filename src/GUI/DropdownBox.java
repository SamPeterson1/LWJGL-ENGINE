package GUI;

import java.util.LinkedHashMap;
import java.util.Map;

import events.EventListener;
import math.Utils;
import math.Vector3f;
import models.Text;
import rendering.GLFWWindow;
import rendering.ModelBatch;
import xml.XMLElement;

public class DropdownBox extends GUIComponent {
	
	Vector3f color;
	Vector3f tintedColor;
	String[] options;
	String selected;
	Text selectedText;
	Map<GUIComponent, Text> cells = new LinkedHashMap<>();
	GUIComponent optionContainer;
	boolean showOptions = false;
	int optionPadding;
	int initHeight;
	
	public DropdownBox(XMLElement element, GUIComponent parent) {
		
		super("/assets/DropdownBox.png", 0.9f);
		
		if(element.hasAttribute("depth")) {
			super.depth = element.getAttribute("depth").getFloat();
		}
		
		Vector3f color = new Vector3f(0.8f, 0.8f, 0.8f);
		if(element.hasChildWithName("color")) {
			XMLElement colorElement = element.anyChildWithName("color");
			color.setX(colorElement.getAttribute("r").getFloat());
			color.setY(colorElement.getAttribute("g").getFloat());
			color.setZ(colorElement.getAttribute("b").getFloat());
		}
		
		Vector3f textColor = new Vector3f();
		if(element.hasChildWithName("textColor")) {
			XMLElement colorElement = element.anyChildWithName("textColor");
			textColor.setX(colorElement.getAttribute("r").getFloat());
			textColor.setY(colorElement.getAttribute("g").getFloat());
			textColor.setZ(colorElement.getAttribute("b").getFloat());
		}
		
		float textSize = 0.5f;
		if(element.hasAttribute("textSize")) {
			textSize = element.getAttribute("textSize").getFloat();
		}
		
		String[] options = new String[0];
		if(element.hasAttribute("options")) {
			options = element.getAttribute("options").getArray();
		}
		
		if(element.hasChildWithName("constraints")) {
			System.out.println("loaded constraints");
			super.loadConstraints(element.anyChildWithName("constraints"));
		} else {
		}
		super.setParent(parent);
		this.calculateConstraints();
		this.createDropdown(color, textColor, options, textSize);
		
		
	}
	
	public DropdownBox(Vector3f buttonColor, String[] options, float depth) {
		super("/assets/DropdownBox.png", depth);
		this.createDropdown(buttonColor, new Vector3f(), options, 0.5f);
	}
	
	private void createDropdown(Vector3f buttonColor, Vector3f textColor, String[] options, float textSize) {
		this.tintedColor = buttonColor.copyOf();
		this.tintedColor.multiply(new Vector3f(0.9f, 0.9f, 0.9f));
		this.color = buttonColor.copyOf();
		MasterGUI.addComponent(this);
		this.addConstraint(new RelativeConstraint(0.1f, Constraint.HEIGHT));
		this.addConstraint(new AspectConstraint(4, Constraint.WIDTH));
		this.calculateConstraints();
		GUIComponent bgColor = new GUIComponent(buttonColor, depth+0.01f);
		bgColor.addConstraint(new RelativeConstraint(1f, Constraint.WIDTH));
		bgColor.addConstraint(new RelativeConstraint(1f, Constraint.HEIGHT));
		bgColor.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		bgColor.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		this.addChild(bgColor);
		ModelBatch.addEntity(bgColor.getEntity());
		this.optionContainer = new GUIComponent(new Vector3f(0f, 0f, 0f), 0.1f);
		this.optionContainer.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		this.optionContainer.addConstraint(new PixelConstraint((int) (-this.getConstrainedValue(Constraint.HEIGHT) * GLFWWindow.getHeight()), Constraint.Y, Constraint.REF_CORNER));
		this.optionContainer.addConstraint(new RelativeConstraint(options.length, Constraint.HEIGHT));
		this.optionContainer.addConstraint(new RelativeConstraint(1f, Constraint.WIDTH));
		this.optionContainer.disable();
		ModelBatch.addEntity(optionContainer.getEntity());
		this.addChild(optionContainer);
		int index = 0;
		
		this.selectedText = new Text(options[0], 1f, textColor, "/assets/TestFont.fnt", 0.5f);
		selectedText.addConstraint(new RelativeConstraint(textSize, Constraint.HEIGHT));
		selectedText.addConstraint(new AspectConstraint(selectedText.pixelWidth()/(float)selectedText.pixelHeight(), Constraint.WIDTH));
		selectedText.addConstraint(new PixelConstraint(10, Constraint.X, Constraint.REF_CORNER));
		selectedText.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		this.addChild(selectedText);
		ModelBatch.addEntity(selectedText.getEntity());
		
		for(String option: options) {
			System.out.println(option);
			GUIComponent cell = new GUIComponent(buttonColor, 0.1f);
			cell.addConstraint(new RelativeConstraint(1f, Constraint.WIDTH));
			cell.addConstraint(new RelativeConstraint(1f/options.length, Constraint.HEIGHT));
			cell.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
			cell.addConstraint(new PixelConstraint((int) (this.getConstrainedValue(Constraint.HEIGHT) * GLFWWindow.getHeight() * index), Constraint.Y, Constraint.REF_CORNER));
			index --;
			optionContainer.addChild(cell);
			ModelBatch.addEntity(cell.getEntity());
			
			Text t = new Text(option, 1f, textColor, "/assets/TestFont.fnt", 0.5f);
			t.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
			t.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
			t.addConstraint(new RelativeConstraint(textSize, Constraint.HEIGHT));
			t.addConstraint(new AspectConstraint(t.pixelWidth()/(float)t.pixelHeight(), Constraint.WIDTH));
			cell.addChild(t);
			cells.put(cell, t);
			
			ModelBatch.addEntity(t.getEntity());
			
		}
		
		this.optionContainer.disableChildren();
	}
	
	@Override
	public void onWindowResizeOverride(int width, int height) {
		this.optionContainer.setConstraint(new PixelConstraint((int) (-this.getConstrainedValue(Constraint.HEIGHT) * GLFWWindow.getHeight()), Constraint.Y, Constraint.REF_CORNER));
		int index = 0;
		for(GUIComponent cell: this.cells.keySet()) {
			cell.setConstraint(new PixelConstraint((int) (((this.getConstrainedValue(Constraint.HEIGHT) * GLFWWindow.getHeight())-1) * index), Constraint.Y, Constraint.REF_CORNER));
			index --;
		}
	}
	
	public void addText() {
		ModelBatch.addEntity(this.selectedText.getEntity());
	}
	
	@Override
	public void update() {
		Vector3f pos = super.getEntity().getTransform().getPos();
		Vector3f scale = super.getEntity().getTransform().getScale();
		int width = (int) (scale.getX() * GLFWWindow.getWidth());
		int height = (int) (scale.getY() * GLFWWindow.getHeight());
		int x = (int) ((pos.getX() + 1)/2 * GLFWWindow.getWidth()) - width/2;
		int y = (int) ((-pos.getY() + 1)/2 * GLFWWindow.getHeight()) - height/2;
		if(Utils.inBounds(x, y, width, height, (int) GLFWWindow.getCursorX(), (int) GLFWWindow.getCursorY())) {
			super.getMaterial().setColor(color);	
			if(EventListener.leftMouseJustDown()) {
				this.showOptions = !this.showOptions;
				if(!this.showOptions) {
					this.optionContainer.disableChildren();
				} else {
					this.optionContainer.enableChildren();
				}
			}
		} else {
			super.getMaterial().setColor(tintedColor);
		}
		for(GUIComponent cell: this.cells.keySet()) {
			pos = cell.getEntity().getTransform().getPos();
			scale = cell.getEntity().getTransform().getScale();
			width = (int) (scale.getX() * GLFWWindow.getWidth());
			height = (int) (scale.getY() * GLFWWindow.getHeight());
			x = (int) ((pos.getX() + 1)/2 * GLFWWindow.getWidth()) - width/2;
			y = (int) ((-pos.getY() + 1)/2 * GLFWWindow.getHeight()) - height/2;
			if(Utils.inBounds(x, y, width, height, (int) GLFWWindow.getCursorX(), (int) GLFWWindow.getCursorY())) {
				cell.getMaterial().setColor(color);	
				if(EventListener.leftMouseJustDown()) {
					this.showOptions = !this.showOptions;
					if(!this.showOptions) {
						this.optionContainer.disableChildren();
					} else {
						this.optionContainer.enableChildren();
					}
					String text = this.cells.get(cell).getText();
					this.selectedText.setText(text);
					selectedText.setConstraint(new AspectConstraint(this.selectedText.pixelWidth()/(float)this.selectedText.pixelHeight(), Constraint.WIDTH));
				}
			} else {
				cell.getMaterial().setColor(tintedColor);
			}
		}
		super.updateChildren();
	}
	
}
