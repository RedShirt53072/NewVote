package com.gmail.akashirt53072.newvote;


import org.bukkit.Sound;
import org.bukkit.entity.Player;


public class SoundManager {
	
	
	
	public static void send(Player player,Sound s,float pitch) {
		player.playSound(player.getLocation(), s, 1, pitch);
	}
	

	public static void sendClick(Player player) {
		player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
	}
	
	public static void sendSelect(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
	}
	public static void sendCancel(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, (float)0.5);
	}
	
}
