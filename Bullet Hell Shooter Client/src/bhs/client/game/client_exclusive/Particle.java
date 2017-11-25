package bhs.client.game.client_exclusive;

import game.protocol.GameObject;
import processing.core.PApplet;

public class Particle extends GameObject{
	PApplet world;
	
	float speedX = 0;
	float speedY = 0;
	float size = (float) 1.5;
	boolean active = false;
	
	public Particle(PApplet world) {
		this.world = world;
	}

	public void activate(float x, float y) {
		if (!isActive()) {
			this.x = x;
			this.y = y;
			this.active = true;
		}
	}

	public void run() {
		if (isActive()) {
			move();
			display();
		}
	}
	
	public void move() {
		x += speedX;
		y += speedY;
	}

	public void display() {
		world.ellipse(x, y, size * 2, size * 2);
	}
	
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	boolean isActive() {
		return active;
	}

	public void deactivate() {
		this.active = false;
	}
}
