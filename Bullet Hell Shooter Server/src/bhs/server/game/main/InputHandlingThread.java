package bhs.server.game.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import bhs.server.game.control.*;
import bhs.server.game.model.*;

public class InputHandlingThread implements Runnable {

	DatagramSocket ioSocket;
	DatagramPacket packet;
	byte[] buf = new byte[8192];
	Player temp;
	DataController dataController;

	ByteArrayInputStream bais;
	ObjectInputStream is;

	EnemySystem enemySystem;

	public InputHandlingThread(DatagramSocket ioSocket, DataController dataController, EnemySystem enemySystem) {
		System.out.println("Input handler created.");
		this.ioSocket = ioSocket;
		this.dataController = dataController;
		this.enemySystem = enemySystem;
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Receiving");
				packet = new DatagramPacket(buf, buf.length);
				ioSocket.receive(packet);

				System.out.println("Received packet");
				bais = new ByteArrayInputStream(packet.getData());

				is = new ObjectInputStream(bais);

				System.out.println("attempting to update data");
				System.out.println("current num of players: " + dataController.getPlayers().size());
				try {
					temp = (Player) is.readObject();
					dataController.updatePlayer(temp);
					System.out.println("received player object from client and data updated");
					System.out.println("number of player bullets: " + temp.getBullets().size());

					for (Integer i : temp.getHitEnemies()) {
						enemySystem.getOriginals().get(i).getHit();
						if (enemySystem.getOriginals().get(i).isActive()) {
							enemySystem.changeShadowColor(i);
						} else {
							enemySystem.getShadows().get(i).setXY(-1000, -1000);
						}
						dataController.addExplosion(enemySystem.getOriginals().get(i).getLastKnownX(),
								enemySystem.getOriginals().get(i).getLastKnownY(),
								enemySystem.getShadows().get(i).getRGB(0), enemySystem.getShadows().get(i).getRGB(1),
								enemySystem.getShadows().get(i).getRGB(2));
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(1);
				} 
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
