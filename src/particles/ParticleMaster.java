package particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import math.Vector3f;
import models.ModelBatch;

public class ParticleMaster {
	
	private static List<Particle> particles = new ArrayList<>();
	
	public static void addParticle(Particle p) {
		particles.add(p);
		ModelBatch.addEntity(p.getEntity());
	}
	
	public static void update() {
		
		Particle particle = new Particle(new Vector3f(1f, 1f, 0f), new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()), 0.01f, 1000);
		ParticleMaster.addParticle(particle);
		
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
