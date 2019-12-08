package terrain;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class Face {
	
	List<Vertex> vertices = new ArrayList<>();
	
	public void addVertex(Vertex vertex) {
		this.vertices.add(vertex);
	}

	public List<Vertex> getVertices() {
		return this.vertices;
	}
	
	public Vector3f getNormal() {
		
		Vector3f a = vertices.get(1).getPos().copyOf();
		a.subtract(vertices.get(0).getPos());
		Vector3f b = vertices.get(2).getPos().copyOf();
		b.subtract(vertices.get(1).getPos());
		
		float x = a.getX() * b.getZ() - a.getZ() * b.getY();
		float y = a.getZ() * b.getX() - a.getX() * b.getZ();
		float z = a.getX() * b.getY() - a.getY() * b.getX();
		
		return new Vector3f(x, y, z);
		
	}
	
}
