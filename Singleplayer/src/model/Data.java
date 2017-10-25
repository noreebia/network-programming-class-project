package model;
import java.io.Serializable;
import java.util.*;

public class Data implements Serializable{
	
	ArrayList<Player> players = new ArrayList<Player>();
	Random rand = new Random();
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void randomize() {
		for(Player p: players) {
			p.setXY(rand.nextInt(1200) + 1, rand.nextInt(800) + 1);
		}
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
}