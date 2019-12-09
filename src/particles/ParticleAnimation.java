package particles;

import math.Vector2f;
import math.Vector3f;

public class ParticleAnimation {
	
	private int stages;
	private int numRows;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	private float fadeOut;
	
	public ParticleAnimation(int stages, int numRows) {
		this.stages = stages;
		this.numRows = numRows;
	}
	
	private float fade(float x) {
		return (float) (1f/(1+Math.pow(Math.E, x)));
	}
	
	public void update(float percentLifetime) {
		
		float progression = percentLifetime * this.stages;
		
		int index1 = (int) progression;
		int index2 = index1 < stages - 1 ? index1 + 1 : index1;
		
		this.blend = progression - index1;
		this.fadeOut = 1-percentLifetime;
		this.setTextureOffset(texOffset1, index1);
		this.setTextureOffset(texOffset2, index2);
		
	}
	
	public Vector3f getTextureData() {
		return new Vector3f(this.numRows, this.blend, this.fadeOut);
	}
	
	public Vector2f getTexOffset1() {
		return this.texOffset1;
	}
	
	public Vector2f getTexOffset2() {
		return this.texOffset2;
	}
	
	private void setTextureOffset(Vector2f offset, int index) {
		int column = index % this.numRows;
		int row = index / this.numRows;
		offset.setX((float) column/this.numRows);
		offset.setY((float) row/this.numRows);
		
	}
	
}
