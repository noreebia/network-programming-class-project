package bhs.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

import bhs.client.game.main.World;
import processing.core.PApplet;
import protocol.Message;

public class InputHandler extends Thread{

	Socket socket;
	ObjectInputStream ois;
	boolean run = true;
	JTextArea chatbox;
	JList roomListBox;
	String username;
	String serverIP;
	
	public InputHandler(Socket socket, JTextArea chatbox, JList roomListBox, String username, String serverIP) {
		this.socket = socket;
		this.chatbox = chatbox;
		this.roomListBox = roomListBox;
		this.username = username;
		this.serverIP = serverIP;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		World world;
		String messageContents;
		Message message = null;
		String[] sketchArgs = {"Game"};
		while(shouldRun()) {
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
				world = new World(serverIP, roomPort, 0, username);
				PApplet.runSketch(sketchArgs, world);
				break;
			case "room list update":
				ArrayList<String> roomList = (ArrayList<String>) message.getData();
				DefaultListModel<String> listModel = new DefaultListModel<>();
				for(String roomInfo: roomList) {
					listModel.addElement(roomInfo);
				}
				roomListBox.setModel(listModel);
				break;
			case "join game response":
				int[] roomInfo = (int[]) message.getData();
				world = new World(serverIP, roomInfo[0], roomInfo[1], username);
				PApplet.runSketch(sketchArgs, world);
				break;
			}
		}
		return;
	}
	
	public boolean shouldRun() {
		return run;
	}
	
	public void terminate() {
		run = false;
	}
}
