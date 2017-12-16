package game.protocol;

import java.util.ArrayList;
import java.util.Vector;

public class Player extends GameObject{

	String username;
	boolean alive;
	boolean hit;
	short hp;
	short playerID;
	short direction = 0;
	
	Vector<Bullet> bullets;
	ArrayList<Integer> enemiesHit = new ArrayList<Integer>();
	
	public Player() {
		
	}
	
	public Player(Vector<Bullet> bullets) {
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
		this.setHP((short)player.getHP());
		this.setAlive(player.isAlive());
	}
	
	public void setBullets(Vector<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public Vector<Bullet> getBullets(){
		return bullets;
	}
	
	public void addHitEnemies(int i) {
		this.enemiesHit.add(i);
	}
	
	public ArrayList<Integer> getHitEnemies(){
		return enemiesHit;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	public boolean isHit() {
		return hit;
	}
}
