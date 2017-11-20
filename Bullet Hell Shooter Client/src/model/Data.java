package model;

import java.io.Serializable;
import java.util.*;

public class Data implements Serializable{
	
	public short level;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<GameObject> enemies = new ArrayList<GameObject>();
	public ArrayList<GameObject> explosions = new ArrayList<GameObject>();

}