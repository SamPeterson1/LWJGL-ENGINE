package rendering;

import java.util.List;

import models.Entity;
import models.Mesh;

public interface Renderer {
	
	void begin();
	void render(Mesh mesh, List<Entity> entities);
	void end();
	
}
