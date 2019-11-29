package rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Entity;
import models.Mesh;

public class ModelBatch {
	
	
	private Map<Mesh, List<Entity>> entities = new HashMap<>();
	
	public ModelBatch(Entity...entities) {
		for(Entity entity: entities) {
			this.addEntity(entity);
		}
	}
	
	public void addEntity(Entity e) {
		if(this.entities.containsKey(e.getMesh())) {
			this.entities.get(e.getMesh()).add(e);
		} else {
			List<Entity> entitiesOfType = new ArrayList<>();
			entitiesOfType.add(e);
			this.entities.put(e.getMesh(), entitiesOfType);
		}
	}
	
	public Map<Mesh, List<Entity>> getEntities() {
		System.out.println(this.entities.keySet().size() + " fr");
		return this.entities;
	}
	
}
