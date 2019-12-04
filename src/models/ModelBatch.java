package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import text.Text;

public class ModelBatch {
	
	private static Map<Mesh, List<Entity>> entities = new LinkedHashMap<>();
	private static List<Entity> text = new ArrayList<>();
	
	public static void addEntities(Entity...entities) {
		for(Entity entity: entities) {
			addEntity(entity);
		}
	}
	
	public static void addEntity(Entity e) {
		if(e.getMesh() instanceof Text) {
			text.add(e);
		} else {
			if(entities.containsKey(e.getMesh())) {
				entities.get(e.getMesh()).add(e);
			} else {
				List<Entity> entitiesOfType = new ArrayList<>();
				entitiesOfType.add(e);
				entities.put(e.getMesh(), entitiesOfType);
			}
		}
	}
	
	public static List<Entity> getText() {
		return text;
	}
	
	public static Map<Mesh, List<Entity>> getEntities() {
		return entities;
	}
	
}
