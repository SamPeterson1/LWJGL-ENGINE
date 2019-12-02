package GUI;

import rendering.GLFWWindow;
import rendering.WindowListener;

public class MasterGUI extends GUIComponent implements WindowListener {

	private static GUIComponent root;
	
	public MasterGUI() {
		GLFWWindow.addListener(this);
		root = new GUIComponent();
	}
	
	@Override
	public void addChild(GUIComponent child) {
		root.children.add(child);
		child.setParent(root);
	}
	
	public static void addComponent(GUIComponent component) {
		root.children.add(component);
		component.setParent(root);
	}

	@Override
	public void onResize(int width, int height) {
		for(GUIComponent child: root.children) {
			child.onWindowResize(width, height);
		}
	}
	
}
