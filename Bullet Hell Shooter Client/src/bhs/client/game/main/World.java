package bhs.client.game.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import bhs.client.game.client_exclusive.*;
import bhs.client.game.control.DataController;
import model.*;
import processing.core.PApplet;

public class World extends PApplet {
	InetAddress serverAddress;
	int serverPort;

	DatagramSocket socket;
	DatagramPacket packet;

	byte buf[] = new byte[8192];

	DataController dataController = new DataController();

	short connectionID;

	ExecutorService executor = Executors.newCachedThreadPool();
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

	DisplayHandler displayHandler;
	PhysicsEngine physicsEngine;
	
	Player player = new Player();
	PlayerController playerController = new PlayerController(this, player);

	public World(int serverPort, int playerID) {
		System.out.println("initializing world");
		try {
			serverAddress = InetAddress.getByName("localhost");
			// serverAddress = InetAddress.getByName("192.168.0.12");
			this.serverPort = serverPort;
			socket = new DatagramSocket();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		packet = new DatagramPacket(buf, buf.length, serverAddress, serverPort);

		/*
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Sent packet to server");

			packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			connectionID = byteArrayToShort(packet.getData());

			player.setID((short) connectionID);
			System.out.println("My ID: " + connectionID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		//Random rand = new Random();
		//short randomShort = (short)rand.nextInt(100);
		//player.setID((short)1);
		try {
			player.setID((short)playerID);
			connectionID = (short) playerID;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void settings() {
		size(1200, 800);

		/* can only do this after calling size() */
		playerController.initializePlayer();

		/* starting executor threads */
		executor.execute(new InputHandlingThread(socket, dataController, connectionID));
		ses.scheduleAtFixedRate(new OutputHandlingThread(socket, serverAddress, serverPort, player), 0, 8,
				TimeUnit.MILLISECONDS);

		displayHandler = new DisplayHandler(this, connectionID, dataController, player);
		physicsEngine = new PhysicsEngine(dataController, player, playerController);
	}


	public void setup() {
		strokeWeight((float) 1.5);
		stroke(255);
	}

	public void draw() {
		background(0);
		
		playerController.run();

		displayHandler.run();
		physicsEngine.run();
	}

	public short byteArrayToShort(byte[] b) {
		ByteBuffer bb = ByteBuffer.wrap(b);
		return bb.getShort();
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
			playerController.shoot();
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
}
