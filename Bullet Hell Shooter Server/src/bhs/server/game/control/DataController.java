package bhs.server.game.control;
import java.util.ArrayList;


import java.util.LinkedList;
import java.util.Queue;

import bhs.server.game.model.*;

public class DataController {
	
	private Data data = new Data();
	
	public DataController() {
		setLevel(1);
	}
	
	public void addPlayer(Player player) {
		getPlayers().add(player);
	}
	
	public void updatePlayer(Player player) {
		for(Player p: getPlayers()) {
			if(p.getID() == player.getID()) {
				p.cloneInfoOf(player);
				return;
			}
		}
		addPlayer(player);
	}
	
	public void createNewEnemyArrayList() {
		data.enemies = new ArrayList<GameObject>();
	}
	
	public void addExplosion(float x, float y, short r, short g, short b) {
		data.explosions.add(new GameObject(x, y,r,g,b));
	}
	
	public void clearExplosions() {
		data.explosions.clear();
	}
	
	public Data getData() {
		return data;
	}
	
	public ArrayList<Player> getPlayers(){
		return data.players;
	}
	
	public ArrayList<GameObject> getEnemies(){
		return data.enemies;
	}
	
	public ArrayList<GameObject> getExplosions(){
		return data.explosions;
	}
	
	public void increaseLevel() {
		setLevel(getLevel() + 1);
	}
	
	public void setLevel(int level) {
		data.level = (short) level;
	}
	
	public short getLevel() {
		return data.level;
	}
}
