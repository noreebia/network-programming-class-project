package bhs.server.main;

import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import bhs.server.game.main.Room;
import protocol.Message;

public class ClientHandler extends Thread {
	Client client;
	CopyOnWriteArrayList<Client> clientList;
	CopyOnWriteArrayList<ClientHandler> clientHandlingThreads;
	CopyOnWriteArrayList<Room> rooms;
	AtomicInteger uniqueRoomID;
	BufferedReader input;
	PrintWriter output;

	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	boolean run = true;

	public ClientHandler(Client client, CopyOnWriteArrayList<Client> clientList, CopyOnWriteArrayList<ClientHandler> clientHandlingThreads, CopyOnWriteArrayList<Room> rooms, AtomicInteger uniqueRoomID) {
		this.client = client;
		this.clientList = clientList;
		this.clientHandlingThreads = clientHandlingThreads;
		this.rooms = rooms;
		this.uniqueRoomID = uniqueRoomID;
		try {
			oos = new ObjectOutputStream(client.getSocket().getOutputStream());
			ois = new ObjectInputStream(client.getSocket().getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("A client handler has just been created");
	}

	public void run() {
		Message incomingMessage = null;
		Message outgoingMessage = null;
		String request;
		String messageData;
		ArrayList<String> listOfRooms;
		while (shouldRun()) {
			System.out.println("listening for client input...");
			try {
				incomingMessage = (Message) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				terminate();
				System.exit(1);
				break;
			} catch (SocketException e) {
				terminate();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			if (incomingMessage == null) {
				terminate();
				break;
			}
			request = incomingMessage.getContents();
			System.out.println("From client: " + client.getNickname());
			System.out.println("Message contents: " + request);
			switch (request) {
			case "chat":
				messageData = (String) incomingMessage.getData();
				String modifiedMessage = client.getNickname() + ": " + messageData + "\n";
				System.out.println(modifiedMessage);
				outgoingMessage = new Message("chatboxUpdate", modifiedMessage);
				sendToAllClients(outgoingMessage);
				break;
			case "create game":
				/*
				int newRoomID = uniqueRoomID.getAndIncrement();
				Room newRoom = new Room(newRoomID);
				int newRoomPort = newRoom.getPort();
				rooms.add(newRoom);
				outgoingMessage = new Message("create game response", newRoomPort);
				try {
					oos.writeObject(outgoingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				refreshEveryClient();
				*/
				int newRoomID = createRoom();
				sendRoomInfo(newRoomID);
				break;
			case "join game":
				int roomID = (int) incomingMessage.getData();
				sendRoomInfo(roomID);
				/*
				for(Room r:rooms) {
					if(r.getID() == roomID) {
						int[] roomInfo = {r.getPort(), r.getUniquePlayerID()};
						outgoingMessage = new Message("join game response", roomInfo);
						sendToClient(outgoingMessage);
						break;
					}
				}
				*/
				break;
			case "refresh room list":
				refreshEveryClient();
				break;
			}
		}
		System.out.println("client has disconnected. terminating");
		disconnectClient();
		removeSelfFromArray();
		return;
	}
	
	public int createRoom() {
		int newRoomID = uniqueRoomID.getAndIncrement();
		Room newRoom = new Room(newRoomID);
		int newRoomPort = newRoom.getPort();
		rooms.add(newRoom);
		return newRoomID;
	}
	
	public void sendRoomInfo(int roomID) {
		Message message;
		for(Room r:rooms) {
			if(r.getID() == roomID) {
				int[] roomInfo = {r.getPort(), r.getUniquePlayerID()};
				message = new Message("join game response", roomInfo);
				sendToClient(message);
				break;
			}
		}
	}
	
	public void refreshEveryClient() {
		ArrayList<String> listOfRooms= new ArrayList<String>();
		for(Room r:rooms) {
			listOfRooms.add("Room " + r.getID());
		}
		Message message = new Message("refresh room list response", listOfRooms);
		sendToAllClients(message);
	}
	
	public void sendToClient(Message message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToAllClients(Message message) {
		for(ClientHandler ch: clientHandlingThreads) {
			ch.sendToClient(message);
		}
	}

	public void disconnectClient() {
		clientList.remove(client);
	}
	
	public boolean shouldRun() {
		return run;
	}
	
	public void removeSelfFromArray() {
		clientHandlingThreads.remove(this);
	}
	
	public void terminate() {
		this.run = false;
	}
}
