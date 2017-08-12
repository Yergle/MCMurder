package me.fudged.murder.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.fudged.murder.ArenaManager;

public class PlayerPickupItem implements Listener {
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e){
		if(ArenaManager.getInstance().getArena(e.getPlayer()) != null) e.setCancelled(true);
	}

}
