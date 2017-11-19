package bhs.server.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import bhs.server.game.main.Room;

public class Server {

	ArrayList<Client> clients = new ArrayList<Client>();
	ArrayList<Room> rooms = new ArrayList<Room>();

	ArrayList<ClientHandler> clientHandlingThreads = new ArrayList<ClientHandler>();
	ServerSocket serverSocket;

	BufferedReader input;
	PrintWriter output;

	public Server() throws IOException {
		serverSocket = new ServerSocket(50000);

		String userChosenNickname;
		while (true) {
			System.out.println("Listening for clients...");
			
			Socket socket = serverSocket.accept();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);

			userChosenNickname = input.readLine();

			if (isDuplicateNickname(userChosenNickname)) {
				output.println("duplicate");
				System.out.println("duplicate nickname");
			} else {				
				output.println("connected");

				Client connectedClient = new Client(socket, userChosenNickname);
				clients.add(connectedClient);
				
				ClientHandler clientHandler = new ClientHandler(connectedClient, clients, clientHandlingThreads);
				clientHandler.start();
				clientHandlingThreads.add(clientHandler);
				
				System.out.println("success");
				//Thread clientHandler = new Thread(new ClientHandler(connectedClient));
				//new Thread(new ClientHandler(connectedClient)).start();
				//clientHandler.start();
				//clientHandlingThreads.add(clientHandler);
				//output.println("connected");
			}
			System.out.println("current connected clients: " + clients.size());
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
