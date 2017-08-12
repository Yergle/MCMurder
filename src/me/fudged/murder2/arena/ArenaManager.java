package me.fudged.murder2.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import me.fudged.murder2.MCMurder;

public class ArenaManager {
	
	public ArenaManager(){
		
	}
	
	private List<Arena> arenas = new ArrayList<Arena>();
	
	public void setupArenas(){
		if(!arenas.isEmpty()){
			arenas.clear();
		}
		
		if(MCMurder.getInstance().getArenaFiles().getSection("arenas") == null){
			MCMurder.getInstance().getArenaFiles().creatSection("arenas");
		}
		
		for(String arenaName : MCMurder.getInstance().getArenaFiles().<ConfigurationSection>get("arenas").getKeys(false)){
			arenas.add(new Arena(arenaName));
		}
		
	}
	
	public void createArena(String name, Location loc){
		arenas.add(new Arena(name, loc));
	}
	
	public Arena getArena(String name){
		for(Arena a : arenas){
			if(a.getName().equalsIgnoreCase(name)){
				return a;
			}
		}
		return null;
	}
	
	public Arena getArena(UUID uu){
		for(Arena a : arenas){
			for(UUID uuid : a.getPlayers()){
				if(uuid == uu){
					return a;
				}
			}
			
			for(UUID uuid : a.getSpectators()){
				if(uuid == uu){
					return a;
				}
			}
			
		}
		return null;
	}
	
	public List<Arena> getAllArenas(){
		return arenas;
	}

}
