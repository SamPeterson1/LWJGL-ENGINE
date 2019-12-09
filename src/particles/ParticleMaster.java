package particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import math.Vector3f;
import models.ModelBatch;
import rendering.Texture;

public class ParticleMaster {
	
	private static List<Particle> particles = new ArrayList<>();
	private static Texture t = new Texture("/assets/particleAtlas.png");
	private static ParticleMesh mesh = new ParticleMesh(t);
	
	public static void addParticle(Particle p) {
		particles.add(p);
		ModelBatch.addEntity(p.getEntity());
	}
	
	public static void update() {
		
		long time = System.currentTimeMillis();
		Vector3f velocity = new Vector3f((float)Math.random() * 0.2f - 0.1f, (float)Math.random() * 0.2f - 0.1f, (float)Math.random() * 0.2f - 0.1f);
		velocity.normalize();
		velocity.multiplyScalar(0.1f);
		Particle particle = new Particle(new Vector3f(1f, 1f, 0f), velocity, 0f, 5000, mesh);
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
		
		System.out.println(particles.size() + " " + (System.currentTimeMillis() - time));
		
	}
	
}
