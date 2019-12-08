package terrain;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class Vertex {
	
	private Vector3f pos;
	private List<Vertex> connected = new ArrayList<>();
	private List<Face> faces = new ArrayList<>();
	
	public Vertex(float x, float y, float z) {
		this.pos = new Vector3f(x, y, z);
	}
	
	public Vertex(Vector3f pos) {
		this.pos = pos;
	}
	
	public void addFace(Face face) {
		if(!this.faces.contains(face))
		this.faces.add(face);
	}
	
	public void addConnection(Vertex other) {
		this.connected.add(other);
	}
	
	public List<Face> getFaces() {
		return this.faces;
	}
	
	public List<Vertex> getConnected() {
		return this.connected;
	}
	
	public Vector3f getPos() {
		return this.pos;
	}
	
	public float getX() {
		return this.pos.getX();
	}
	
	public float getY() {
		return this.pos.getY();
	}
	
	public float getZ() {
		return this.pos.getZ();
	}
	
	public Vector3f getNormal() {
		
		Vector3f avg = new Vector3f();
		for(Face f: faces) {
			avg.add(f.getNormal());
		}
		
		avg.multiplyScalar(1f/faces.size());
		avg.normalize();
		return avg;
		
	}
	
}
