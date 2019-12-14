package particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import math.Vector3f;
import models.ModelBatch;
import rendering.Texture;

public class ParticleMaster {
	
	private static List<Particle> particles = new ArrayList<>();
	
	public static void addParticle(Particle p) {
		particles.add(p);
		ModelBatch.addEntity(p);
	}
	
	public static void addParticleSystem(ParticleSystem system) {
		
		for(Particle p: system.getParticles()) {
			particles.add(p);
			ModelBatch.addEntity(p);
		}
		
	}
	
	public static void update() {
		
		Iterator<Particle> iterator = particles.iterator();
		while(iterator.hasNext()) {
			Particle p = iterator.next();
			boolean stillAlive = p.updateParticle();
			if(!stillAlive) {
				p.remove();
				iterator.remove();
			}
		}
		
	}
	
}
