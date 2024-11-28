package player;

import GUI.Constraint;
import GUI.GUIComponent;
import GUI.MasterGUI;
import math.Vector4f;

public class Player {
	
	private float health = 100f;
	private float oxygen = 100f;
	
	private GUIComponent healthBar;
	private GUIComponent oxygenBar;
	
	private Constraint healthBarWidth;
	private Constraint oxygenBarWidth;
	
	private static float BAR_WIDTH = 8.5f;
	private float maxHealth = 100f;
	private float maxOxygen = 100f;
	
	public Player() {
		this.healthBar = MasterGUI.getComponent("healthBar");
		this.oxygenBar = MasterGUI.getComponent("oxygenBar");
		
		this.healthBarWidth = this.healthBar.getConstraint(Constraint.WIDTH);
		this.oxygenBarWidth = this.oxygenBar.getConstraint(Constraint.WIDTH);
	}
	
	public void update() {
		this.tickVitals();
	}
	
	private void tickVitals() {
		if(this.oxygen > 0) {
			this.oxygen -= 1;
		} else if(this.health > 0) {
			this.oxygen = 0;
			this.health -= 0.1;
		} else {
			this.health = 0;
		}
		
		this.healthBarWidth.setValue(health*BAR_WIDTH/maxHealth);
		this.oxygenBarWidth.setValue(oxygen*BAR_WIDTH/maxOxygen);
		
		this.healthBar.calculateConstraints();
		this.oxygenBar.calculateConstraints();
		
		if(this.health < 0.5f * maxHealth) {
			float i = ((float) Math.cos(System.currentTimeMillis()/250.0) + 1);
			Vector4f healthBarColor = this.healthBar.getMaterial().getColor();
			healthBarColor.setX(1-i);
			healthBarColor.setY(i);
		}
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public float getOxygen() {
		return this.oxygen;
	}
	
}
