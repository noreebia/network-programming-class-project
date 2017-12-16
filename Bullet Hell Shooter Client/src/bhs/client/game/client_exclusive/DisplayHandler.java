package bhs.client.game.client_exclusive;

import bhs.client.game.control.DataController;
import game.protocol.Bullet;
import game.protocol.GameObject;
import game.protocol.Player;
import processing.core.PApplet;
import processing.core.PFont;

public class DisplayHandler {

	PApplet world;
	short connectionID;
	DataController dataController;

	int numberOfParticleSystems = 20;
	ParticleSystem particleSystems[] = new ParticleSystem[numberOfParticleSystems];

	long timeOfLevelChange;
	int durationOfLevelChangeDisplay = 1000;
	int aliveEnemies;
	int alivePlayers;
	int textOffset = 30;

	Player user;
	PFont font;
	PlayerController playerController;

	boolean hasGameEnded = false;

	public DisplayHandler(PApplet world, short connectionID, DataController dataController, Player user,
			PlayerController playerController) {
		this.world = world;
		this.connectionID = connectionID;
		this.dataController = dataController;
		this.playerController = playerController;
		this.user = playerController.getPlayer();

		int i;
		for (i = 0; i < numberOfParticleSystems; i++) {
			particleSystems[i] = new ParticleSystem(world);
		}
		font = world.createFont("sans-serif", 100, true);
		world.textFont(font);
	}

	public void run() {
		drawBackground();
		drawUser();
		drawPlayersAndBullets();
		drawEnemies();
		drawExplosions();
		runParticleSystems();
		displayGameStats();
		displayLevelChange();
		if (hasGameEnded) {
			notifyEndOfGame();
		}
		displayExitButton();
	}

	public void drawBackground() {
		world.background(0);
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
		drawPlayer(user);
	}

	public void drawPlayer(Player player) {
		world.pushMatrix();
		world.translate(player.getX(), player.getY());
		for (int i = 0; i < 3; i++) {
			world.stroke(255);
			if (i + 1 > player.getHP()) {
				world.fill(0);
			} else {
				world.fill(255);
			}
			world.ellipse((i - 1) * 15, 30, 10, 10);
		}
		world.textSize(15);
		world.fill(255);
		float temp = world.textWidth("<DEAD>");
		if (!player.isAlive()) {
			world.text("<DEAD>", -temp / 2, -40);
		}
		temp = world.textWidth(player.getUsername());
		world.text(player.getUsername(), -temp / 2, -20);
		world.rotate((float) (Math.PI / 4 * player.getDirection()));
		setStrokeAndFillOf(player);
		world.beginShape();
		world.vertex(0, -player.getSize() - 5);
		world.vertex(-player.getSize(), 5);
		world.vertex(0, 15);
		world.vertex(player.getSize(), 5);
		world.endShape(world.CLOSE);
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
				drawPlayer(p);
			} else {
				world.fill(255, 128, 0);
				world.ellipse(p.getX(), p.getY(), 5, 5);
			}
			for (Bullet b : p.getBullets()) {
				drawBullet(b);
			}
		}
	}

	public void drawBullet(Bullet bullet) {
		setStrokeAndFillOf(bullet);
		world.ellipse(bullet.getX(), bullet.getY(), 2 * bullet.getSize(), 2 * bullet.getSize());
	}

	public void drawEnemies() {
		int count = 0;
		for (GameObject e : dataController.getEnemies()) {
			if (e.getX() > -500 || e.getY() > -500) {
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
		world.stroke(gameObject.getRGB(0), gameObject.getRGB(1), gameObject.getRGB(2));
		world.fill(gameObject.getRGB(0), gameObject.getRGB(1), gameObject.getRGB(2));
	}

	public void displayGameStats() {
		world.fill(255);
		world.textSize(26);
		/* display level */
		float temp = world.textWidth("LEVEL " + dataController.getLevel());
		world.text("LEVEL " + dataController.getLevel(), world.width / 2 - temp / 2, 30);
		/* display number of enemies left alive */
		temp = world.textWidth("ENEMIES LEFT: " + aliveEnemies);
		world.text("ENEMIES LEFT: " + aliveEnemies, world.width - (temp + textOffset), textOffset);
		alivePlayers = dataController.getAlivePlayers();
		temp = world.textWidth("PLAYERS ALIVE: " + alivePlayers);
		world.text("PLAYERS ALIVE: " + alivePlayers, textOffset, textOffset);
		if (alivePlayers <= 0) {
			if (!user.isAlive()) {
				hasGameEnded = true;
			}
		}
	}

	public void displayLevelChange() {
		if (dataController.hasLevelChanged()) {
			dataController.setLevelChanged(false);
			timeOfLevelChange = System.currentTimeMillis();
			playerController.revivePlayer();
		} else if (System.currentTimeMillis() - timeOfLevelChange <= durationOfLevelChangeDisplay) {
			world.fill(255);
			world.textSize(70);
			float temp = world.textWidth("LEVEL " + dataController.getLevel());
			world.text("LEVEL " + dataController.getLevel(), world.width / 2 - temp / 2, world.height / 2 - 35);
		}
	}

	public void notifyEndOfGame() {
		world.fill(0, 0, 0, 128);
		world.rect(0, 0, world.width, world.height);

		world.fill(255);
		world.textSize(70);
		float temp = world.textWidth("END OF GAME");
		world.text("END OF GAME", world.width / 2 - temp / 2, world.height / 2 - 35);
	}

	public void displayExitButton() {
		world.textSize(26);
		world.stroke(0, 128, 255);
		if (world.mouseX >= world.width - 120 && world.mouseY >= world.height - 70 && world.mouseX <= world.width - 20
				&& world.mouseY <= world.height - 20) {
			world.fill(153, 204, 255);
		} else {
			world.fill(0);
		}
		world.rect(world.width - 120, world.height - 70, 100, 50);

		world.fill(0, 128, 255);
		float temp = world.textWidth("EXIT");
		world.text("EXIT", world.width - 120 + (100 - temp) / 2, world.height - 35);
	}
}
