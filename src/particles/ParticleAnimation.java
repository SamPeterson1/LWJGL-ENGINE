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
	private boolean fade;
	
	public ParticleAnimation(int stages, int numRows) {
		this(stages, numRows, true);
	}
	
	public ParticleAnimation(int stages, int numRows, boolean fade) {
		this.stages = stages;
		this.numRows = numRows;
		this.fade = fade;
	}
	
	public void update(float percentLifetime) {
		
		float progression = percentLifetime * this.stages;
		
		int index1 = (int) progression;
		int index2 = index1 < stages - 1 ? index1 + 1 : index1;
		
		this.blend = fade ? progression - index1 : 1;
		if(index1 <= stages-1) fadeOut = stages*(1-percentLifetime);
		else fadeOut = 1f;
		this.setTextureOffset(texOffset1, index1);
		this.setTextureOffset(texOffset2, index2);
		
	}
	
	public int getAtlasRows() {
		System.out.println(this.numRows);
		return this.numRows;
	}
	
	public float getFadeOut() {
		return this.fadeOut;
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
