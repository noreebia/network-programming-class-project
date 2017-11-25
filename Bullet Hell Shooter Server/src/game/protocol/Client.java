package game.protocol;

import java.net.InetAddress;

public class Client {
	
	long lastSentTime;
	InetAddress address;
	int port;
	short id;
	
	public Client(short id, InetAddress address, int port, long lastSentTime){
		this.id = id;
		this.address = address;
		this.port = port;
		this.lastSentTime = lastSentTime;
	}
	
	public Client(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void setID(short id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setLastSentTime(long lastSentTime) {
		this.lastSentTime = lastSentTime;
	}
	
	public long getLastSentTime() {
		return lastSentTime;
	}
}