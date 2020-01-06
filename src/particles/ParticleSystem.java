package particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import math.Vector3f;
import models.ModelBatch;
import rendering.Texture;

public class ParticleSystem {
	
	private List<Particle> livingParticles = new ArrayList<>();
	private List<Particle> deadParticles = new ArrayList<>();
	private int maxParticles;
	private int emissionWait;
	private int emissionBurst;
	private long lastEmission;
	private float gravity;
	private int lifetime;
	private ParticleEmission emission;
	private int emissionCounter;
	private ParticleMesh mesh;
	private int numRows;
	private int stages;
	
	public ParticleSystem(ParticleEmission emission, int emissionWait, int emissionBurst, float gravity, int lifetime, String texture, int stages, int numRows) {
		
		this.emissionWait = emissionWait;
		this.emissionBurst = emissionBurst;
		this.gravity = gravity;
		this.lifetime = lifetime;
		this.mesh = new ParticleMesh(new Texture(texture));
		this.numRows = numRows;
		this.emission = emission;
		this.stages = stages;
		
	}
	
	public int numParticles() {
		return this.livingParticles.size();
	}
	
	public void update() {
			emissionCounter ++;
			Iterator<Particle> iterator = this.livingParticles.iterator();
			while(iterator.hasNext()) {
				Particle p = iterator.next();
				boolean stillAlive = p.updateParticle();
				if(!stillAlive) {
					iterator.remove();
					deadParticles.add(p);
					p.disable();
				}
			}
			if(System.currentTimeMillis() - this.lastEmission >= this.emissionWait) {
				for(int i = 0; i < this.emissionBurst; i ++) {
					this.spawnParticle();
				}
			}

	}
	
	private void spawnParticle() {
		
		Particle p = null;
		
		if(this.deadParticles.size() == 0) {
			p = new Particle(new Vector3f(), new Vector3f());	
			p.setMesh(mesh);
			p.setAnimation(new ParticleAnimation(stages, numRows));
			p.setGravity(gravity);
			p.setLifetime(lifetime);
			ModelBatch.addEntity(p); 
		} else {
			p = this.deadParticles.get(0);
			this.deadParticles.remove(0);
			p.enable();
			p.reuse();
		}
		
		this.emission.emit(p);
		this.livingParticles.add(p);
		this.lastEmission = System.currentTimeMillis();
		
	}	

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}


	public float getGravity() {
		return gravity;
	}

	public float getLifetime() {
		return lifetime;
	}	
	
}
