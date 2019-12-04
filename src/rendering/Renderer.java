package rendering;

import math.Matrix;
import models.Mesh;

public interface Renderer {
	
	void begin();
	void end();
	void loadMesh(Mesh mesh);
	void unloadMesh();
	void setTransformationMatrix(Matrix matrix);
	
}
