package rendering;

import math.Matrix;
import models.Mesh;

public interface IRenderer {
	
	void begin();
	void end();
	void loadMesh(Mesh mesh);
	void unloadMesh();
	void setTransformationMatrix(Matrix matrix);
	
}
