package bhs.client.game.client_exclusive;

import java.util.ArrayList;

import game.protocol.Bullet;
import game.protocol.Player;
import processing.core.PApplet;

public class PlayerController {
	
	Player player;
	BulletSystem bulletSystem;
	
	String username;
	
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
	
	short playerID;

	public PlayerController(PApplet world, Player player, String username, short playerID, int[] playerColor) {
		this.world = world;
		this.player = player;
		this.username = username;
		this.playerID = playerID;
		player.setRGB((short)playerColor[0], (short)playerColor[1], (short)playerColor[2]);
	}
	
	public void initializePlayer() {
		player.setUsername(username);
		player.setX(world.width/2);
		player.setY(world.height/2);
		
		backupRGB = player.getRGB().clone();
		bulletSystem = new BulletSystem(world, player, backupRGB);		
		player.setHP((short) 3);
		player.setAlive(true);
		player.setHit(false);
		player.setID(playerID);
	}
	
	public void run() {
		if(player.isAlive()) {
			setSpeed();
			setDirection();
			movePlayer();
			deactivateInvincibility();
		}
		bulletSystem.run();
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
		float xSpeed = 0;
		float ySpeed = 0;
		if (isMoving(0)) {
			ySpeed -= speed;
		}
		if (isMoving(1)) {
			xSpeed -= speed;
		}
		if (isMoving(2)) {
			ySpeed += speed;
		}
		if (isMoving(3)) {
			xSpeed += speed;
		}
		if(player.getX() + xSpeed > world.width || player.getX() + xSpeed < 0) {
			xSpeed = 0;
		}
		if(player.getY() + ySpeed > world.height || player.getY() + ySpeed < 0) {
			ySpeed = 0;
		}
		player.move(xSpeed, ySpeed);
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
		if(player.isAlive()) {
			if (!isPlayerInvincible()) {
				player.setHit(true);
				player.setHP((short) (player.getHP() - 1));
				if(player.getHP() <= 0) {
					player.setAlive(false);
					player.setRGB((short)211, (short)211, (short)211);
					stopShooting();
					return;
				}else {
					setPlayerInvicibility(true);
					timeOfHit = System.currentTimeMillis();
				}
			}
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
	
	public void revivePlayer() {
		player.setAlive(true);
		player.setHP((short)3);
		player.setRGB(backupRGB[0], backupRGB[1], backupRGB[2]);
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
	
	public boolean isPlayerAlive() {
		return player.isAlive();
	}
	
	public Player getPlayer() {
		return player;
	}
}
