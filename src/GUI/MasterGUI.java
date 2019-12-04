package GUI;

import java.util.HashMap;
import java.util.Map;

import rendering.GLFWWindow;
import rendering.WindowListener;

public class MasterGUI extends GUIComponent implements WindowListener {

	private static GUIComponent root;
	private static Map<String, GUIComponent> componentsByTag = new HashMap<>();
	
	public MasterGUI() {
		GLFWWindow.addListener(this);
		root = new GUIComponent();
	}
	
	public static void addComponentByTag(GUIComponent component) {
		componentsByTag.put(component.getTag(), component);
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
	
	public static GUIComponent getComponent(String tag) {
		return componentsByTag.get(tag);
	}
	
	@Override
	public void onResize(int width, int height) {
		for(GUIComponent child: root.children) {
			child.onWindowResize(width, height);
		}
	}
	
}
