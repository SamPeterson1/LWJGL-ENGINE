package models;

import math.Matrix;
import math.Transform;
import math.Vector3f;

public class GameElement {
	
	private Mesh mesh;
	private Transform transform;
	private static boolean a;
	
	public GameElement(Mesh mesh) {
		this.mesh = mesh;
		this.transform = new Transform();
	}
	
	public GameElement() {
		this.mesh = null;
		this.transform = new Transform();
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public void update() {
		
		this.transform.rotateY(1);
		//this.transform.rotateX((a ? -1 : 4));
		//a = !a;
	}
	
	public Matrix getTransformationMatrix() {
		return this.transform.getTransform();
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
	
}
