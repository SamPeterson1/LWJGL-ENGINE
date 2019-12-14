package rendering;

import java.util.List;
import java.util.Map;

import models.Entity;
import models.Mesh;

public interface Renderer2 {
	
	void begin();
	void render(Mesh mesh, List<Entity> entities);
	void end();
	
}
