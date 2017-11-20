package bhs.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JTextArea;

import bhs.client.game.main.World;
import processing.core.PApplet;
import protocol.Message;

public class InputHandler extends Thread{

	Socket socket;
	ObjectInputStream ois;
	boolean stop = false;
	JTextArea chatbox;
	
	public InputHandler(Socket socket, JTextArea chatbox) {
		this.socket = socket;
		this.chatbox = chatbox;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String messageContents;
		Message message = null;
		while(!shouldStop()) {
			try {
				message = (Message) ois.readObject();
				System.out.println("received message from server");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			messageContents = message.getContents();
			switch(messageContents) {
			case "chatboxUpdate":
				String messageData = (String) message.getData();
				chatbox.append(messageData);
				break;
			case "create game response":
				System.out.println("received response to 'host game' button click");
				int roomPort = (int) message.getData();
				String[] sketchArgs = {"Game"};
				World world = new World(roomPort);
				PApplet.runSketch(sketchArgs, world);
				break;
			}
		}
		return;
	}
	
	public boolean shouldStop() {
		return stop;
	}
	
	public void terminate() {
		stop = true;
	}
}
