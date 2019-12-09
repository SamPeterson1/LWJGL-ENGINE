package particles;

import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.ModelLoader;
import models.RawModel;
import rendering.Material;
import rendering.Texture;

public class Particle {
	
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
	private Entity entity;
	private ParticleMesh mesh;
	private ParticleAnimation animation;
	
	public Particle(Vector3f pos, Vector3f velocity, float gravity, int lifetime, ParticleMesh mesh) {
		
		this.animation = new ParticleAnimation(15, 4);
		this.mesh = mesh;
		this.pos = pos;
		this.velocity = velocity;
		this.gravity = gravity;
		this.lifetime = lifetime;
		this.birthTime = System.currentTimeMillis();
		this.entity = new Entity(this.mesh);
		this.entity.setAnimation(animation);
		
	}

	public Entity getEntity() {
		return this.entity;
	}
	
	public void remove() {
		ModelBatch.removeEntity(this.mesh, this.entity);
	}
	
	public boolean updateParticle() {
		this.animation.update((float)(System.currentTimeMillis()-birthTime)/lifetime);
		this.entity.getTransform().getPos().add(velocity);
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
