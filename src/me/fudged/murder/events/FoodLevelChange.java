package me.fudged.murder.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.fudged.murder.ArenaManager;

public class FoodLevelChange implements Listener{	
	
	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent e){
		if(e.getEntityType() != EntityType.PLAYER) return;
		
		if(ArenaManager.getInstance().getArena((Player) e.getEntity()) != null) e.setFoodLevel(20);
	}

}
