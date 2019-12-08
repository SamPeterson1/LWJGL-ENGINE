package particles;

import math.Vector3f;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import models.ModelLoader;
import models.RawModel;

public class Particle extends Mesh {
	
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
	
	public Particle(Vector3f pos, Vector3f velocity, float gravity, int lifetime) {
		
		super(Mesh.PARTICLE);
		super.setModel(rectangle);
		this.pos = pos;
		this.velocity = velocity;
		this.gravity = gravity;
		this.lifetime = lifetime;
		this.birthTime = System.currentTimeMillis();
		this.entity = new Entity(this);
		
	}

	public Entity getEntity() {
		return this.entity;
	}
	
	public void remove() {
		ModelBatch.removeEntity(this, this.entity);
	}
	
	public boolean updateParticle() {
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
