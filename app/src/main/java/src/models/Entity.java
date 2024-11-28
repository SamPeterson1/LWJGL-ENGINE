package models;

import java.util.ArrayList;
import java.util.List;

import math.Transform;
import math.TransformListener;
import math.Vector3f;

public class Entity {
	
	protected Transform transform;
	protected Mesh mesh;
	protected boolean enabled = true;
	private static List<TransformListener> listeners = new ArrayList<>();
	private boolean updatesListeners = true;
	
	public Entity(Mesh mesh) {
		this.transform = new Transform();
		this.mesh = mesh;
	}
	
	public Entity() {
		this(null);
	}
	
	public void setVisibility(boolean updatesListeners) {
		this.updatesListeners = updatesListeners;
	}
	
	public static void addListener(TransformListener listener) {
		listeners.add(listener); 
	}
	
	private void updateListener(TransformListener listener) {
		Vector3f toListener = this.transform.getPos().copyOf();
		toListener.subtract(listener.getPosition().copyOf());
		float distToListener = toListener.magnitude();
		if(distToListener < listener.getRadius()) {
			listener.update();
		}
	}
	
	public void updateListeners() {
		if(this.updatesListeners) {
			for(TransformListener listener: listeners) {
				this.updateListener(listener);
			}
		}
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
	
	public int getMeshType() {
		return this.mesh.getType();
	}
	
	public void update() {
		this.mesh.update();
		if(this.transform.checkForUpdates() && this.updatesListeners) {
			this.updateListeners();
		}
	}
	
}
