package bhs.client.game.client_exclusive;

import java.util.ArrayList;

import model.*;

import processing.core.PApplet;

public class User extends GameObject {
	
	PApplet world;
	
	public BulletSystem bulletSystem;
	public short directionModifier = 0;

	double angle;
	
	boolean invincible;
	boolean[] moving = new boolean[4];
	boolean[] facing = new boolean[4];

	float speed ;
	float straightSpeed = 5;
	float diagonalSpeed = (float) (straightSpeed / Math.sqrt(2));

	long timeOfHit;
	int colorFlashCount;
	short[] backupRGB;

	public User(PApplet world) {
		this.world = world;
		setRGB((short) 0, (short) 255, (short) 255);
		backupRGB = rgb.clone();
		bulletSystem = new BulletSystem(world, this);
	}

	public void run() {
		adjustSpeed();
		move();
		setDirection();
		bulletSystem.run();
		deactivateInvincibility();
		//display();
	}

	void display() {
		world.stroke(rgb[0], rgb[1], rgb[2]);
		world.pushMatrix();
		world.translate(x, y);
		setAngle();
		world.rotate((float) angle);
		world.fill(rgb[0], rgb[1], rgb[2]);
		// world.ellipse(0, 0, size * 2, size * 2);
		world.beginShape();
		world.vertex(0, -size - 5);
		world.vertex(-size, 5);
		world.vertex(0, 15);
		world.vertex(size, 5);
		world.endShape(world.CLOSE);
		// bulletSystem.display();
		world.popMatrix();
	}

	public void adjustSpeed() {
		int count = 0;
		int i;
		for (i = 0; i < 4; i++) {
			if (moving[i]) {
				count++;
			}
		}
		if (count >= 2) {
			setSpeed(diagonalSpeed);
		} else {
			setSpeed(straightSpeed);
		}
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void move() {
		if (isMoving(0)) {
			this.y = this.y - speed;
		}
		if (isMoving(1)) {
			this.x = this.x - speed;
		}
		if (isMoving(2)) {
			this.y = this.y + speed;
		}
		if (isMoving(3)) {
			this.x = this.x + speed;
		}
	}

	public void shoot() {
		bulletSystem.startFiring();
	}

	public void stopShooting() {
		bulletSystem.stopFiring();
	}

	public void setAngle() {
		angle = Math.PI / 4 * this.directionModifier;
	}

	public void setDirection() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (isFacing(i)) {
				count++;
			}
		}
		if (count >= 2) {
			if (isFacing(0) && isFacing(3)) {
				setDirectionModifier((short) 1);
			} else if (isFacing(2) && isFacing(3)) {
				setDirectionModifier((short) 3);
			} else if (isFacing(2) && isFacing(1)) {
				setDirectionModifier((short) 5);
			} else if (isFacing(0) && isFacing(1)) {
				setDirectionModifier((short) 7);
			}
		} else {
			if (isFacing(0)) {
				setDirectionModifier((short) 0);
			} else if (isFacing(2)) {
				setDirectionModifier((short) 4);
			} else if (isFacing(3)) {
				setDirectionModifier((short) 2);
			} else if (isFacing(1)) {
				setDirectionModifier((short) 6);
			}
		}
	}

	public void getHit() {
		if (!isInvincible()) {
			setInvincibility(true);
			timeOfHit = System.currentTimeMillis();
		}
	}

	public void deactivateInvincibility() {
		if (isInvincible()) {
			colorFlashCount++;
			if (colorFlashCount % 3 == 0) {
				setRGB(getBackupRGB(0), getBackupRGB(1), getBackupRGB(2));
			} else {
				setRGB((short) 0, (short) 0, (short) 0);
			}
			if (System.currentTimeMillis() - timeOfHit > 1000) {
				setRGB(getBackupRGB(0), getBackupRGB(1), getBackupRGB(2));
				setInvincibility(false);
			}
		}
	}

	public ArrayList<Bullet> getBullets() {
		return bulletSystem.getBullets();
	}

	public void setDirectionModifier(short value) {
		directionModifier = value;
	}

	public void shouldMove(int direction, boolean val) {
		moving[direction] = val;
	}

	public void shouldFace(int direction, boolean value) {
		facing[direction] = value;
	}

	public boolean isFacing(int direction) {
		return facing[direction];
	}

	public boolean isMoving(int direction) {
		return moving[direction];
	}

	public void writeInfoInto(Player player) {
		player.cloneInfoOf(this);
		player.setDirection(directionModifier);
	}

	public void setInvincibility(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public short getBackupRGB(int i) {
		return backupRGB[i];
	}
	
	public double getAngle() {
		return angle;
	}
}
