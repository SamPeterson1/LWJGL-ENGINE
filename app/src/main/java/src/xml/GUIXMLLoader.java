package xml;

import GUI.Button;
import GUI.CheckBox;
import GUI.DropdownBox;
import GUI.GUIComponent;
import GUI.MasterGUI;
import models.ModelBatch;
import text.Text;
import window.GLFWWindow;

public class GUIXMLLoader {
	
	private XMLFile gui;

	public GUIXMLLoader(String filePath) {
		this.gui = new XMLFile(filePath);
		this.loadToGUI(gui.getRoot(), new MasterGUI());
	}
	
	private void loadToGUI(XMLElement component, GUIComponent parentComponent) { 
		
		for(XMLElement child: component.getChildren()) {
			String name = child.getName();
			GUIComponent guiComponent = null;
			
			if(name.equals("button")) {
				guiComponent = new Button(child);
			} else if(name.equals("text")) {
				guiComponent = new Text(child);
			} else if(name.equals("dropdown")) {
				guiComponent = new DropdownBox(child, parentComponent);
			} else if(name.equals("checkbox")) {
				guiComponent = new CheckBox(child);
			} else if(name.equals("rectangle")) {
				guiComponent = new GUIComponent(child);
			}
			
			if(guiComponent != null) {
				if(child.hasAttribute("tag")) {
					guiComponent.setTag(child.getAttribute("tag").getString());
				}
				MasterGUI.addComponentByTag(guiComponent);
				parentComponent.addChild(guiComponent);
				ModelBatch.addEntity(guiComponent.getEntity());
				this.loadToGUI(child, guiComponent);
			}
		}
		
		if(parentComponent instanceof MasterGUI) {
			((MasterGUI)parentComponent).onResize(GLFWWindow.getWidth(), GLFWWindow.getHeight());
		}
		
	}
	
}
