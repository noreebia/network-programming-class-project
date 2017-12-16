package bhs.client.game.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.Frame;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import bhs.client.game.client_exclusive.*;
import bhs.client.game.control.DataController;
import game.protocol.*;
import processing.awt.PSurfaceAWT;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;

public class World extends PApplet {
	InetAddress serverAddress;
	int serverPort;

	DatagramSocket socket;

	Player player;

	DataController dataController;
	PlayerController playerController;
	DisplayHandler displayHandler;
	PhysicsEngine physicsEngine;

	short playerID;

	ExecutorService executor;
	ScheduledExecutorService ses;

	InputHandlingThread inputHandlingThread;
	OutputHandlingThread outputHandlingThread;
	
	JFrame lobby;
	Frame frame;

	String username;

	boolean shouldRun = false;

	public World(String username, JFrame lobby) {
		this.username = username;
		this.lobby = lobby;
	}

	public void settings() {
		size(1200, 800);
	}

	public void setup() {
		strokeWeight((float) 1.5);
		stroke(255);

		frame = ((SmoothCanvas) ((PSurfaceAWT) surface).getNative()).getFrame();
		frame.setVisible(false);
		noLoop();
	}

	public void setServerInfo(String serverIP, int serverPort, int playerID) {
		System.out.println("initializing world");
		try {
			serverAddress = InetAddress.getByName(serverIP);
			this.serverPort = serverPort;
			socket = new DatagramSocket();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.playerID = (short) playerID;
		System.out.println(playerID);
	}

	public void reset(int[] playerColor) {
		try {
			frame.setVisible(true);
			lobby.setVisible(false);

			executor = Executors.newCachedThreadPool();
			ses = Executors.newScheduledThreadPool(2);

			player = new Player();
			playerController = new PlayerController(this, player, username, playerID, playerColor);
			playerController.initializePlayer();

			dataController = new DataController();
			
			inputHandlingThread = new InputHandlingThread(socket, dataController, playerID);
			outputHandlingThread = new OutputHandlingThread(socket, serverAddress, serverPort, player);

			displayHandler = new DisplayHandler(this, playerID, dataController, player, playerController);
			physicsEngine = new PhysicsEngine(dataController, player, playerController);

			executor.execute(inputHandlingThread);
			ses.scheduleAtFixedRate(outputHandlingThread,0,8,TimeUnit.MILLISECONDS);

			shouldRun = true;
			loop();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void draw() {
		if (shouldRun) {
			playerController.run();
			displayHandler.run();
			physicsEngine.run();
		}
	}

	public void keyPressed() {
		if (keyCode == UP) {
			playerController.shouldFace(0, true);
		}
		if (keyCode == LEFT) {
			playerController.shouldFace(1, true);
		}
		if (keyCode == DOWN) {
			playerController.shouldFace(2, true);
		}
		if (keyCode == RIGHT) {
			playerController.shouldFace(3, true);
		}
		if (key == 'w') {
			playerController.shouldMove(0, true);
		}
		if (key == 'a') {
			playerController.shouldMove(1, true);
		}
		if (key == 's') {
			playerController.shouldMove(2, true);
		}
		if (key == 'd') {
			playerController.shouldMove(3, true);
		}
		if (key == ' ') {
			if (playerController.isPlayerAlive()) {
				playerController.shoot();
			}
		}
	}

	public void keyReleased() {
		if (keyCode == UP) {
			playerController.shouldFace(0, false);
		}
		if (keyCode == LEFT) {
			playerController.shouldFace(1, false);
		}
		if (keyCode == DOWN) {
			playerController.shouldFace(2, false);
		}
		if (keyCode == RIGHT) {
			playerController.shouldFace(3, false);
		}
		if (key == 'w') {
			playerController.shouldMove(0, false);
		}
		if (key == 'a') {
			playerController.shouldMove(1, false);
		}
		if (key == 's') {
			playerController.shouldMove(2, false);
		}
		if (key == 'd') {
			playerController.shouldMove(3, false);
		}
		if (key == ' ') {
			playerController.stopShooting();
		}
	}
	
	public void mousePressed() {
		if(mouseX >= width-120 && mouseY >= height - 70 && mouseX <= width -20 && mouseY <= height-20) {
			pauseAndHide();
		}
	}
	
	public void pauseAndHide() {
		shouldRun = false;
		noLoop();
		background(0);
		inputHandlingThread.terminate();
		executor.shutdown();
		ses.shutdown();
		try {
			if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)
					|| !ses.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executor.shutdownNow();
				ses.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		socket.close();
		frame.setVisible(false);
		lobby.setVisible(true);
	}
	
	public void destroy() {
		shouldRun = false;
		noLoop();
		background(0);
		if(inputHandlingThread != null && executor != null && ses != null) {
			inputHandlingThread.terminate();
			executor.shutdown();
			ses.shutdown();
			try {
				if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)
						|| !ses.awaitTermination(800, TimeUnit.MILLISECONDS)) {
					executor.shutdownNow();
					ses.shutdownNow();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(inputHandlingThread != null) {
			inputHandlingThread.terminate();
		}
		if(executor != null) {
			executor.shutdown();
			try {
				if(!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
					executor.shutdownNow();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(ses != null) {
			ses.shutdown();
			try {
				if(!ses.awaitTermination(800, TimeUnit.MILLISECONDS)) {
					ses.shutdownNow();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(socket != null) {
			socket.close();
		}
		frame.dispose();
		frame.setVisible(false);
	}
}
