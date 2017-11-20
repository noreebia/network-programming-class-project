package bhs.client.game.client_exclusive;

import java.util.ArrayList;

import processing.core.PApplet;
import model.*;

public class BulletSystem {
	PApplet world;
	User owner;
	boolean isFiring;
	int length = 9;
	int width = 4;
	int lastFiredTime = 0;
	float reloadTime = 100;
	
	float bulletSpeedModifier;
	float bulletSpeedModifierStraight = 15;
	float bulletSpeedModifierDiagonal = (float) (bulletSpeedModifierStraight / Math.sqrt(2));
	float bulletSpeedX, bulletSpeedY;
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public ArrayList<Integer> collidedBullets = new ArrayList<Integer>();

	public BulletSystem(PApplet world, User owner) {
		this.world = world;
		this.owner = owner;
	}

	public void display() {
		world.rect(-width / 2, -owner.size, width, -length);
	}

	public void run() {
		fire();
		manageBullets();
		displayBullets();
		System.out.println("Active bullets: " + bullets.size());
	}
	
	public void removeInactiveBullets() {
		int i;
		for(i=0; i< bullets.size();i++) {
			if(!bullets.get(i).isActive()) {
				bullets.remove(i);
			}
		}
	}
	
	public void fire() {
		if (isFiring) {
			float bulletSpawnLocationX = (float) (owner.x + 1.5 * Math.sin(owner.directionModifier * Math.PI / 4) * length);
			float bulletSpawnLocationY = (float) (owner.y - 1.5 * Math.cos(owner.directionModifier * Math.PI / 4) * length);
			if (world.millis() - lastFiredTime >= reloadTime) {
				bullets.add(new Bullet(bulletSpawnLocationX, bulletSpawnLocationY, owner.directionModifier));
				bullets.get(bullets.size()-1).setRGB(owner.getBackupRGB(0), owner.getBackupRGB(1), owner.getBackupRGB(2));
				lastFiredTime = world.millis();
			}
		}
	}
	
	public void manageBullets() {
		int i;
		for(i =0; i < bullets.size(); i++) {
			moveBullet(bullets.get(i));
			/*
			if( bullets.get(i).isOutOfMap() || !bullets.get(i).isActive()) {
				bullets.remove(i);
			}
			*/
			if( bullets.get(i).isOutOfMap(world.width, world.height) || !bullets.get(i).isActive()) {
				bullets.remove(i);
			}
		}
	}
	
	public void displayBullets() {
		for(Bullet b: bullets) {
			world.stroke(b.getRGB(0),b.getRGB(1), b.getRGB(2));
			world.fill(b.getRGB(0), b.getRGB(1), b.getRGB(2));
			world.ellipse(b.getX(), b.getY(), 2 * b.size, 2 * b.size);
		}
	}
	
	public void moveBullet(Bullet b) {
		bulletSpeedX = 0;
		bulletSpeedY = 0;
		switch(b.direction) {
		case 0:
			bulletSpeedY--;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 1:
			bulletSpeedX++;
			bulletSpeedY--;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		case 2:
			bulletSpeedX++;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 3:
			bulletSpeedX++;
			bulletSpeedY++;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		case 4:
			bulletSpeedY++;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 5:
			bulletSpeedX--;
			bulletSpeedY++;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		case 6:
			bulletSpeedX--;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 7:
			bulletSpeedX--;
			bulletSpeedY--;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		}
		b.move(bulletSpeedX * bulletSpeedModifier, bulletSpeedY * bulletSpeedModifier);
	}

	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void startFiring() {
		isFiring = true;
	}
	
	public void stopFiring() {
		isFiring = false;
	}
}
