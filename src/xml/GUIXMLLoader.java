package xml;

import GUI.Button;
import GUI.DropdownBox;
import GUI.GUIComponent;
import GUI.MasterGUI;
import models.Text;
import rendering.ModelBatch;

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
			}
			
			if(guiComponent != null) {
				parentComponent.addChild(guiComponent);
				ModelBatch.addEntity(guiComponent.getEntity());
				this.loadToGUI(child, guiComponent);
			}
		}
		
	}
	
}
