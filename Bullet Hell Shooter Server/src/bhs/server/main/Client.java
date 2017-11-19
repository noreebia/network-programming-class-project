package bhs.server.main;

import java.net.Socket;

public class Client {
	private Socket socket;
	private String nickname;
	
	public Client(Socket socket, String nickname) {
		this.socket = socket;
		this.nickname = nickname;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getNickname(){
		return nickname;
	}
}
