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

	public InputHandlingThread(DatagramSocket socket, DataController dataController, short connectionID) {
		System.out.println("Input handler created.");
		this.socket = socket;
		this.dataController = dataController;
		this.connectionID = connectionID;
	}

	public void run() {
		while (true) {
			try {
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				//System.out.println("Received packet");
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
				System.out.println("data received and cloned");
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
