package com.sentue.dev;

import com.sentue.dev.misc.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	public Main() {}
	private static Main instance = new Main();
	public static Main getInstance() {return instance;}
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(PlayerMenuListener.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(DualListener.getInstance(), this);
	}
	
	public void onDisable(){
		
	}
	
	/*
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		
			Connection con = DriverManager.getConnection("jdbc:mysql://66.85.144.162:3306/mcph21073", "mcph21073", "7750d849a2");
			
			PreparedStatement statement = con.prepareStatement("select * from PlayerInfo where Username = ?");
			statement.setString(1, event.getPlayer().getName());
		
			ResultSet result = statement.executeQuery();
			
			if(result == null){
				PreparedStatement statement1 = con.prepareStatement("INSERT INTO PlayerInfo(Username, Rank, Pennies, FirstJoin, LastJoin) VALUES (" + event.getPlayer().getName() + ", Peasant, 0, " + System.currentTimeMillis() + "," + System.currentTimeMillis() + ")");
				statement1.execute();
			}else{
				PreparedStatement statement1 = con.prepareStatement("UPDATE `PlayerInfo` SET `LastJoin`=" + System.currentTimeMillis() + "WHERE Username='" + event.getPlayer().getName() + "'");
				statement1.execute();
			}
			
		}catch(Exception e){
			Bukkit.getLogger().severe(e.getMessage());
		}
	}
	*/
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("test")){
			Player p = (Player) sender;
			PlayerMenu.getInstance().displayMenuToPlayer(p, p);
		}
		if(commandLabel.equalsIgnoreCase("dual")){
			Player p1 = (Player) sender;
			if(args.length != 2){ 
				p1.sendMessage("The correct syntax is /dual [accept/decline] [playername]");
			}else{
				Player p2 = Bukkit.getPlayer(args[1]);
				if(!DualRequests.getInstance().hasRequested(p2)) return false;
				if(args[0].equalsIgnoreCase("accept")){
					Dual.getInstance().startDual(p1, p2);
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
