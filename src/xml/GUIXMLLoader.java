package xml;

import GUI.Button;
import GUI.CheckBox;
import GUI.DropdownBox;
import GUI.GUIComponent;
import GUI.MasterGUI;
import models.ModelBatch;
import text.Text;

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
				System.out.println("oh you foot you");
				guiComponent = new DropdownBox(child, parentComponent);
			} else if(name.equals("checkbox")) {
				guiComponent = new CheckBox(child);
			} else if(name.equals("rectangle")) {
				System.out.println("loaded rectangle");
				guiComponent = new GUIComponent(child);
			}
			
			if(guiComponent != null) {
				System.out.println("FOOOOOOOOOOOOOo0");
				if(child.hasAttribute("tag")) {
					System.out.println("YAAAAAAAAAAAAAAA");
					guiComponent.setTag(child.getAttribute("tag").getString());
				}
				MasterGUI.addComponentByTag(guiComponent);
				parentComponent.addChild(guiComponent);
				ModelBatch.addEntity(guiComponent.getEntity());
				this.loadToGUI(child, guiComponent);
			}
		}
		
	}
	
}
