package me.fudged.murder.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.fudged.murder.Arena;
import me.fudged.murder.ArenaManager;
import me.fudged.murder.MessageManager;
import me.fudged.murder.SettingsManager;
import me.fudged.murder.Arena.ArenaState;

public class ArenaEditor {
	
	public ArenaEditor(){
		
	}
	
	public void disable(Player p, String s){

		int id = 0;
		try{ 
			id = Integer.parseInt(s); 
		}catch (Exception e){
			MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return;
		}

		if(ArenaManager.getInstance().getArena(id) == null){
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if(a.getState() == ArenaState.DISABLED){
			MessageManager.getInstance().sendMessage(p, "Arena " + id + " is already disabled!");
			return;
		}

		a.setState(ArenaState.DISABLED);
		MessageManager.getInstance().sendMessage(p, "Arena " + id + " has been disabled!");
		
	}
	
	public void enable(Player p, String s){
		
		int id = 0;
		try{ 
			id = Integer.parseInt(s); 
		}catch (Exception e){
			MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return;
		}
		
		if(ArenaManager.getInstance().getArena(id) == null){
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if(a.getState() != ArenaState.DISABLED){
			MessageManager.getInstance().sendMessage(p, "Arena " + id + " is already enabled!");
			return;
		}

		a.setState(ArenaState.WAITING);
		MessageManager.getInstance().sendMessage(p, "Arena " + id + " has been enabled!");
		
	}
	
	public void createArena(Player p){
		
		int id = 1;

		while(ArenaManager.getInstance().getArena(id) != null) id++;
		
		SettingsManager.getArenas().createConfigurationSection("arenas." + id);
		SettingsManager.getArenas().set("arenas." + id + ".maxPlayers", 12);
		SettingsManager.getArenas().set("arenas." + id + ".playersNeeded", 3);
		SettingsManager.getArenas().set("arenas." + id + ".spawn", getLocationToString(p.getLocation()));
		SettingsManager.getArenas().set("arenas." + id + ".lobby", getLocationToString(p.getLocation()));
		SettingsManager.getArenas().set("arenas." + id + ".spectate", getLocationToString(p.getLocation()));

		ArenaManager.getInstance().setupArenas();
		
		MessageManager.getInstance().sendMessage(p, ChatColor.GRAY + "You have created a new arena!" + ChatColor.RED + " (" + id + ")");
		MessageManager.getInstance().sendMessage(p, "The following spawns have been set to your location: " + ChatColor.RED + "Lobby" + ChatColor.GRAY + ","
				+ ChatColor.RED + " Arena" + ChatColor.GRAY + "," + ChatColor.RED + " Spectator" + ChatColor.GRAY + "!");
		MessageManager.getInstance().sendMessage(p, "Use this command to change any of these locations" + ChatColor.RED + " /murder arena <setarena/setlobby/setspecate> <arenaID>");
		
	}
	
	public void deleteArena(Player p, Integer id){
		Arena a = ArenaManager.getInstance().getArena(id);

		if(a == null) {
			MessageManager.getInstance().sendMessage(p, "This is not a valid arena id!");
			return;
		}

		if(id <= 0) {
			MessageManager.getInstance().sendMessage(p, "Please enter a number greather than 0!"); 
			return;
		}

		if(a.getState() == ArenaState.STARTED) MessageManager.getInstance().sendMessage(p, "Arena " + ChatColor.RED + id + " is currently in a game... Please wait for the game to finish!");

		SettingsManager.getArenas().set("arenas." + id + "", null);

		ArenaManager.getInstance().setupArenas();

		MessageManager.getInstance().sendMessage(p, "Arena " + id + " deleted!");
		
		return;
	}
	
	public void helpDefault(Player p){
		MessageManager.getInstance().sendMessage(p, "/murder help" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows this message");
		MessageManager.getInstance().sendMessage(p, "/murder join help" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Help about joining games");
		MessageManager.getInstance().sendMessage(p, "/murder join random" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Join a random arena");
		MessageManager.getInstance().sendMessage(p, "/murder join <arenaId>" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Join the specified arena");
		MessageManager.getInstance().sendMessage(p, "/murder leave" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Leave your game");
		MessageManager.getInstance().sendMessage(p, "If an admin, use /murder help admin");
	}
	
	public void helpAdmin(Player p){
		MessageManager.getInstance().sendMessage(p, "/murder arena create" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Create an arena");
		MessageManager.getInstance().sendMessage(p, "/murder arena delete <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Delete an arena");
		MessageManager.getInstance().sendMessage(p, "/murder arena start <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Start an arena");
		MessageManager.getInstance().sendMessage(p, "/murder arena stop <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Stop an arena");
		MessageManager.getInstance().sendMessage(p, "/murder arena enable <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Enable an arena");
		MessageManager.getInstance().sendMessage(p, "/murder arena disable <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Disable an arena");
		MessageManager.getInstance().sendMessage(p, "/murder arena <setarena/setlobby/setspectate> <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Set an arena's locations");
		MessageManager.getInstance().sendMessage(p, "/murder arena tp <arena/lobby/spectate> <arenaId>" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Teleport an arena's locations");
		MessageManager.getInstance().sendMessage(p, "/murder arena reload" + ChatColor.DARK_GRAY +  " - " + ChatColor.GRAY + "Reload all arenas");
	}
	
	public void join(Player p, String s){

		for(Arena a : ArenaManager.getInstance().getArenas()){
			if(a.containsPlayer(p)){
				MessageManager.getInstance().sendMessage(p, "You are already in an arena!");
				return;
			}
			
		}

		int id = 0;
		try{ 
			id = Integer.parseInt(s); 
		}catch (Exception e){
			MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return;
		}

		if(ArenaManager.getInstance().getArena(id) == null){
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		if(ArenaManager.getInstance().getArena(id).getState() == ArenaState.STARTED){
			MessageManager.getInstance().sendMessage(p, "Arena already in game!");
			return;
		}
		
		if(ArenaManager.getInstance().getArena(id).getState() == ArenaState.DISABLED){
			MessageManager.getInstance().sendMessage(p, "Arena is disabled!");
			return;
		}
		
		ArenaManager.getInstance().getArena(id).addPlayer(p);

		return;
	}

	public void leave(Player p){
		if(ArenaManager.getInstance().getArena(p) == null){
			MessageManager.getInstance().sendMessage(p, "You are not in a murder game!");
		}else{
			
			MessageManager.getInstance().sendMessage(p, "You have left " + ChatColor.RED + "Arena " + ArenaManager.getInstance().getArena(p).getID() + ChatColor.GRAY + "!");
			
			
			for(Arena a : ArenaManager.getInstance().arenas){
				if(a.containsPlayer(p)){
					a.removePlayer(p);
				}
			}
			
			return;
		}

	}
	
	public void randomJoin(Player p){
		
		if(ArenaManager.getInstance().getArenas().size() == 0){
			MessageManager.getInstance().sendMessage(p, "There are no arenas available!");
			return;
		}
		int randomArena = ThreadLocalRandom.current().nextInt(1, ArenaManager.getInstance().getArenas().size() + 1);
		for(Arena a : ArenaManager.getInstance().getArenas()){
			if(a.containsPlayer(p)){
				MessageManager.getInstance().sendMessage(p, "You are already in an arena!");
				return;
			}
		}
		if(ArenaManager.getInstance().getArena(randomArena) == null){
			MessageManager.getInstance().sendMessage(p, "An error has occured! Please try again");
			return;
		}
		
		if(ArenaManager.getInstance().getArena(randomArena).getState() == ArenaState.STARTED){
			MessageManager.getInstance().sendMessage(p, "Arena already in game!");
			return;
		}
		
		if(ArenaManager.getInstance().getArena(randomArena).getState() == ArenaState.DISABLED){
			MessageManager.getInstance().sendMessage(p, "Arena is disabled!");
			return;
		}

		ArenaManager.getInstance().getArena(randomArena).addPlayer(p);

		return;
	}
	
	public void setLobby(Integer id, Player p){

		if(ArenaManager.getInstance().getArena(id) == null) {
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		ArenaManager.getInstance().setupArenas();

		MessageManager.getInstance().sendMessage(p, "The " + ChatColor.RED + "lobby spawn" + ChatColor.GRAY + 
				" of " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + " has been set to your location!");


		SettingsManager.getArenas().set("arenas." + id + ".lobby", getLocationToString(p.getLocation()));
	}
	
	public void setSpawn(Integer id, Player p){

		if(ArenaManager.getInstance().getArena(id) == null) {
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		ArenaManager.getInstance().setupArenas();

		MessageManager.getInstance().sendMessage(p, "The " + ChatColor.RED + "spawn point" + ChatColor.GRAY + 
				" of " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + " has been set to your location!");

		SettingsManager.getArenas().set("arenas." + id + ".spawn", getLocationToString(p.getLocation()));
	}
	
	public void setSpectateSpawn(Integer id, Player p){

		if(ArenaManager.getInstance().getArena(id) == null) {
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		ArenaManager.getInstance().setupArenas();

		MessageManager.getInstance().sendMessage(p, "The " + ChatColor.RED + "spectator spawn" + ChatColor.GRAY + 
				" of " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + " has been set to your location!");

		SettingsManager.getArenas().set("arenas." + id + ".spectate", getLocationToString(p.getLocation()));
	}
	
	public void teleportArena(Integer id, Player p){

		if(ArenaManager.getInstance().getArena(id) == null) {
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		Location loc = ArenaManager.getInstance().getArena(id).getSpawn();
		
		p.teleport(loc);

		MessageManager.getInstance().sendMessage(p, "You have been teleported to the spawn point of " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + "!");

	}
	
	public void teleportLobby(Integer id, Player p){

		if(ArenaManager.getInstance().getArena(id) == null) {
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		Location loc = ArenaManager.getInstance().getArena(id).getLobby();
		
		p.teleport(loc);

		MessageManager.getInstance().sendMessage(p, "You have been teleported to the lobby of " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + "!");

	}
	
	public void teleportSpectatorSpawn(Integer id, Player p){

		if(ArenaManager.getInstance().getArena(id) == null) {
			MessageManager.getInstance().sendMessage(p, "Please pick a valid arena!");
			return;
		}

		Location loc = ArenaManager.getInstance().getArena(id).getSpectateSpawn();
		
		p.teleport(loc);

		MessageManager.getInstance().sendMessage(p, "You have been teleported to the spectators spawn of " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + "!");

	}
	
	public String getLocationToString(Location l){
		String s = l.getWorld().getName().toString() + ";" + l.getX() + ";" + l.getY() + ";" + l.getZ() + ";" + l.getYaw() + ";" + l.getPitch();
		return s;
	}

	public Location locationFromConfig(String s) {
		String [] locString = s.split(";");
		Location loc = new Location(Bukkit.getWorld(locString[0]), Double.parseDouble(locString[1]), 
				Double.parseDouble(locString[2]), Double.parseDouble(locString[3]));

		loc.setPitch(Float.parseFloat(locString[5]));
		loc.setYaw(Float.parseFloat(locString[4]));

		return loc;
	}
	
}

