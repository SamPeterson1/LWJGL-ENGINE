package particles;

import java.util.ArrayList;
import java.util.List;

public class ParticleMaster {
	
	private static List<ParticleSystem> systems = new ArrayList<>();
	
	public static void addParticle(ParticleSystem system) {
		systems.add(system);
	}
	
	public static void update() {
		
		for(ParticleSystem system: systems) {
			system.update();
		}
		
	}
	
}
