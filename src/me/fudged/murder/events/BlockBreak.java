package me.fudged.murder.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.fudged.murder.ArenaManager;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(ArenaManager.getInstance().getArena(e.getPlayer()) != null) e.setCancelled(true);
	}
}
