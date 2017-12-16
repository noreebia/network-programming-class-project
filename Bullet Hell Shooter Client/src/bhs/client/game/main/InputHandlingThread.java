package bhs.client.game.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import bhs.client.game.control.DataController;
import game.protocol.Data;
import game.protocol.Player;

public class InputHandlingThread implements Runnable {

	byte[] buf = new byte[20000];

	DatagramPacket packet;
	DatagramSocket socket;

	ByteArrayInputStream bais;
	ObjectInputStream is;

	Data temp;
	DataController dataController;

	short connectionID;
	boolean run = true;

	public InputHandlingThread(DatagramSocket socket, DataController dataController, short connectionID) {
		this.socket = socket;
		this.dataController = dataController;
		this.connectionID = connectionID;
	}

	public void run() {
		while (shouldRun()) {
			try {
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				bais = new ByteArrayInputStream(packet.getData());
				is = new ObjectInputStream(bais);

				try {
					temp = (Data) is.readObject();
				} catch (InvalidClassException e) {
					e.printStackTrace();
					System.exit(1);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(1);
				}
				dataController.updateData(temp);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean shouldRun() {
		return run;
	}
	
	public void terminate() {
		run = false;
	}
}
