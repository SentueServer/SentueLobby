package com.sentue.dev.misc;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Dual extends JavaPlugin{
	
	public Dual() {}
	private static Dual instance = new Dual();
	public static Dual getInstance() {return instance;}
	
	private HashMap<Player, Player> Dual = new HashMap<Player, Player>();
	
	public void startDual(Player p1, Player p2){
		if(Dual.containsKey(p1)) return;
		if(Dual.containsKey(p2)) return;
		if(Dual.containsValue(p1)) return;
		if(Dual.containsValue(p2)) return;
		Dual.put(p1, p2);
		Dual.put(p2, p1);
		p1.sendMessage("You are now dualing " + p2.getName());
		p2.sendMessage("You are now dualing " + p1.getName());
	}
	
	public void endDual(Player p1, Player p2){
		if(!Dual.containsKey(p1)) return;
		if(!Dual.containsKey(p2)) return;
		if(!Dual.containsValue(p1)) return;
		if(!Dual.containsValue(p2)) return;
		Dual.remove(p1);
		Dual.remove(p2);
	}
	
	public boolean isInDual(Player p){
		if(Dual.containsKey(p)) return true;
		if(Dual.containsValue(p)) return true;
		else return false;
	}
	
	public boolean isInInDualWith(Player p1, Player p2){
		if(!Dual.containsKey(p1)) return false;
		if(!Dual.containsKey(p2)) return false;
		if(!Dual.containsValue(p1)) return false;
		if(!Dual.containsValue(p2)) return false;
		if(Dual.get(p1).equals(p2)) return true;
		else return false;
	}
	
	public Player getInDualWith(Player p){
		if(!Dual.containsKey(p)) return null;
		if(!Dual.containsValue(p)) return null;
		return Dual.get(p);
	}

}
