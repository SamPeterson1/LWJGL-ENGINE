package rendering;

import java.util.ArrayList;

import models.Mesh;
import shaders.Shader;

public interface IRenderer {
	
	int getType();
	void render(ArrayList<Mesh> mesh);
	Shader getShader();
}
