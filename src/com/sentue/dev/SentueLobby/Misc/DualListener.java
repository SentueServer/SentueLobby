package com.sentue.dev.SentueLobby.Misc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DualListener implements Listener{
	
	public DualListener() {}
	private static DualListener instance = new DualListener();
	public static DualListener getInstance() {return instance;}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(!event.getEntity().getType().equals(EntityType.PLAYER)) event.setCancelled(true);
		Player p1 = (Player) event.getEntity();
		if(!Dual.getInstance().isInDual(p1)) event.setCancelled(true);
		if(!event.getDamager().getType().equals(EntityType.PLAYER)) event.setCancelled(true);
		Player p2 = (Player) event.getDamager();
		if(!Dual.getInstance().getInDualWith(p1).equals(p2)) event.setCancelled(true);
		p1.setVelocity(p2.getLocation().getDirection().multiply(2));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("dual")){
			Player p1 = (Player) sender;
			if(args.length != 1){
				sendCorrection(p1);
			}else{
				Player p2 = Bukkit.getPlayer(args[1]);
				if(args[0].equalsIgnoreCase("accept")){
					Dual.getInstance().startDual(p1, p2);
				}else if(args[0].equalsIgnoreCase("decline")){
					p2.sendMessage("Succesfully declined " + p1.getName() + "'s request");
				}else{
					sendCorrection(p1);
				}
			}
		}
		return false;
	}

	private void sendCorrection(Player p){
		p.sendMessage("The correct syntax is /dual [accept/decline] [playername]");
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player p1 = event.getEntity();
		if(!Dual.getInstance().isInDual(p1)) return;
		Player p2 = Dual.getInstance().getInDualWith(p1);
		Dual.getInstance().endDual(p1, p2);
		p1.sendMessage("You have lost the dual!");
		p2.sendMessage("You have defeated " + p1.getName());
	}
	
}
