package rendering;

import java.util.HashMap;

public class MasterRenderer {
	
	private static HashMap<Integer, IRenderer> renderers;
	
	public static void addRenderer(IRenderer renderer) {
		renderers.put(renderer.getType(), renderer);
	}
	
	public static void render(ModelBatch batch) {
		
	}
	
}
