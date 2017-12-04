package bhs.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
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
	PanelWithDrawing avatarPanel;

	public InputHandler(Socket socket, ObjectInputStream ois, JTextArea chatbox, JList roomListBox, String username, JFrame lobby, PanelWithDrawing avatarPanel) {
		this.socket = socket;
		this.chatbox = chatbox;
		this.roomListBox = roomListBox;
		this.username = username;
		this.avatarPanel = avatarPanel;
		this.ois = ois;
		roomListBox.setModel(listModel);
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
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
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
				int[] avatarColor = {avatarPanel.getBackground().getRed(), avatarPanel.getBackground().getGreen(),avatarPanel.getBackground().getBlue()};
				world.setServerInfo(socket.getInetAddress().getHostAddress(), roomInfo[0], roomInfo[1]);
				world.reset(avatarPanel.getAvatarColor());
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
