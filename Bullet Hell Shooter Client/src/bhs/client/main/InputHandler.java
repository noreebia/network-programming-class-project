package bhs.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import bhs.client.game.main.World;
import processing.core.PApplet;
import protocol.Message;

public class InputHandler extends Thread {

	Socket socket;
	ObjectInputStream ois;
	volatile boolean run = true;
	JTextArea chatbox;
	JList roomListBox;
	String username;
	DefaultListModel<String> listModel = new DefaultListModel<>();
	World world;
	Lobby lobby;
	AvatarDisplayPanel avatarPanel;

	public InputHandler(Socket socket, ObjectInputStream ois, JTextArea chatbox, JList roomListBox, String username,
			Lobby lobby, AvatarDisplayPanel avatarPanel) {
		this.socket = socket;
		this.chatbox = chatbox;
		this.roomListBox = roomListBox;
		this.username = username;
		this.avatarPanel = avatarPanel;
		this.ois = ois;
		this.lobby = lobby;
		roomListBox.setModel(listModel);
		world = new World(username, lobby);
		String[] sketchArgs = { "" };
		PApplet.runSketch(sketchArgs, world);
	}

	public void run() {
		String messageContents;
		Message message = null;
		while (shouldRun()) {
			try {
				message = (Message) ois.readObject();
				// System.out.println("Received message from server");
				messageContents = message.getContents();
				System.out.println("Received " + messageContents + " message from server");
				switch (messageContents) {
				case "chatboxUpdate":
					String messageData = (String) message.getData();
					chatbox.append(messageData);
					break;
				case "refresh room list response":
					ArrayList<String> roomList = (ArrayList<String>) message.getData();
					listModel.clear();
					for (String roomInfo : roomList) {
						listModel.addElement(roomInfo);
					}
					break;
				case "join game response":
					int[] roomInfo = (int[]) message.getData();
					world.setServerInfo(socket.getInetAddress().getHostAddress(), roomInfo[0], roomInfo[1]);
					world.reset(avatarPanel.getAvatarColor());
					break;
				default:
				}
			} catch (SocketException e) {
				try {
					ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (lobby.hasPressedExit()) {
					terminate();
					break;
				} else {
					/*
					 * display error message because it means server has shut down, not that user
					 * has pressed exit
					 */
					lobby.closeOutputStream();
					try {
						socket.close();
						ErrorScreen errorScreen = new ErrorScreen();
						errorScreen.setVisible(true);
						lobby.dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					terminate();
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				terminate();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				terminate();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				terminate();
				break;
			}
		}
		world.destroy();
		return;
	}

	public boolean shouldRun() {
		return run;
	}

	public void terminate() {
		run = false;
	}
}
