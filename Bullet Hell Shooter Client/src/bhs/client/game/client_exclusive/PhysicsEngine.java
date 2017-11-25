package bhs.client.game.client_exclusive;

import bhs.client.game.control.DataController;
import game.protocol.*;

public class PhysicsEngine {
	
	DataController dataController;
	PlayerController playerController;
	Player player;
	
	public PhysicsEngine(DataController dataController, Player player, PlayerController playerController) {
		this.dataController = dataController;
		this.playerController = playerController;
		this.player = player;
	}
	
	public void run() {
		handlePlayerEnemyCollision();
		handleBulletEnemyCollision();
	}
	
	public void handlePlayerEnemyCollision() {
		if(!playerController.isPlayerInvincible()) {
			for(GameObject e: dataController.getEnemies()) {
				if(getDistance(e, player) <= e.getSize() + player.getSize()) {
					playerController.damagePlayer();
					return;
				}
			}
		}
	}

	public void handleBulletEnemyCollision() {
		int i;
		for (Bullet b : player.getBullets()) {
			for (i = 0; i < dataController.getEnemies().size(); i++) {
				if (getDistance(b, dataController.getEnemies().get(i)) <= b.getSize()
						+ dataController.getEnemies().get(i).getSize()) {
					player.addHitEnemies(i);
					b.deactivate();
					break;
				}
			}
		}
	}

	public double getDistance(GameObject a, GameObject b) {
		float xDistance = a.getX() - b.getX();
		float yDistance = a.getY() - b.getY();
		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

}
