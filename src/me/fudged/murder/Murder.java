package me.fudged.murder;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.fudged.murder.commands.MurderCommands;
import me.fudged.murder.events.BlockBreak;
import me.fudged.murder.events.BlockPlace;
import me.fudged.murder.events.EntityDamageByEntity;
import me.fudged.murder.events.FoodLevelChange;
import me.fudged.murder.events.PlayerDropItem;
import me.fudged.murder.events.PlayerPickupItem;
import me.fudged.murder.events.PlayerQuit;
import me.fudged.murder.utils.ArenaEditor;
import me.fudged.murder.utils.ItemStacks;

public class Murder extends JavaPlugin {
	
	private ArenaEditor arenaEditor;
	private static Murder instance;
	private ItemStacks items;
	
	@Override
	public void onEnable(){
		instance = this;
		arenaEditor = new ArenaEditor();
		items = new ItemStacks();
		saveDefaultConfig();
		
		registerEvents();
		registerCommands();
		
		ArenaManager.getInstance().setupArenas();
	}
	
	@Override
	public void onDisable(){
		
		for(Arena a : ArenaManager.getInstance().arenas) a.stop();
		
		for(UUID u : PlayerData.getInstance().playerData){
			PlayerData.getInstance().restorePlayerData(Bukkit.getServer().getPlayer(u));
			PlayerData.getInstance().removePlayerData(Bukkit.getServer().getPlayer(u));
		}
		
	}

	public static Plugin getPlugin(){
		return Bukkit.getServer().getPluginManager().getPlugin("MCMurder");
	}
	public static Murder getInstance(){
		return instance;
	}
	
	public ArenaEditor getArenaEditor(){
		return arenaEditor;
	}
	
	public ItemStacks getItems(){
		return items;
	}
	
	public void registerEvents(){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new FoodLevelChange(), this);
		pm.registerEvents(new PlayerDropItem(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerPickupItem(), this);
	}
	
	public void registerCommands(){
		getCommand("murder").setExecutor(new MurderCommands());
	}
	
}
