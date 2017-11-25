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

	public ClientHandler(Client client, CopyOnWriteArrayList<Client> clientList, CopyOnWriteArrayList<ClientHandler> clientHandlingThreads, CopyOnWriteArrayList<Room> rooms, AtomicInteger uniqueRoomID) {
		this.client = client;
		this.clientList = clientList;
		this.clientHandlingThreads = clientHandlingThreads;
		this.rooms = rooms;
		this.uniqueRoomID = uniqueRoomID;
		try {
			// input = new BufferedReader(new
			// InputStreamReader(client.getSocket().getInputStream()));
			// output = new PrintWriter(client.getSocket().getOutputStream(), true);

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
		while (true) {
			System.out.println("listening for client input...");
			// message = input.readLine();
			try {
				incomingMessage = (Message) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				disconnectClient();
				System.exit(1);
				break;
			} catch (SocketException e) {
				disconnectClient();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			if (incomingMessage == null) {
				disconnectClient();
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
				//sendToAllClients(client.getNickname() + ":" + chatMessage+"\n");
				break;
			case "create game":
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
				ArrayList<String> listOfRooms= new ArrayList<String>();
				for(Room r:rooms) {
					listOfRooms.add("Room " + r.getID());
				}
				outgoingMessage = new Message("room list update", listOfRooms);
				sendToAllClients(outgoingMessage);
				break;
			case "join game":
				int roomID = (int) incomingMessage.getData();
				for(Room r:rooms) {
					if(r.getID() == roomID) {
						int[] roomInfo = {r.getPort(), r.getUniquePlayerID()};
						outgoingMessage = new Message("join game response", roomInfo);
						sendMessage(outgoingMessage);
						break;
					}
				}
				break;
			}
		}
		System.out.println("client has disconnected. terminating");
		return;
	}
	
	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToAllClients(Message message) {
		for(ClientHandler ch: clientHandlingThreads) {
			ch.sendMessage(message);
		}
	}
	/*
	public void sendMessage(String chatMessage) {
		Message message = new Message("chatboxUpdate", chatMessage);
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToAllClients(String chatMessage) {
		try {
			for (ClientHandler ch : clientHandlingThreads) {
				ch.sendMessage(chatMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	/*
	 * public void sendToAllClients(String chatMessage) { Socket tempSocket;
	 * ObjectOutputStream tempOos = null; Message message = new
	 * Message("chatboxUpdate", chatMessage); for(Client c: clientList) { tempSocket
	 * = c.getSocket(); try { tempOos = new
	 * ObjectOutputStream(tempSocket.getOutputStream()); } catch (IOException e) {
	 * e.printStackTrace(); } try { tempOos.writeObject(message); } catch
	 * (IOException e) { e.printStackTrace(); } } }
	 */

	public void disconnectClient() {
		clientList.remove(client);
	}
	
	public void terminate() {
		clientHandlingThreads.remove(this);
	}
}
