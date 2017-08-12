package me.fudged.murder2.command;

import org.bukkit.entity.Player;

public abstract class MurderCommand {
	
	public abstract void onCommand(Player p, String[] args);
	
	private String usage, message;
	
	public MurderCommand(String usage, String message){
		this.usage = usage;
		this.message = message;
	}
	
	public final String getMessage(){
		return message;
	}
	
	public final String getUsage(){
		return usage;
	}
	
}
