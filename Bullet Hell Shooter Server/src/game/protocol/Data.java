package game.protocol;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data implements Serializable{
	
	public short level;
	public CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<Player>();
	public ArrayList<GameObject> enemies = new ArrayList<GameObject>();
	public ArrayList<GameObject> explosions = new ArrayList<GameObject>();

}