package bhs.server.game.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import bhs.server.game.control.*;
import model.Client;

public class OutputHandlingThread implements Runnable {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream os;

	byte[] buf = new byte[8192];
	DatagramSocket ioSocket;
	DatagramPacket packet;

	DataController dataController;
	CopyOnWriteArrayList<Client> clients;

	EnemySystem enemySystem;

	public OutputHandlingThread(DatagramSocket ioSocket, DataController dataController, CopyOnWriteArrayList<Client> clients, EnemySystem enemySystem) {
		System.out.println("OutputHandlingThread created");
		this.ioSocket = ioSocket;
		try {
			os = new ObjectOutputStream(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.clients = clients;
		this.dataController = dataController;
		this.enemySystem = enemySystem;
	}

	public void run() {
		try {
			enemySystem.run();

			try {
				baos.reset();
				os = new ObjectOutputStream(baos);
				os.writeObject(dataController.getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			buf = baos.toByteArray();
			
			/*
			int i;
			for(i=0; i< clients.size(); i++) {
				if(System.currentTimeMillis() - clients.get(i).getLastSentTime() <= 2000) {
					packet = new DatagramPacket(buf, buf.length, clients.get(i).getAddress(), clients.get(i).getPort());
					try {
						ioSocket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Sent to:" + clients.get(i).getAddress().toString());
				}
			}
			*/
			
			for (Client c : clients) {
				if(System.currentTimeMillis() - c.getLastSentTime() <= 2000) {
					packet = new DatagramPacket(buf, buf.length, c.getAddress(), c.getPort());
					try {
						ioSocket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Sent to:" + c.getAddress().toString());
				}else {
					dataController.removePlayer((short) c.getID());
					clients.remove(c);
				}
			}
			
			//System.out.println("Length of sent data in byte: " + buf.length);
			//System.out.println("number of explosions sent: " + dataController.getExplosions().size());
			if (dataController.getExplosions().size() > 0) {
				dataController.clearExplosions();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
