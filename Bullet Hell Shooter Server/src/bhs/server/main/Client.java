package bhs.server.main;

import java.io.*;
import java.net.Socket;

public class Client {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String nickname;
	
	public Client(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, String nickname) {
		this.socket = socket;
		this.oos = oos;
		this.ois = ois;
		this.nickname = nickname;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getNickname(){
		return nickname;
	}
	
	public ObjectInputStream getInputStream() {
		return ois;
	}
	
	public ObjectOutputStream getOutputStream() {
		return oos;
	}
}
