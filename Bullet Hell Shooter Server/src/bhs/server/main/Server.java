package bhs.server.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import bhs.server.game.main.Room;
import protocol.Message;

public class Server {
	CopyOnWriteArrayList<Client> clients = new CopyOnWriteArrayList<Client>();
	CopyOnWriteArrayList<Room> rooms = new CopyOnWriteArrayList<Room>();

	CopyOnWriteArrayList<ClientHandler> clientHandlingThreads = new CopyOnWriteArrayList<ClientHandler>();
	ServerSocket serverSocket;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	Message incomingMessage;
	Message outgoingMessage;

	AtomicInteger uniqueRoomID = new AtomicInteger();

	public Server() throws IOException {
		uniqueRoomID.set(1);
		serverSocket = new ServerSocket(50000);

		String userChosenNickname;
		while (true) {
			System.out.println("Listening for clients...");
			
			Socket socket = serverSocket.accept();
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
						
			try {
				incomingMessage = (Message)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			userChosenNickname = (String) incomingMessage.getData();
			System.out.println("Received login request");

			if (isDuplicateNickname(userChosenNickname)) {
				outgoingMessage = new Message("login response", "duplicate");
				System.out.println("Request Rejected. Duplicate Nickname exists");
			} else {				
				outgoingMessage = new Message(null, "accepted");

				Client connectedClient = new Client(socket, oos, ois, userChosenNickname);
				clients.add(connectedClient);
				
				ClientHandler clientHandler = new ClientHandler(connectedClient, clients, clientHandlingThreads, rooms, uniqueRoomID);
				clientHandler.start();
				clientHandlingThreads.add(clientHandler);
				
				System.out.println("Client login successful. Added client count.");
			}
			oos.writeObject(outgoingMessage);

			System.out.println("Current connected clients: " + clients.size());
			System.out.println("Current client handling threads: " + clientHandlingThreads.size());
		}
	}

	public boolean isDuplicateNickname(String nickname) {
		for (Client c : clients) {
			if (c.getNickname().equals(nickname)) {
				return true;
			}
		}
		return false;
	}
}
