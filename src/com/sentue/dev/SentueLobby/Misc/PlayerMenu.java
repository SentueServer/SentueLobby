package com.sentue.dev.SentueLobby.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerMenu{

	public PlayerMenu() {}
	private static PlayerMenu instance = new PlayerMenu();
	public static PlayerMenu getInstance() {return instance;}
	
	private HashMap<Player, Player> openMenus = new HashMap<Player, Player>();
	
	/**
	 * Displays a specified players menu to another specified player
	 * @param toDisplay The player whose menu is to be displayed
	 * @param displayTo The player who the menu will be displayed to*/
	public void displayMenuToPlayer(Player toDisplay, Player displayTo){
		Inventory Menu = getMenu(toDisplay);
		if(isInMenu(displayTo)){
			displayTo.sendMessage(ChatColor.RED + "You appear to be in a players menu already if this is a bug please report it.");
		}else{
			if(displayTo.isOp()){
				Menu.setItem(8, createMenuItem(Material.WOOL, (byte) 14, ChatColor.RED + "Admin Menu", ChatColor.WHITE + "Display the admin menu for " + toDisplay.getName()));
			}else{
				openMenus.put(displayTo, toDisplay);
				displayTo.openInventory(Menu);
			}
		}
	}
	
	/**
	 * Gets the menu of a player in the form of an inventory
	 * @param player The player whose menu it will return
	 * @return The menu in the form of an inventory*/
	public Inventory getMenu(Player player){
		Inventory inv = Bukkit.createInventory(null, 9, player.getName());
		ItemStack Dual = createMenuItem(Material.WOOL, (byte) 10, ChatColor.DARK_PURPLE + "Dual", ChatColor.WHITE + "Request to dual " + player.getName());
		ItemStack Achi = createMenuItem(Material.WOOL, (byte) 14, ChatColor.RED + "Achievements", ChatColor.WHITE + "Click to view " + player.getName() + " achievements");
		ItemStack Stat = createMenuItem(Material.WOOL, (byte) 14, ChatColor.RED + "Stats", ChatColor.WHITE + player.getName() + "'s stats");
		ItemStack Frie = createMenuItem(Material.WOOL, (byte) 5, ChatColor.GREEN + "Add Friend", ChatColor.WHITE + "Click to add " + player.getName() + " as friend");
		ItemStack Toss = createMenuItem(Material.WOOL, (byte) 5, ChatColor.GREEN + "Toss", ChatColor.WHITE + "Toss " + player.getName());
		
		inv.setItem(4, Dual);
		inv.setItem(3, Achi);
		inv.setItem(5, Stat);
		inv.setItem(2, Frie);
		inv.setItem(6, Toss);
		return inv;
	}
	
	/**
	 * Creates a menu item for a player menu
	 * @param mat The material of the item
	 * @param data The data of the item
	 * @param Name The name of the item
	 * @param Lore The first lore of the item
	 * @return The item stack of the menu item*/
	public ItemStack createMenuItem(Material mat, Byte data, String Name, String Lore){
		ItemStack item = new ItemStack(mat, 1, data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(Name);
		List<String> lore = new ArrayList<String>();
		lore.add(Lore);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	/**
	 * Checks to see if a player is in a menu returns true if they are
	 * @param player The player who is being check to see if they are in a menu
	 * @return Returns true if the player is in an menu*/
	public boolean isInMenu(Player player){
		if(openMenus.containsKey(player)) return true;
		else return false;
	}
	
	/**
	 * Checks to see if a player is in the menu of another player returns true if they are
	 * @param inMenu The player who the menu is being displayed to
	 * @param menuOf The player whose menu is being displayed
	 * @return Returns true if the player is in the menu of the other player*/
	public boolean isInMenuOf(Player inMenu, Player menuOf){
		if(openMenus.containsKey(inMenu)){
			if(openMenus.get(inMenu).getName().equalsIgnoreCase(menuOf.getName())) return true;
			else return false;
		}else{
			return false;
		}
	}
	
	/**
	 * Gets whose menu the player is inside
	 * @param player The player who is looking at a menu
	 * @return The player of the menu being looked at*/
	public Player inMenuOf(Player player){
		if(openMenus.containsKey(player)){
			Player menuOf = openMenus.get(player);
			return menuOf;
		}else{
			return null;
		}
	}
	
	public void exitMenu(Player player){
		if(isInMenu(player)){
			openMenus.remove(player);
		}
	}
	
}
