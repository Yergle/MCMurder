package me.fudged.murder2.arena;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.fudged.murder2.MCMurder;
import me.fudged.murder2.runnable.GameTimer;
import me.fudged.murder2.util.MurderConfig;
import me.fudged.murder2.util.Util;

public class Arena {
	
	private String name, scoreboardTime;
	private Set<UUID> players, spectators;
	private Location lobby, arena, spectate;
	private Integer maxPlayers;
	private UUID murderer, detective;
	private State state = State.DISABLED;
	private Integer timeLeft = MurderConfig.GAMETIME;
	private List<Location> scrapSpawn;
	
	public enum State {
		WAITING, COUNTING_DOWN, STARTED, DISABLED;
	}
	
	public Arena(String name, Location loc){
		this.name = name;
		this.players = new HashSet<>();
		this.spectators = new HashSet<>();
		this.lobby = loc;
		this.arena = loc;
		this.spectate = loc;
		this.state = State.WAITING;
		this.maxPlayers = MurderConfig.MAXPLAYERS;
		scrapSpawn = new ArrayList<Location>();
		
		MCMurder.getInstance().getArenaFiles().set("arenas." + name + ".lobby", Util.getLocationToString(loc));
		MCMurder.getInstance().getArenaFiles().set("arenas." + name + ".arena", Util.getLocationToString(loc));
		MCMurder.getInstance().getArenaFiles().set("arenas." + name + ".spectate", Util.getLocationToString(loc));
		
	}
	
	public Arena(String name){
		this.name = name;
		this.players = new HashSet<>();
		this.spectators = new HashSet<>();
		this.lobby = Util.getLocationFromConfig(MCMurder.getInstance().getArenaFiles().get("arenas." + name + ".lobby"));
		this.arena = Util.getLocationFromConfig(MCMurder.getInstance().getArenaFiles().get("arenas." + name + ".arena"));
		this.spectate = Util.getLocationFromConfig(MCMurder.getInstance().getArenaFiles().get("arenas." + name + ".spectate"));
		this.state = State.WAITING;
		this.maxPlayers = MurderConfig.MAXPLAYERS;
		scrapSpawn = new ArrayList<Location>();
	}
	
	public String getName(){
		return name;
	}
	
	public String getScoreboardTime(){
		return scoreboardTime;
	}
	
	public State getState(){
		return state;
	}
	
	public UUID getMurderer(){
		return murderer;  
	}
	
	public UUID getDetective(){
		return detective;
	}
	
	public Integer getTimeRemaining(){
		return timeLeft;
	}
	
	public Set<UUID> getPlayers(){
		return players;
	}
	
	public Set<UUID> getSpectators(){
		return spectators;
	}
	
	public Location getLobbySpawn(){
		return lobby;
	}
	
	public Location getArenaSpawn(){
		return arena;
	}
	
	public Location getSpectatorsSpawn(){
		return spectate;
	}
	
	public Integer getAmountOfPlayers(){
		return players.size();
	}
	
	public Integer getMaxPlayers(){
		return maxPlayers;
	}
	
	public List<Location> getScrapSpawnLocations(){
		return scrapSpawn;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public void setTimeRemaining(Integer time){
		timeLeft = time;
	}
	
	public void setScoreBoardTime(String timeToString){
		scoreboardTime = timeToString;
	}
	
	public void addPlayer(Player player){
		if(players.size() >= MurderConfig.MAXPLAYERS){
			player.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " This arena is currently full");
			return;
		}
		
		if(this.getState() == State.DISABLED){
			player.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " This arena is current disabled");
			return;
		}
		
		if(this.getState() == State.STARTED){
			player.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " This arena is currently in-game");
			return;
		}
		
		player.teleport(lobby);
		players.add(player.getUniqueId());
		
		MCMurder.getInstance().getPlayerData().savePlayerData(player);
		
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());

		if(player.getGameMode() != GameMode.SURVIVAL)
			player.setGameMode(GameMode.SURVIVAL);

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setLevel(0);
		player.setExp(0);
		player.setAllowFlight(false);
		
		if(players.size() == MurderConfig.NEEDEDPLAYERS){
			this.start();
		}

	}
	
	public void removePlayer(Player player){
		
	}
	
	public void stop(){
		
	}
	
	public void start(){
		this.state = State.COUNTING_DOWN;
		
		new GameTimer(this).runTaskTimer(MCMurder.getInstance(), 0L, 20L);
		
	}
	
	public String getRole(UUID uu){
		if(!players.contains(uu)) return null;
		
		if(murderer == uu) return "Murderer";
		
		if(detective == uu) return "Detective";
		
		return "Bystander";
		
	}
}
