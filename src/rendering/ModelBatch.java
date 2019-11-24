package rendering;

import java.util.ArrayList;
import models.GameElement;

public class ModelBatch {
	
	private ArrayList<GameElement> elements = new ArrayList<GameElement>();
	
	public ModelBatch(GameElement...elements) {
		for(GameElement element: elements) {
			this.elements.add(element);
		}
	}
	
	public void addElement(GameElement element) {
		this.elements.add(element);
	}
	
	public ArrayList<GameElement> getElements() {
		return this.elements;
	}
	
	public GameElement get(int index) {
		return this.elements.get(index);
	}
	
}
