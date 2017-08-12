package me.fudged.murder.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.fudged.murder.ArenaManager;

public class PlayerDropItem implements Listener{
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(ArenaManager.getInstance().getArena(e.getPlayer()) != null) e.setCancelled(true);
	}

}
