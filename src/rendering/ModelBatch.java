package rendering;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelBatch {
	
	private HashMap<Integer, ArrayList<GameElement>> elementsMap = new HashMap<>();
	private ArrayList<GameElement> elementsList = new ArrayList<>();
	
	public ModelBatch(GameElement...elements) {
		for(GameElement element: elements) {
			this.addElement(element);
			this.elementsList.add(element);
		}
	}
	
	public void addElement(GameElement element) {
		int type = element.getMesh().getType();
		if(!this.elementsMap.containsKey(type)) {
			ArrayList<GameElement> typeElements = new ArrayList<>();
			typeElements.add(element);
			this.elementsMap.put(type, typeElements);
		} else {
			this.elementsMap.get(type).add(element);
		}
	}

	public ArrayList<GameElement> getElementsOfType(int type) {
		return this.elementsMap.get(type);
	}
	
	public ArrayList<GameElement> getAllElements() {
		return this.elementsList;
	}
	
}
