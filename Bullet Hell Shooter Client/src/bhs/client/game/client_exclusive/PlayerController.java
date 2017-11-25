package bhs.client.game.client_exclusive;

import java.util.ArrayList;

import model.Bullet;
import model.Player;
import processing.core.PApplet;

public class PlayerController {
	Player player;

	BulletSystem bulletSystem;
	
	PApplet world;

	public short directionModifier;

	double angle;

	boolean playerInvincible = false;
	boolean[] moving = new boolean[4];
	boolean[] facing = new boolean[4];
	
	float speed;
	float straightSpeed = 5;
	float diagonalSpeed = (float) (straightSpeed / Math.sqrt(2));

	long timeOfHit;
	int colorFlashCount;
	short[] backupRGB;

	public PlayerController(PApplet world, Player player) {
		this.world = world;
		this.player = player;
	}
	
	public void initializePlayer() {
		player.setX(world.width/2);
		player.setY(world.height/2);
		player.setRGB((short)0, (short)255, (short)255);
		
		backupRGB = player.getRGB().clone();
		bulletSystem = new BulletSystem(world, player, backupRGB);		
		player.setBullets(bulletSystem.getBullets());
		player.setHP((short) 3);
	}
	
	public void run() {
		setSpeed();
		setDirection();
		movePlayer();
		bulletSystem.run();
		deactivateInvincibility();
	}
	

	public void setSpeed() {
		int count = 0;
		for (int i = 0; i < moving.length; i++) {
			if (isMoving(i)) {
				count++;
			}
		}
		if (count >= 2) {
			speed = diagonalSpeed;
		} else {
			speed = straightSpeed;
		}
	}

	public void movePlayer() {
		if (isMoving(0)) {
			player.move(0, -speed);
		}
		if (isMoving(1)) {
			player.move(-speed, 0);
		}
		if (isMoving(2)) {
			player.move(0, speed);
		}
		if (isMoving(3)) {
			player.move(speed, 0);
		}
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
				player.setDirection((short) 1);
			} else if (isFacing(2) && isFacing(3)) {
				player.setDirection((short) 3);
			} else if (isFacing(2) && isFacing(1)) {
				player.setDirection((short) 5);
			} else if (isFacing(0) && isFacing(1)) {
				player.setDirection((short) 7);
			}
		} else {
			if (isFacing(0)) {
				player.setDirection((short) 0);
			} else if (isFacing(2)) {
				player.setDirection((short) 4);
			} else if (isFacing(3)) {
				player.setDirection((short) 2);
			} else if (isFacing(1)) {
				player.setDirection((short) 6);
			}
		}
	}

	public void damagePlayer() {
		if (!isPlayerInvincible()) {
			player.setHP((short) (player.getHP() - 1));
			setPlayerInvicibility(true);
			timeOfHit = System.currentTimeMillis();
		}
	}

	public void deactivateInvincibility() {
		if (isPlayerInvincible()) {
			colorFlashCount++;
			if (colorFlashCount % 3 == 0) {
				player.setRGB(getBackupRGB(0), getBackupRGB(1), getBackupRGB(2));
			} else {
				player.setRGB((short)0,(short)0,(short)0);
			}
			if (System.currentTimeMillis() - timeOfHit > 1000) {
				player.setRGB(getBackupRGB(0), getBackupRGB(1), getBackupRGB(2));
				setPlayerInvicibility(false);
			}
		}
	}

	public void shoot() {
		bulletSystem.startFiring();
	}

	public void stopShooting() {
		bulletSystem.stopFiring();
	}

	public boolean isMoving(int i) {
		return moving[i];
	}
	
	public void shouldMove(int i, boolean value) {
		moving[i] = value;
	}

	public boolean isFacing(int i) {
		return facing[i];
	}
	
	public void shouldFace(int i, boolean value) {
		facing[i] = value;
	}

	public void setPlayerInvicibility(boolean invincible) {
		this.playerInvincible = invincible;
	}

	public boolean isPlayerInvincible() {
		return playerInvincible;
	}
	
	public short getBackupRGB(int i) {
		return backupRGB[i];
	}
	
	public ArrayList<Bullet> getBullets(){
		return bulletSystem.getBullets();
	}
}
