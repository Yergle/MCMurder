package me.fudged.murder.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.fudged.murder.ArenaManager;

public class BlockPlace implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		if(ArenaManager.getInstance().getArena(e.getPlayer()) != null) e.setCancelled(true);
	}

}
