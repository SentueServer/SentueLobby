package com.sentue.dev.SentueLobby;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sentue.dev.SentueLobby.Misc.Dual;
import com.sentue.dev.SentueLobby.Misc.DualListener;
import com.sentue.dev.SentueLobby.Misc.DualRequests;
import com.sentue.dev.SentueLobby.Misc.PlayerMenu;
import com.sentue.dev.SentueLobby.Misc.PlayerMenuListener;

public class Main extends JavaPlugin{
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(PlayerMenuListener.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(DualListener.getInstance(), this);
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("test")){
			Player p = (Player) sender;
			try{
			PlayerMenu.getInstance().displayMenuToPlayer(p, p);
			}catch(Exception e){
				Bukkit.getLogger().severe("Failed to display player menu");
				Bukkit.getLogger().severe(e.getMessage());
			}
		}
		if(commandLabel.equalsIgnoreCase("dual")){
			Player p1 = (Player) sender;
			if(args.length != 2){ 
				p1.sendMessage("The correct syntax is /dual [accept/decline] [playername]");
			}else{
				Player p2 = Bukkit.getPlayer(args[1]);
				if(!DualRequests.getInstance().hasRequested(p2)) return false;
				if(args[0].equalsIgnoreCase("accept")){
					try{
					Dual.getInstance().startDual(p1, p2);
					}catch(Exception e){
						Bukkit.getLogger().severe("Failed to start a dual");
						Bukkit.getLogger().severe(e.getMessage());
					}
				}else if(args[0].equalsIgnoreCase("decline")){
					p2.sendMessage("Succesfully declined " + p1.getName() + "'s request");
				}else{
					p1.sendMessage("The correct syntax is /dual [accept/decline] [playername]");
				}
			}
		}
		return false;
	}
	
}
