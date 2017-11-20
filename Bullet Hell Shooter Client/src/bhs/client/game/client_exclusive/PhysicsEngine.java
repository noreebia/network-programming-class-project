package bhs.client.game.client_exclusive;

import bhs.client.game.control.*;
import model.*;

public class PhysicsEngine {
	
	DataController dataController;
	User user;
	Player player;
	
	public PhysicsEngine(DataController dataController, User user, Player player) {
		this.dataController = dataController;
		this.user = user;
		this.player = player;
	}
	
	public void run() {
		handlePlayerEnemyCollision();
		handleBulletEnemyCollision();
	}
	
	public void handlePlayerEnemyCollision() {
		if(!user.isInvincible()) {
			for(GameObject e: dataController.getEnemies()) {
				if(getDistance(e, user) <= e.getSize() + user.getSize()) {
					user.getHit();
					return;
				}
			}
		}
	}

	public void handleBulletEnemyCollision() {
		int i;
		for (Bullet b : user.getBullets()) {
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
