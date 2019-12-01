package GUI;

import java.util.LinkedHashMap;
import java.util.Map;

import events.EventListener;
import math.Utils;
import math.Vector3f;
import models.Text;
import rendering.GLFWWindow;
import rendering.ModelBatch;

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
	
	public DropdownBox(Vector3f buttonColor, String[] options, float fontSize, float depth) {
		super("/assets/DropdownBox.png", depth);
		this.tintedColor = buttonColor.copyOf();
		this.tintedColor.multiply(new Vector3f(0.8f, 0.8f, 0.8f));
		this.color = buttonColor.copyOf();
		this.addConstraint(new RelativeConstraint(0.05f, Constraint.HEIGHT));
		this.addConstraint(new AspectConstraint(4f, Constraint.WIDTH));
		MasterGUI.addComponent(this);
		GUIComponent bgColor = new GUIComponent(buttonColor, depth+0.01f);
		bgColor.addConstraint(new RelativeConstraint(1f, Constraint.WIDTH));
		bgColor.addConstraint(new RelativeConstraint(1f, Constraint.HEIGHT));
		bgColor.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		bgColor.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		this.addChild(bgColor);
		ModelBatch.addEntity(bgColor.getEntity());
		this.calculateConstraints();
		this.optionContainer = new GUIComponent(new Vector3f(0f, 0f, 0f), 0.1f);
		this.optionContainer.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
		this.optionContainer.addConstraint(new PixelConstraint((int) (-this.getConstrainedValue(Constraint.HEIGHT) * GLFWWindow.getHeight()), Constraint.Y, Constraint.REF_CORNER));
		this.optionContainer.addConstraint(new RelativeConstraint(options.length, Constraint.HEIGHT));
		this.optionContainer.addConstraint(new RelativeConstraint(1f, Constraint.WIDTH));
		this.optionContainer.disable();
		ModelBatch.addEntity(optionContainer.getEntity());
		this.addChild(optionContainer);
		int index = 0;
		
		this.selectedText = new Text(options[0], 1f, new Vector3f(0.8f, 0.8f, 0.8f), "/assets/TestFont.fnt", 0.1f);
		selectedText.addConstraint(new RelativeConstraint(0.5f, Constraint.HEIGHT));
		selectedText.addConstraint(new AspectConstraint((float)selectedText.pixelWidth()/(float)selectedText.pixelHeight(), Constraint.WIDTH));
		selectedText.addConstraint(new PixelConstraint(10, Constraint.X, Constraint.REF_CORNER));
		selectedText.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
		this.addChild(selectedText);
		ModelBatch.addEntity(selectedText.getEntity());
		
		for(String option: options) {
			System.out.println(option);
			GUIComponent cell = new GUIComponent(new Vector3f(), 0.1f);
			cell.addConstraint(new RelativeConstraint(1f, Constraint.WIDTH));
			cell.addConstraint(new RelativeConstraint(1f/options.length, Constraint.HEIGHT));
			cell.addConstraint(new RelativeConstraint(0.5f, Constraint.X));
			cell.addConstraint(new PixelConstraint((int) (this.getConstrainedValue(Constraint.HEIGHT) * GLFWWindow.getHeight() * index), Constraint.Y, Constraint.REF_CORNER));
			index --;
			optionContainer.addChild(cell);
			ModelBatch.addEntity(cell.getEntity());
			
			Text t = new Text(option, 1f, new Vector3f(0.8f, 0.8f, 0.8f), "/assets/TestFont.fnt", 0.1f);
			t.addConstraint(new PixelConstraint(0, Constraint.X, Constraint.REF_CORNER));
			t.addConstraint(new RelativeConstraint(0.5f, Constraint.Y));
			t.addConstraint(new RelativeConstraint(0.6f, Constraint.HEIGHT));
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
					selectedText.setConstraint(new AspectConstraint((float)selectedText.pixelWidth()/(float)selectedText.pixelHeight(), Constraint.WIDTH));
				}
			} else {
				cell.getMaterial().setColor(tintedColor);
			}
		}
		super.updateChildren();
	}
	
}
