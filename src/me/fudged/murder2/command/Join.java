package me.fudged.murder2.command;

import org.bukkit.entity.Player;

import me.fudged.murder2.MCMurder;
import me.fudged.murder2.arena.Arena;
import me.fudged.murder2.arena.Arena.State;
import me.fudged.murder2.util.MurderConfig;

public class Join extends MurderCommand {
	
	public void onCommand(Player p, String[] args){
		if(MCMurder.getInstance().getArenaManager().getArena(p.getUniqueId()) != null){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " You are already in a game");
			return;
		}
		
		if(!p.hasPermission("murder.arena.join")){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " You do not have permission to do this");
			return;
		}
		
		if(args.length == 0){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " Please specify an arena");
			return;
		}
		
		Arena a = MCMurder.getInstance().getArenaManager().getArena(args[0]);
		
		if(a == null){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " The arena " + MurderConfig.SECONDARY + args[0] + MurderConfig.PRIMARY + " does not exist");
			return;
		}
		
		if(a.getState() == State.DISABLED){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " The arena " + MurderConfig.SECONDARY + args[0] + MurderConfig.PRIMARY + "is currently disabled");
			return;
		}
		
		if(a.getState() == State.STARTED){
			p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " The arena " + MurderConfig.SECONDARY + args[0] + MurderConfig.PRIMARY + "is currently in-game");
			return;
		}
		
		a.addPlayer(p);
		
	}
	
	public Join(){
		super("join (arena)", "Join an arena");
	}

}
