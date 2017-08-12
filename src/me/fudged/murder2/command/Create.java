package me.fudged.murder2.command;

import org.bukkit.entity.Player;

import me.fudged.murder2.MCMurder;
import me.fudged.murder2.util.MurderConfig;

public class Create extends MurderCommand {
	
	public void onCommand(Player p, String[] args){
		if(!p.hasPermission("murder.arena.create")){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " You do not have permission to do this");
			return;
		}
		
		if(args.length == 0){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " Please enter an arena name");
			return;
		}
		
		MCMurder.getInstance().getArenaManager().createArena(args[0], p.getLocation());
		
		p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " You have created a new arena called " + MurderConfig.SECONDARY + args[0]);
		p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " All the arena's locations have been set to your location");
		
	}
	
	public Create(){
		super("create (name)", "Create an arena");
	}

}
