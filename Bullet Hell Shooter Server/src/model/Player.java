package model;

import java.util.ArrayList;

public class Player extends GameObject{

	String username;
	short hp;
	short playerID;
	short direction = 0;
	
	ArrayList<Bullet> bullets;
	
	ArrayList<Integer> enemiesHit = new ArrayList<Integer>();
	
	public Player() {
		
	}
	
	public Player(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public void setID(short id) {
		playerID = id;
	}
	
	public short getID() {
		return playerID;
	}
	
	public void setDirection(short direction) {
		this.direction = direction;
	}
	
	public short getDirection() {
		return direction;
	}
	
	public void cloneInfoOf(GameObject object) {
		this.setXY(object.getX(), object.getY());
		this.setRGB(object.getRGB(0), object.getRGB(1), object.getRGB(2));
	}
	
	public void cloneInfoOf(Player player) {
		this.setXY(player.getX(), player.getY());
		this.setRGB(player.getRGB(0), player.getRGB(1), player.getRGB(2));
		this.setBullets(player.getBullets());
		this.setDirection(player.getDirection());
	}
	
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void addHitEnemies(int i) {
		this.enemiesHit.add(i);
	}
	
	public ArrayList<Integer> getHitEnemies(){
		return enemiesHit;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setHP(short hp) {
		this.hp = hp;
	}
	
	public short getHP() {
		return hp;
	}
}
