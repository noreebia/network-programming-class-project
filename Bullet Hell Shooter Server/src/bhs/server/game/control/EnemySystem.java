package bhs.server.game.control;

import java.util.ArrayList;

import java.util.Random;

import bhs.server.game.main.Room;
import game.protocol.Enemy;
import game.protocol.GameObject;
import game.protocol.Player;


public class EnemySystem {
	DataController dataController;

	ArrayList<Enemy> originals = new ArrayList<Enemy>();
	ArrayList<GameObject> shadows;

	float speed = 1;
	int offset = 150;
	
	int screenWidth = 1200;
	int screenHeight = 800;
	
	int enemySpawnLocationWidth = 150;
	
	boolean everyEnemyDead;

	Random rand = new Random();
	
	public EnemySystem(DataController dataController) {
		this.dataController = dataController;
	}
	
	public void increaseLevel() {
		dataController.increaseLevel();
		resetEnemies(dataController.getLevel() * (10 + dataController.getPlayers().size()));
	}

	public void resetEnemies(int numOfEnemies) {
		originals = new ArrayList<Enemy>();
		dataController.createNewEnemyArrayList();
		shadows = dataController.getEnemies();

		int i;
		float spawnPointX = 0;
		float spawnPointY = 0;
		for (i = 0; i < numOfEnemies; i++) {
			int spawnDirection = rand.nextInt(4) + 1;
			switch(spawnDirection) {
			case 1:
				spawnPointX = rand.nextInt(screenWidth) + 1;
				spawnPointY = rand.nextInt(enemySpawnLocationWidth) + 1;
				break;
			case 2:
				spawnPointX = rand.nextInt(screenWidth - (screenWidth - enemySpawnLocationWidth) + 1) + (screenWidth - enemySpawnLocationWidth);
				spawnPointY = rand.nextInt(screenHeight) + 1;
				break;
			case 3:
				spawnPointX = rand.nextInt(screenWidth) + 1;
				spawnPointY = rand.nextInt(screenHeight - (screenHeight - enemySpawnLocationWidth) +1) + (screenHeight - enemySpawnLocationWidth);
				break;
			case 4:
				spawnPointX = rand.nextInt(enemySpawnLocationWidth) + 1;
				spawnPointY = rand.nextInt(screenHeight) + 1;
				break;
			}
			originals.add(new Enemy(spawnPointX, spawnPointY));

			shadows.add(new GameObject(spawnPointX, spawnPointY));
			shadows.get(i).setRGB((short) (rand.nextInt(255) + 1), (short) (rand.nextInt(255) + 1), (short) (rand.nextInt(255) + 1));
		}
	}

	public void run() {
		if (dataController.getPlayers().size() > 0) {
			for (Enemy e : originals) {
				if (e.isActive()) {
					if (e.getVelocityX() != -1 && e.getVelocityY() != -1) {
						if (e.isOutOfMap(screenWidth, screenHeight)) {
							setRandomPointAsTarget(e);
						} 
					} else {
						setRandomPointAsTarget(e);
					}
					e.moveToDestination();
				}
			}
			updateShadows();
			if(isEveryEnemyDead()) {
				increaseLevel();
			}
		}
	}
	
	public void setRandomPointAsTarget(Enemy e) {
		int randomX = rand.nextInt(screenWidth) + 1;
		int randomY = rand.nextInt(screenHeight) + 1;
		
		e.setDestination(randomX, randomY);
	}

	public void setRandomPlayerAsTarget(Enemy e) {
		int randomNum = rand.nextInt(dataController.getPlayers().size());
		e.setDestination(getXLocationOfPlayer(randomNum), getYLocationOfPlayer(randomNum));
	}

	public void updateShadows() {
		everyEnemyDead = true;
		if (originals.size() == shadows.size()) {
			int i;
			for (i = 0; i < originals.size(); i++) {
				if(originals.get(i).isActive()) {
					shadows.get(i).setX(originals.get(i).getX());
					shadows.get(i).setY(originals.get(i).getY());
					if(isEveryEnemyDead()) {
						everyEnemyDead = false;
					}
				}				
			}
		}
	}
	
	public void changeShadowColor(int i) {
		shadows.get(i).setRGB((short)(rand.nextInt(255) + 1), (short)(rand.nextInt(255) + 1), (short)(rand.nextInt(255) + 1));
	}

	public void moveEnemiesTowardsDestination() {
		for (Enemy e : originals) {
			if (e.getVelocityX() != -1 && e.getVelocityY() != -1) {
				e.moveToDestination();
			}
		}
	}

	public float getXLocationOfPlayer(int i) {
		return dataController.getPlayers().get(i).getX();
	}

	public float getYLocationOfPlayer(int i) {
		return dataController.getPlayers().get(i).getY();
	}

	public void respawnEnemy(int i) {
		this.originals.get(i).setX(-offset);
	}

	public void respawnEnemy(GameObject enemy) {
		enemy.setX(-offset);
	}

	public double getDistance(GameObject a, GameObject b) {
		float xDistance = a.getX() - b.getX();
		float yDistance = a.getY() - b.getY();

		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public ArrayList<GameObject> getShadows() {
		return shadows;
	}

	public ArrayList<Enemy> getOriginals() {
		return originals;
	}
	
	public boolean isEveryEnemyDead() {
		return everyEnemyDead;
	}
	
	public boolean isEveryPlayerDead() {
		for(Player p: dataController.getPlayers()) {
			if(p.isAlive()) {
				return false;
			}
		}
		return true;
	}
}
