package com.sentue.dev.misc;

import net.minecraft.server.v1_7_R1.ChatSerializer;
import net.minecraft.server.v1_7_R1.IChatBaseComponent;
import net.minecraft.server.v1_7_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerMenuListener extends JavaPlugin implements Listener{

	public PlayerMenuListener() {}
	private static PlayerMenuListener instance = new PlayerMenuListener();
	public static PlayerMenuListener getInstance() {return instance;}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getCurrentItem().equals(null)) return;
		if(event.getCursor().equals(null)) return;
		if(!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
		if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Dual"))
			dualMenuSelect((Player) event.getWhoClicked());
		if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Achievements"))
			achievementMenuSelect((Player) event.getWhoClicked());
		if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Stats"))
			statMenuSelect((Player) event.getWhoClicked());
		if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Add Friend"))
			friendMenuSelect((Player) event.getWhoClicked());
		if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Toss"))
			tossMenuSelect((Player) event.getWhoClicked());
		if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Admin Menu"))
			;
		else
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		Player p = Bukkit.getPlayer(event.getPlayer().getName());
		PlayerMenu.getInstance().exitMenu(p);
	}
	
	public void tossMenuSelect(Player p){
		Player menuPlayer = PlayerMenu.getInstance().inMenuOf(p);
		p.sendMessage("You have chosen to toss " + menuPlayer.getName() + " punch to throw them in that direction");
		p.closeInventory();
		menuPlayer.setVelocity(p.getLocation().getDirection().multiply(2));
	}
	
	public void dualMenuSelect(Player p){
		Player menuPlayer = PlayerMenu.getInstance().inMenuOf(p);
		
		DualRequests.getInstance().addDelayedRequest(p, menuPlayer);
		
		p.sendMessage("You have requested to dual " + menuPlayer.getName());
		menuPlayer.sendMessage(p.getName() + " has requested to dual you");
		
		String rawMessage = "{\"text\":\" \",\"color\":\"white\",\"extra\":[{\"text\":\"[Accept] \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/dual accept " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Accept " + p.getName() + "'s dual request\"}},{\"text\":\"[Decline]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/dual decline " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Decline " + p.getName() + "'s dual request\"}}]}";
		
		IChatBaseComponent comp = ChatSerializer.a(rawMessage);
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
        ((CraftPlayer) menuPlayer).getHandle().playerConnection.sendPacket(packet);
        
		p.closeInventory();
	}
	
	public void friendMenuSelect(Player p){
		Player menuPlayer = PlayerMenu.getInstance().inMenuOf(p);
		
		p.sendMessage("You have sent a friend request to " + menuPlayer.getName());
		menuPlayer.sendMessage(p.getName() + " has sent you a friend request");
		
		String rawMessage = "{\"text\":\" \",\"color\":\"white\",\"extra\":[{\"text\":\"[Accept] \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/friend accept " + menuPlayer.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Accept " + menuPlayer.getName() + "'s friend request\"}},{\"text\":\"[Decline]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/friend decline " + menuPlayer.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Decline " + menuPlayer.getName() + "'s friend request\"}}]}";
		
		IChatBaseComponent comp = ChatSerializer.a(rawMessage);
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        
		p.closeInventory();
	}
	
	public void achievementMenuSelect(Player p){
		Inventory inv = Bukkit.createInventory(null, 36, "Place holder for achievement menu");
		p.openInventory(inv);
	}
	
	public void statMenuSelect(Player p){
		Inventory inv = Bukkit.createInventory(null, 36, "Place holder for stats menu");
		p.openInventory(inv);
	}
	
	public void adminMenuSelect(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "Admin Menu");
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		if(event.getRightClicked().getType().equals(EntityType.PLAYER)) PlayerMenu.getInstance().displayMenuToPlayer((Player) event.getRightClicked(), event.getPlayer());
	}
}
