package rendering;

import java.util.ArrayList;
import java.util.HashMap;

import models.GameElement;

public class ModelBatch {
	
	private HashMap<Integer, ArrayList<GameElement>> elements = new HashMap<>();
	public ModelBatch(GameElement...elements) {
		for(GameElement element: elements) {
			this.addElement(element);
		}
	}
	
	public void addElement(GameElement element) {
		int type = element.getMesh().getType();
		if(!this.elements.containsKey(type)) {
			ArrayList<GameElement> typeElements = new ArrayList<>();
			typeElements.add(element);
			this.elements.put(type, typeElements);
		} else {
			this.elements.get(type).add(element);
		}
	}

	public ArrayList<GameElement> getElementsOfType(int type) {
		return this.elements.get(type);
	}
	
	public HashMap<Integer, ArrayList<GameElement>> getAllElements() {
		return this.elements;
	}
	
}
