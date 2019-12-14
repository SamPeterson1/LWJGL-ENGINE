package particles;

import java.util.Random;

import math.Vector3f;
import rendering.Texture;

// r*r = dx*dx + dy*dy
// -dy*dy = dx*dx - r*r
// dy*dy = r*r - dx*dx;
// dy = sqrt(r*r - dx*dx)

public class CircularEmission implements ParticleEmission {

	private float y;
	private float radius;
	private Random random;
	
	
	public CircularEmission(float y, float radius) {
		this.y = y;
		this.radius = radius;
		this.random = new Random();
	}
	
	@Override
	public Particle emit() {
		
		float x = (1f-random.nextFloat()*2f) * radius;
		float zRange = (float) Math.sqrt(radius*radius - x*x);
		float z = zRange - random.nextFloat()*zRange*2f;
		
		return new Particle(new Vector3f(x, y, z), new Vector3f());
		
	}
	
}
