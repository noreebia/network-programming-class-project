package bhs.client.game.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import game.protocol.*;

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
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (player.getHitEnemies().size() > 0) {
				player.getHitEnemies().clear();
			}
			if (player.isHit()) {
				player.setHit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
