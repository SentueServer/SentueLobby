package com.sentue.dev.SentueLobby.Misc;

import net.minecraft.server.v1_7_R1.ChatSerializer;
import net.minecraft.server.v1_7_R1.IChatBaseComponent;
import net.minecraft.server.v1_7_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MiscFunction{

	public void sendRawMessage(Player p, String message){
		IChatBaseComponent comp = ChatSerializer.a(message);
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
