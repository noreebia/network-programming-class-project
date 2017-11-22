package bhs.server.game.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import bhs.server.game.control.DataController;
import bhs.server.game.control.EnemySystem;
import model.Client;

public class Room {
	DatagramSocket socket;
		
	DataController dataController = new DataController();
	EnemySystem enemySystem = new EnemySystem(dataController);
	
	ArrayList<Client> clients = new ArrayList<Client>();
	
	short connectionCount=0;
	
	int id;
	int port;
	
	ExecutorService executor = Executors.newCachedThreadPool();
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
	
	public Room() {
		enemySystem.resetEnemies(10);

		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		System.out.println(socket.getLocalPort());
		
		executor.execute(new InputHandlingThread(socket, dataController, enemySystem, clients));
		ses.scheduleAtFixedRate(new OutputHandlingThread(socket, dataController, clients, enemySystem), 0, 8, TimeUnit.MILLISECONDS);
	}
	
	public Room(int port) {
		enemySystem.resetEnemies(10);

		try {
			this.port = port;
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		System.out.println(socket.getLocalPort());
		
		executor.execute(new InputHandlingThread(socket, dataController, enemySystem, clients));
		ses.scheduleAtFixedRate(new OutputHandlingThread(socket, dataController, clients, enemySystem), 0, 8, TimeUnit.MILLISECONDS);
	}
	
	public short getConnectionCount() {
		return connectionCount;
	}
	
	/*
	public void addClient(short id, InetAddress clientAddress, int clientPort) {
		clients.add(new Client(id, clientAddress, clientPort));
	}
	*/
	
	public int getPort() {
		return socket.getLocalPort();
	}
	
	public int getID() {
		return id;
	}
}
