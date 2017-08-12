package me.fudged.murder2;

import org.bukkit.plugin.java.JavaPlugin;

import me.fudged.murder2.arena.Arena;
import me.fudged.murder2.arena.ArenaManager;
import me.fudged.murder2.command.CommandManager;
import me.fudged.murder2.storage.MurderFiles;
import me.fudged.murder2.storage.PlayerData;
import me.fudged.murder2.util.MurderConfig;

public class MCMurder extends JavaPlugin {
	
	private static MCMurder instance;
	private static MurderFiles arenas;
	private static ArenaManager arenaManager;
	private static PlayerData playerData;
	
	@Override
	public void onEnable(){
		instance = this;
		
		new MurderConfig(this);
		arenas = new MurderFiles("arenas");
		
		arenaManager = new ArenaManager();
		
		arenaManager.setupArenas();
		
		playerData = new PlayerData();
		
		getCommand("murder").setExecutor(new CommandManager());
		
	}
	
	@Override
	public void onDisable(){
		for(Arena a : arenaManager.getAllArenas()){
			a.stop();
		}
		
	}
	
	public static MCMurder getInstance(){
		return instance;
	}
	
	public MurderFiles getArenaFiles(){
		return arenas;
	}
	
	public ArenaManager getArenaManager(){
		return arenaManager;
	}
	
	public PlayerData getPlayerData(){
		return playerData;
	}

}
