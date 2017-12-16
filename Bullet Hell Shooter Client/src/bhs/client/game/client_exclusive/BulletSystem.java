package bhs.client.game.client_exclusive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import game.protocol.Bullet;
import game.protocol.Player;
import processing.core.PApplet;

public class BulletSystem {
	PApplet world;
	Player owner;
	boolean isFiring;
	int length = 9;
	int width = 4;
	int lastFiredTime = 0;
	float reloadTime = 100;

	float bulletSpeedModifier;
	float bulletSpeedModifierStraight = 15;
	float bulletSpeedModifierDiagonal = (float) (bulletSpeedModifierStraight / Math.sqrt(2));
	float bulletSpeedX, bulletSpeedY;

	public Vector<Bullet> bullets = new Vector<Bullet>();

	public ArrayList<Integer> collidedBullets = new ArrayList<Integer>();

	public short[] bulletRGB;

	public BulletSystem(PApplet world, Player owner, short[] bulletRGB) {
		this.world = world;
		this.owner = owner;
		owner.setBullets(getBullets());
		this.bulletRGB = bulletRGB;
	}

	public void display() {
		world.rect(-width / 2, -owner.size, width, -length);
	}

	public void run() {
		fire();
		manageBullets();
		displayBullets();
	}

	public void removeInactiveBullets() {
		int i;
		for (i = 0; i < bullets.size(); i++) {
			if (!bullets.get(i).isActive()) {
				bullets.remove(i);
			}
		}
	}

	public void fire() {
		if (isFiring) {
			float bulletSpawnLocationX = (float) (owner.x
					+ 1.5 * Math.sin(owner.getDirection() * Math.PI / 4) * length);
			float bulletSpawnLocationY = (float) (owner.y
					- 1.5 * Math.cos(owner.getDirection() * Math.PI / 4) * length);

			if (world.millis() - lastFiredTime >= reloadTime) {
				bullets.add(new Bullet(bulletSpawnLocationX, bulletSpawnLocationY, owner.getDirection()));

				bullets.get(bullets.size() - 1).setRGB(getBulletRGB(0), getBulletRGB(1), getBulletRGB(2));
				lastFiredTime = world.millis();
			}
		}
	}

	public void manageBullets() {
		int i;
		for (i = 0; i < bullets.size(); i++) {
			moveBullet(bullets.get(i));

			if (bullets.get(i).isOutOfMap(world.width, world.height) || !bullets.get(i).isActive()) {
				bullets.remove(i);
			}
		}
	}

	public void displayBullets() {
		for (Bullet b : bullets) {
			world.stroke(b.getRGB(0), b.getRGB(1), b.getRGB(2));
			world.fill(b.getRGB(0), b.getRGB(1), b.getRGB(2));
			world.ellipse(b.getX(), b.getY(), 2 * b.size, 2 * b.size);
		}
	}

	public void moveBullet(Bullet b) {
		bulletSpeedX = 0;
		bulletSpeedY = 0;
		switch (b.direction) {
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

	public Vector<Bullet> getBullets() {
		return bullets;
	}

	public void startFiring() {
		isFiring = true;
	}

	public void stopFiring() {
		isFiring = false;
	}

	public short getBulletRGB(int i) {
		return bulletRGB[i];
	}
}
