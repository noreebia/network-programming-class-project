package bhs.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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
	DefaultListModel<String> listModel = new DefaultListModel<>();
	World world;

	public InputHandler(Socket socket, JTextArea chatbox, JList roomListBox, String username, JFrame lobby) {
		this.socket = socket;
		this.chatbox = chatbox;
		this.roomListBox = roomListBox;
		this.username = username;
		roomListBox.setModel(listModel);
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		world = new World(username, lobby);
		
		String[] sketchArgs = {""};
		PApplet.runSketch(sketchArgs, world);
	}
	
	public void run() {
		String messageContents;
		Message message = null;
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
			case "refresh room list response":
				ArrayList<String> roomList = (ArrayList<String>) message.getData();
				listModel.clear();
				for(String roomInfo: roomList) {
					listModel.addElement(roomInfo);
				}
				break;
			case "join game response":
				int[] roomInfo = (int[]) message.getData();
				//world = new World(socket.getInetAddress().getHostAddress(), roomInfo[0], roomInfo[1], username);
				//PApplet.runSketch(sketchArgs, world);
				world.setConnection(socket.getInetAddress().getHostAddress(), roomInfo[0], roomInfo[1]);
				world.initializeWorld();
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
