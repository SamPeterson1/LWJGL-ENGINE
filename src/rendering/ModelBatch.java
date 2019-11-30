package rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Entity;
import models.Mesh;

public class ModelBatch {
	
	private static Map<Mesh, List<Entity>> entities = new LinkedHashMap<>();
	
	public static void addEntities(Entity...entities) {
		for(Entity entity: entities) {
			addEntity(entity);
		}
	}
	
	public static void addEntity(Entity e) {
		if(entities.containsKey(e.getMesh())) {
			entities.get(e.getMesh()).add(e);
		} else {
			List<Entity> entitiesOfType = new ArrayList<>();
			entitiesOfType.add(e);
			entities.put(e.getMesh(), entitiesOfType);
		}
	}
	
	public static Map<Mesh, List<Entity>> getEntities() {
		return entities;
	}
	
}
