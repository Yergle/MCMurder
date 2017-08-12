package me.fudged.murder.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.fudged.murder.ArenaManager;
import me.fudged.murder.MessageManager;
import me.fudged.murder.PlayerData;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e){

		Player p = e.getPlayer();

		if(ArenaManager.getInstance().getArena(p) != null){
			p.getInventory().clear();
			
			MessageManager.getInstance().messageArena(ArenaManager.getInstance().getArena(p), p.getName() + " has " + ChatColor.RED + "disconnected" + ChatColor.GRAY + "!");
			ArenaManager.getInstance().getArena(p).removePlayer(p);
			
		}
		
		if(PlayerData.getInstance().hasPlayerData(p)){
			PlayerData.getInstance().restorePlayerData(p);
			PlayerData.getInstance().removePlayerData(p);
		}

	}

}
