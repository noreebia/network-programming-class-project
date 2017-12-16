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

	public ClientHandler(Client client, CopyOnWriteArrayList<Client> clientList,
			CopyOnWriteArrayList<ClientHandler> clientHandlingThreads, CopyOnWriteArrayList<Room> rooms,
			AtomicInteger uniqueRoomID) {
		this.client = client;
		this.clientList = clientList;
		this.clientHandlingThreads = clientHandlingThreads;
		this.rooms = rooms;
		this.uniqueRoomID = uniqueRoomID;

		try {
			oos = client.getOutputStream();
			ois = client.getInputStream();
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
		while (shouldRun()) {
			try {
				incomingMessage = (Message) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				terminate();
				break;
			} catch (SocketException e) {
				terminate();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				terminate();
				break;
			}
			if (incomingMessage == null) {
				terminate();
				break;
			}
			request = incomingMessage.getContents();
			System.out.println("Recevied message from client  " + client.getNickname());
			System.out.println("Client request: " + request);
			switch (request) {
			case "chat":
				messageData = (String) incomingMessage.getData();
				String modifiedMessage = client.getNickname() + ": " + messageData + "\n";
				System.out.println(modifiedMessage);
				outgoingMessage = new Message("chatboxUpdate", modifiedMessage);
				sendToAllClients(outgoingMessage);
				break;
			case "create game":
				int newRoomID = createRoom();
				handleRoomJoinRequest(newRoomID);
				refreshEveryClient();
				break;
			case "join game":
				int roomID = (int) incomingMessage.getData();
				handleRoomJoinRequest(roomID);
				break;
			case "refresh room list":
				refreshClient();
				break;
			case "exit":
				terminate();
				break;
			default:
			}
		}
		System.out.println("Client has disconnected. Terminating appointed client handler.");
		disconnectClient();
		removeSelfFromArray();
		return;
	}

	public int createRoom() {
		int newRoomID = uniqueRoomID.getAndIncrement();
		Room newRoom = new Room(newRoomID);
		rooms.add(newRoom);
		return newRoomID;
	}

	public void handleRoomJoinRequest(int roomID) {
		for (Room r : rooms) {
			if (r.getID() == roomID) {
				if (r.getState().equals("Dead") || r.getState().equals("Finished")) {
					refreshClient();
				} else {
					if (r.getPlayerCount() <= 3) {
						int[] roomInfo = { r.getPort(), r.getUniquePlayerID() };
						Message message = new Message("join game response", roomInfo);
						sendToClient(message);
					}
				}
				return;
			}
		}
	}

	public void refreshClient() {
		ArrayList<String> listOfRooms = new ArrayList<String>();
		for (Room r : rooms) {
			if (r.getState().equals("Dead")) {
				rooms.remove(r);
			} else {
				listOfRooms.add("Room " + r.getID() + "     Players:" + r.getPlayerCount() + "/4" + "     Stage: "
						+ r.getCurrentStage() + "    " + r.getState());
			}
		}
		Message message = new Message("refresh room list response", listOfRooms);
		sendToClient(message);
	}

	public void refreshEveryClient() {
		ArrayList<String> listOfRooms = new ArrayList<String>();
		for (Room r : rooms) {
			if (r.getState().equals("Dead")) {
				rooms.remove(r);
			} else {
				listOfRooms.add("Room " + r.getID() + "     Players:" + r.getPlayerCount() + "/4" + "     Stage: "
						+ r.getCurrentStage() + "    " + r.getState());
			}
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
		for (ClientHandler ch : clientHandlingThreads) {
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
