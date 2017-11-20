package bhs.client.game.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import model.*;


public class OutputHandlingThread implements Runnable {

	InetAddress serverAddress;
	int serverPort;

	DatagramSocket socket;
	DatagramPacket packet;

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream os;
	Player player;

	byte[] buf;

	public OutputHandlingThread(DatagramSocket socket, InetAddress serverAddress, int serverPort, Player player) {
		System.out.println("Output handler created");
		this.serverAddress = serverAddress;
		this.socket = socket;
		this.player = player;
		this.serverPort = serverPort;

		try {
			os = new ObjectOutputStream(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			baos.reset();
			os = new ObjectOutputStream(baos);
			os.writeObject(player);

			buf = baos.toByteArray();

			packet = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
			
			System.out.println("Number of bullets: " + player.getBullets().size());
			System.out.println("Length of sent data in bytes: " + buf.length);

			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Sent");
			if(player.getHitEnemies().size() > 0) {
				player.getHitEnemies().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
