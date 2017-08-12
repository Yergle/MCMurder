package me.fudged.murder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerData {
	
	public PlayerData(){ } 
	
	private static PlayerData instance = new PlayerData();
	
	public List<UUID> playerData = new ArrayList<UUID>();	

	public static PlayerData getInstance() {
		return instance;
	}
	public HashMap<UUID, ItemStack[]> armor = new HashMap<UUID, ItemStack[]>();
	public HashMap<UUID, ItemStack[]> inventory = new HashMap<UUID, ItemStack[]>();
	public HashMap<UUID, Location> location = new HashMap<UUID, Location>();
	public HashMap<UUID, Float> xp = new HashMap<UUID, Float>();
	public HashMap<UUID, Integer> level = new HashMap<UUID, Integer>();
	public HashMap<UUID, GameMode> gameMode = new HashMap<UUID, GameMode>();
	
	public void savePlayerData(Player p){
		
		armor.put(p.getUniqueId(), p.getInventory().getArmorContents());
		inventory.put(p.getUniqueId(), p.getInventory().getContents());
		location.put(p.getUniqueId(), p.getLocation());
		xp.put(p.getUniqueId(), p.getExp());
		level.put(p.getUniqueId(), p.getLevel());
		gameMode.put(p.getUniqueId(), p.getGameMode());
		playerData.add(p.getUniqueId());
		
	}
	
	public void restorePlayerData(Player p){
		
		p.getInventory().setArmorContents(armor.get(p.getUniqueId()));
		p.getInventory().setContents(inventory.get(p.getUniqueId()));
		p.teleport(location.get(p.getUniqueId()));
		p.setExp(xp.get(p.getUniqueId()));
		p.setLevel(level.get(p.getUniqueId()));
		p.setGameMode(gameMode.get(p.getUniqueId()));
		
	}
	
	public void removePlayerData(Player p){
		
		armor.remove(p.getUniqueId());
		inventory.remove(p.getUniqueId());
		location.remove(p.getUniqueId());
		xp.remove(p.getUniqueId());
		level.remove(p.getUniqueId());
		gameMode.remove(p.getUniqueId());
		playerData.remove(p.getUniqueId());
		
	}
	
	public boolean hasPlayerData(Player p){
		if(armor.containsKey(p.getUniqueId()) || location.containsKey(p.getUniqueId()) || gameMode.containsKey(p.getUniqueId())) return true;
		
		return false;
	}
	
}
