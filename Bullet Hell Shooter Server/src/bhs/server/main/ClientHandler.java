package bhs.server.main;

import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import protocol.Message;


public class ClientHandler extends Thread{
	Client client;
	ArrayList<Client> clientList;
	BufferedReader input;
	PrintWriter output;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public ClientHandler(Client client, ArrayList<Client> clientList) {
		this.client = client;
		this.clientList = clientList;
		try {
			//input = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			//output = new PrintWriter(client.getSocket().getOutputStream(), true);
			
			oos = new ObjectOutputStream(client.getSocket().getOutputStream());
			ois = new ObjectInputStream(client.getSocket().getInputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("A client handler has just been created");
	}

	public void run() {
		Message message = null;
		String messageContents;
		while(true) {
			System.out.println("listening for client input...");
			//message = input.readLine();
			try {
				message = (Message)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				disconnectClient();
				System.exit(1);
				break;
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			if(message == null) {
				disconnectClient();
				break;
			}
			
			messageContents = message.getContents();
			
			System.out.println("From client: " + client.getNickname());
			System.out.println("Message contents: " + messageContents);
			switch(messageContents) {
			case "chat":
				String chatMessage = (String) message.getData();
				System.out.println(chatMessage);
				sendToAllClients(chatMessage);
				break;
			}
		}
		return;
	}
	
	public void sendToAllClients(String chatMessage) {
		Socket tempSocket;
		ObjectOutputStream tempOos = null;
		Message message = new Message("chatboxUpdate", chatMessage);
		for(Client c: clientList) {
			tempSocket = c.getSocket();
			try {
				tempOos = new ObjectOutputStream(tempSocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				tempOos.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnectClient() {
		clientList.remove(client);
	}
}
