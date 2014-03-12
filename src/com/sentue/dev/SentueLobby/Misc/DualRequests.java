package com.sentue.dev.SentueLobby.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class DualRequests extends JavaPlugin{
	
	public DualRequests() {}
	private static DualRequests instance = new DualRequests();
	public static DualRequests getInstance() {return instance;}
	
	private HashMap<Player, Player> requests = new HashMap<Player, Player>();
	
	public void addPerminateRequest(Player p1, Player p2){
		if(requests.containsKey(p1)) return;
		requests.put(p1, p2);
	}
	
	public void removeRequest(Player p1){
		if(!requests.containsKey(p1)) return;
		requests.remove(p1);
	}
	
	public void addDelayedRequest(final Player p1, Player p2){
		if(requests.containsKey(p1)) return;
		requests.put(p1, p2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SentueLobby"), new Runnable() {
			
			@Override
			public void run() {
				removeRequest(p1);
			}
		}, 1200L);
	}
	
	public boolean hasRequested(Player p1){
		if(requests.containsKey(p1)) return true;
		else return false;
	}
	
	public Player getRequested(Player p1){
		if(!requests.containsKey(p1)) return null;
		return requests.get(p1);
	}

}
