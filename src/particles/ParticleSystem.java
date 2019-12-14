package particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import math.Vector3f;
import models.Mesh;
import models.ModelBatch;
import models.TexturedMesh;
import rendering.Texture;

public class ParticleSystem {
	
	private List<Particle> particles = new ArrayList<>();
	private float emissionRate;
	private float gravity;
	private int lifetime;
	private ParticleEmission emission;
	private int emissionCounter;
	private ParticleMesh mesh;
	private int numRows;
	private int stages;
	
	public ParticleSystem(ParticleEmission emission, float emissionRate, float gravity, int lifetime, String texture, int stages, int numRows) {
		
		this.emissionRate = emissionRate;
		this.gravity = gravity;
		this.lifetime = lifetime;
		this.mesh = new ParticleMesh(new Texture(texture));
		this.numRows = numRows;
		this.emission = emission;
		this.stages = stages;
		
	}
	
	public void update() {
			emissionCounter ++;
			if(emissionCounter == (int) 1f/Math.min(1, emissionRate)) {
				this.spawnParticle();
			}
			
			for(int i = 0; i < (int) (emissionRate - 1); i ++) {
				this.spawnParticle();
			}
	}
	
	private void spawnParticle() {
		Particle p = this.emission.emit();
		p.setMesh(mesh);
		p.setAnimation(new ParticleAnimation(stages, numRows));
		p.setGravity(gravity);
		p.setLifetime(lifetime);
		ParticleMaster.addParticle(p);  
		emissionCounter = 0;
	}
	
	public void setEmissionRate(float emissionRate) {
		this.emissionRate = emissionRate;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public float getEmissionRate() {
		return emissionRate;
	}

	public float getGravity() {
		return gravity;
	}

	public float getLifetime() {
		return lifetime;
	}	
	
}
