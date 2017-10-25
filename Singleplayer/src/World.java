import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.Data;
import model.Player;
import model.User;
import processing.core.PApplet;

public class World extends PApplet {

	Data data = new Data();

	User user = new User(this);
	
	public World() {
		data.addPlayer(new Player());
		data.addPlayer(new Player());
		data.addPlayer(new Player());
		data.addPlayer(new Player());
	}

	public void settings() {
		size(1200, 800);
	}

	public void setup() {
		fill(0);
	}

	public void draw() {
		background(255);
		//displayGameObjects();
		user.run();
	}

	public void displayGameObjects() {
		for (Player p : data.getPlayers()) {
			data.randomize();
			ellipse(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
	}

	public void keyPressed() {
		if (keyCode == UP) {
			user.shouldFace(0, true);
		}

		if (keyCode == LEFT) {
			user.shouldFace(1, true);
		}

		if (keyCode == DOWN) {
			user.shouldFace(2, true);
		}

		if (keyCode == RIGHT) {
			user.shouldFace(3, true);
		}
		if (key == 'w') {
			user.shouldMove(0, true);
		}
		if (key == 'a') {
			user.shouldMove(1, true);
		}
		if (key == 's') {
			user.shouldMove(2, true);
		}
		if (key == 'd') {
			user.shouldMove(3, true);
		}
		if (key == ' ') {
			//user.shoot();
		}
	}

	public void keyReleased() {
		if (keyCode == UP) {
			user.shouldFace(0, false);
		}

		if (keyCode == LEFT) {
			user.shouldFace(1, false);
		}

		if (keyCode == DOWN) {
			user.shouldFace(2, false);
		}

		if (keyCode == RIGHT) {
			user.shouldFace(3, false);
		}
		if (key == 'w') {
			user.shouldMove(0, false);
		}
		if (key == 'a') {
			user.shouldMove(1, false);
		}
		if (key == 's') {
			user.shouldMove(2, false);
		}
		if (key == 'd') {
			user.shouldMove(3, false);
		}
		if (key == ' ') {
			//user.stopShooting();
		}
	}
}
