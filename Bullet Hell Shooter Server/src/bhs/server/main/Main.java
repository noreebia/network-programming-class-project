package bhs.server.main;

import java.io.*;
import java.net.Socket;

import bhs.server.main.Server;

public class Main {
	public static void main(String argv[]) throws Exception {
		Server server = new Server();
		/*
		Socket socket = new Socket("localhost", 40000);
		
		BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
		
		outputToServer.writeBytes("hey there\n");
		String message = inputFromServer.readLine();
		System.out.println("Received: " + message);
		socket.close();
		*/
	}
}