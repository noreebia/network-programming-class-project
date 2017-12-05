package bhs.client.game.control;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import game.protocol.*;


public class DataController {

	private Data data = new Data();
	
	public boolean levelChanged;
	
	public DataController(){
	}
	
	public void updateData(Data data) {
		if(this.data.level != data.level) {
			this.data.level = data.level;
			setLevelChanged(true);
		}
		if(data.explosions.size() > 0) {
			this.data.explosions.addAll(data.explosions);
		}
		this.data.players = data.players;
		this.data.enemies = data.enemies;
	}
	
	public CopyOnWriteArrayList<Player> getPlayers(){
		return data.players;
	}
	
	public Data getData() {
		return data;
	}
	
	public ArrayList<GameObject> getEnemies(){
		return data.enemies;
	}
	
	public ArrayList<GameObject> getExplosions(){
		return data.explosions;
	}
	
	public short getLevel() {
		return data.level;
	}
	
	public void setLevel(short level) {
		this.data.level = level;
	}
	
	public void setLevelChanged(boolean levelChanged) {
		this.levelChanged = levelChanged;
	}
	
	public boolean hasLevelChanged() {
		return levelChanged;
	}
	
	public int getAlivePlayers() {
		int alivePlayers=0;
		for(Player p: data.players) {
			if(p.isAlive()) {
				alivePlayers++;
			}
		}
		return alivePlayers;
	}
}
