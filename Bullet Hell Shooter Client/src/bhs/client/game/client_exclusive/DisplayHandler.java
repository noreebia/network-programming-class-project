package bhs.client.game.client_exclusive;

import processing.core.PApplet;

import bhs.client.game.control.*;
import model.*;


public class DisplayHandler {

	PApplet world;
	short connectionID;
	DataController dataController;
	User user;
	
	int numberOfParticleSystems = 15;
	ParticleSystem particleSystems[] = new ParticleSystem[numberOfParticleSystems];
	
	long timeOfLevelChange;
	int durationOfLevelChangeDisplay = 1000;
	int aliveEnemies;
	int textOffset = 100;

	public DisplayHandler(PApplet world, short connectionID, DataController dataController, User user) {
		this.world = world;
		this.connectionID = connectionID;
		this.dataController = dataController;
		this.user = user;

		int i;
		for (i = 0; i < numberOfParticleSystems; i++) {
			particleSystems[i] = new ParticleSystem(world);
		}
	}

	public void run() {
		drawUser();
		drawPlayersAndBullets();
		drawEnemies();
		drawExplosions();
		runParticleSystems();
		displayGameStats();
		displayLevelChange();
	}
	
	public void drawExplosions() {
		int i;
		for (i = 0; i < dataController.getExplosions().size(); i++) {
			createExplosion(dataController.getExplosions().get(i).getX(), dataController.getExplosions().get(i).getY(), 
					dataController.getExplosions().get(i).getRGB(0), dataController.getExplosions().get(i).getRGB(1),
					dataController.getExplosions().get(i).getRGB(2));
			dataController.getExplosions().remove(i);
		}
	}
	
	public void drawUser() {
		world.pushMatrix();
		world.translate(user.getX(), user.getY());
		user.setAngle();
		world.rotate((float)  user.getAngle());
		setStrokeAndFillOf(user);
		// world.ellipse(0, 0, size * 2, size * 2);
		world.beginShape();
		world.vertex(0, -user.getSize()- 5);
		world.vertex(-user.getSize(), 5);
		world.vertex(0, 15);
		world.vertex(user.getSize(), 5);
		world.endShape(world.CLOSE);
		// bulletSystem.display();
		world.popMatrix();
	}

	public void runParticleSystems() {
		int i;
		for (i = 0; i < numberOfParticleSystems; i++) {
			particleSystems[i].run();
		}
	}

	public void drawPlayersAndBullets() {
		for (Player p : dataController.getPlayers()) {
			if (p.getID() != connectionID) {
				System.out.println("player's id: " + p.getID());
				System.out.println("my id: " + connectionID);
				System.out.println("drawing player...");
				drawPlayer(p);
			} else {
				System.out.println("player's id: " + p.getID());
				System.out.println("my id: " + connectionID);
				System.out.println("drawing me...");

				world.fill(255, 128, 0);
				world.ellipse(p.getX(), p.getY(), 5, 5);
			}
			// System.out.println("Num of player bullets: " + p.getBullets().size());
			/*
			for (Bullet b : p.getBullets()) {
				drawBullet(b);
			}
			*/
		}
	}

	public void drawPlayer(Player player) {
		world.pushMatrix();
		world.translate(player.getX(), player.getY());
		world.rotate(world.PI / 4 * player.getDirection());
		setStrokeAndFillOf(player);
		world.ellipse(0, 0, player.size * 2, player.size * 2);
		world.rect(-2, -player.size, 4, -9);
		world.popMatrix();
	}

	public void drawBullet(Bullet bullet) {
		setStrokeAndFillOf(bullet);
		world.ellipse(bullet.getX(), bullet.getY(), 2 * bullet.getSize(), 2 * bullet.getSize());
	}

	public void drawEnemies() {
		int count = 0;
		for (GameObject e : dataController.getEnemies()) {
			if(e.getX() > -500 || e.getY() > -500) {
				drawEnemy(e);
				count++;
			}
		}
		aliveEnemies = count;
	}

	public void drawEnemy(GameObject enemy) {
		setStrokeAndFillOf(enemy);
		world.ellipse(enemy.getX(), enemy.getY(), 2 * enemy.getSize(), 2 * enemy.getSize());
	}

	public void createExplosion(float x, float y, short r, short g, short b) {
		for (ParticleSystem p : particleSystems) {
			if (!p.isActive()) {
				p.activate(x, y, r, g, b);
				return;
			}
		}
	}
	
	public void setStrokeAndFillOf(GameObject gameObject) {
		world.stroke(gameObject.getRGB(0), gameObject.getRGB(1),gameObject.getRGB(2));
		world.fill(gameObject.getRGB(0),gameObject.getRGB(1),gameObject.getRGB(2));
	}
	
	public void displayGameStats() {
		world.fill(255);
		world.textSize(26);
		/* display level */
		float widthOfString = world.textWidth("LEVEL " + dataController.getLevel());
		world.text("LEVEL " + dataController.getLevel(), world.width/2 - widthOfString/2, 30);
		/* display number of enemies left alive */
		widthOfString = world.textWidth("ENEMIES LEFT: " + aliveEnemies);
		world.text("ENEMIES LEFT: " + aliveEnemies, world.width - (widthOfString + textOffset), 30);
	}
	
	public void displayLevelChange() {
		if(dataController.hasLevelChanged()) {		
			dataController.setLevelChanged(false);
			timeOfLevelChange = System.currentTimeMillis();
		}
		else if(System.currentTimeMillis() - timeOfLevelChange <= durationOfLevelChangeDisplay){
			world.fill(255);
			world.textSize(70);
			float widthOfString = world.textWidth("LEVEL " + dataController.getLevel());
			world.text("LEVEL "+ dataController.getLevel(), world.width/2 - widthOfString/2, world.height/2 - 35);
		}
	}
}
