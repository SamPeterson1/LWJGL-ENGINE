package GUI;

import math.Vector3f;
import rendering.GLFWWindow;
import rendering.WindowListener;

public class MasterGUI extends GUIComponent implements WindowListener {

	public MasterGUI() {
	}
	
	public void addComponent(GUIComponent component) {
		super.children.add(component);
		component.setParent(this);
		GLFWWindow.addListener(this);
	}

	@Override
	public void onResize(int width, int height) {
		for(GUIComponent child: super.children) {
			child.onWindowResize(width, height);
		}
	}
	
}
