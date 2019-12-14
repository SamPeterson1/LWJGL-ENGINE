package particles;

import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.ModelLoader;
import models.RawModel;
import rendering.Material;
import rendering.Texture;

public class Particle extends Entity {
	
	private static final float[] rectVerts = new float[] {
			1, 1,
			1, -1,
			-1, -1,
			-1, 1
	};
	
	private static final int[] rectIndices = new int[] {
			0, 1, 2,
			0, 3, 2
	};
	
	private static final RawModel rectangle = ModelLoader.loadColoredGUIModel(rectVerts, rectIndices);
	
	private Vector3f pos;
	private Vector3f velocity;
	private float gravity;
	private int lifetime;
	private long birthTime;
	private ParticleMesh mesh;
	private ParticleAnimation animation;
	
	public Particle(Vector3f pos, Vector3f velocity) {
		
		this(pos, velocity, 0.01f, 3000, new ParticleMesh(new Texture("/assets/particleAtlas.png")));
		
		//this.velocity = velocity;
		//this.birthTime = System.currentTimeMillis();
		//this.entity = new Entity();
		//this.entity.getTransform().setTranslation(pos);  	

	}
	
	public Particle(Vector3f pos, Vector3f velocity, float gravity, int lifetime, ParticleMesh mesh) {
		
		this.mesh = mesh;
		this.velocity = velocity;
		this.gravity = gravity;
		this.lifetime = lifetime;
		this.birthTime = System.currentTimeMillis();
		this.transform.setTranslation(pos);
		
	}
	
	public void setMesh(ParticleMesh mesh) {
		this.mesh = mesh;
		super.setMesh(mesh);
	}
	
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	
	public void setTexture(Texture t) {
		
		this.mesh.setTexture(t);
		
	}

	public void remove() {
		ModelBatch.removeEntity(this.mesh, this);
	}
	
	public ParticleAnimation getAnimation() {
		return this.animation;
	}
	
	public void setAnimation(ParticleAnimation animation) {
		this.animation = animation;
	}
	
	public boolean updateParticle() {
		this.animation.update((float)(System.currentTimeMillis()-birthTime)/lifetime);
		this.transform.getPos().add(velocity);
		this.velocity.addY(-gravity);
		return System.currentTimeMillis() - birthTime < lifetime;
	}
	
	public Vector3f getPos() {
		return pos;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public float getGravity() {
		return gravity;
	}
	
	
	
}
