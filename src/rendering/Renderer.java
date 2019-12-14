package rendering;

import math.Matrix;
import models.Entity;
import models.Mesh;

public abstract class Renderer {
	
	public abstract void begin();
	public abstract void end();
	public abstract void loadMesh(Mesh mesh);
	public abstract void unloadMesh();
	public abstract void setTransformationMatrix(Matrix matrix);
	
	public void loadEntity(Entity e) {}
	
}
