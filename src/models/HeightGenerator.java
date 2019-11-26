package models;

import java.util.Random;

public class HeightGenerator {
	
	private float amplitude;
	private Random rand = new Random();
	private int seed;
	
	public HeightGenerator(float amplitude) {
		this.seed = rand.nextInt(1000000000);
		System.out.println(this.getNoise(5, 14));
		System.out.println(this.getNoise(5, 14));
	}
	
	public float getNoise(int x, int z) {
		rand.setSeed(x *40000 + z * 324187 + seed);
		return rand.nextFloat() * 2f - 1f;
	}
}
