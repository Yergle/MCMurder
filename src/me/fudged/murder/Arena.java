package me.fudged.murder;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.fudged.murder.utils.Roles;

public class Arena {

	public enum ArenaState { DISABLED, WAITING, COUNTING_DOWN, STARTED; }

	private int id, numPlayers, currentPlayers, neededPlayers;
	private ArrayList<UUID> players, spectators;
	private Location spawnPoint, lobbySpawn, spectate;
	private UUID murderer, detective;
	protected ArenaState state = ArenaState.DISABLED;

	protected Arena(int id) {
		this.id = id;
		this.players = new ArrayList<UUID>();
		this.spectators = new ArrayList<UUID>();
		this.numPlayers = SettingsManager.getArenas().get("arenas." + id + ".maxPlayers");
		this.neededPlayers = SettingsManager.getArenas().get("arenas." + id + ".playersNeeded");

		murderer = null;
		detective = null;

		if (SettingsManager.getArenas().contains("arenas." + id + ".spawn")) {
			String s = SettingsManager.getArenas().get("arenas." + id + ".spawn");
			spawnPoint = Murder.getInstance().getArenaEditor().locationFromConfig(s);
		}
		if (SettingsManager.getArenas().contains("arenas." + id + ".lobby")) {
			String s = SettingsManager.getArenas().get("arenas." + id + ".lobby");
			lobbySpawn = Murder.getInstance().getArenaEditor().locationFromConfig(s);
		}
		if (SettingsManager.getArenas().contains("arenas." + id + ".spectate")) {
			String s = SettingsManager.getArenas().get("arenas." + id + ".spectate");
			spectate = Murder.getInstance().getArenaEditor().locationFromConfig(s);
		}

		state = ArenaState.WAITING;

	}

	public int getID() {
		return id;
	}

	public int getMaxPlayers(){
		return numPlayers;
	}

	public ArenaState getState() {
		return state;
	}

	public int getCurrentPlayers() {
		return currentPlayers;
	}

	public ArrayList<UUID> getPlayers(){
		return players;
	}

	public ArrayList<UUID> getSpectators(){
		return spectators;
	}

	public Location getSpawn(){
		return spawnPoint;
	}

	public Location getLobby(){
		return lobbySpawn;
	}

	public Location getSpectateSpawn(){
		return spectate;
	}

	public Player getMurderer(){
		if(murderer == null) return null;
		if(Bukkit.getServer().getPlayer(murderer) == null) return null;

		return Bukkit.getServer().getPlayer(murderer);
	}

	public Player getDetective(){
		if(detective == null) return null;
		if(Bukkit.getServer().getPlayer(detective) == null) return null;

		return Bukkit.getServer().getPlayer(detective);
	}

	public void setMurderer(Player p){
		murderer = p.getUniqueId();
	}

	public void setDetective(Player p){
		detective = p.getUniqueId();
	}

	public void setState(ArenaState as){
		state = as;
	}

	public void addPlayer(Player p) {
		if (spawnPoint == null) {
			MessageManager.getInstance().sendMessage(p, "Spawn point has not been set yet!");
			return;
		}

		if (lobbySpawn == null) {
			MessageManager.getInstance().sendMessage(p, "Lobby has not been set yet!");
			return;
		}
		if(spectate == null){
			MessageManager.getInstance().sendMessage(p, "Spectator's Spawn has not been set yet!");
			return;
		}
		
		p.setHealth(20D);
		p.setFoodLevel(20);
		
		for(Arena a : ArenaManager.getInstance().arenas){
			if(a.containsPlayer(p)){
				MessageManager.getInstance().sendMessage(p, "You are already in a game");
				return;
			}
		}

		if (currentPlayers >= numPlayers) {
			MessageManager.getInstance().messageArena(this, p.getName() + " is now " + ChatColor.RED + "spectating" + ChatColor.GRAY + "!");

			PlayerData.getInstance().savePlayerData(p);

			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));

			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setLevel(0);
			p.setExp(0);

			p.teleport(spectate);

			spectators.add(p.getUniqueId());

			p.setAllowFlight(true);

			p.setFlying(true);

			return;
		}

		// Safety check, should never happen
		if(PlayerData.getInstance().hasPlayerData(p))
			PlayerData.getInstance().removePlayerData(p);

		PlayerData.getInstance().savePlayerData(p);
		MessageManager.getInstance().messageArena(this, p.getName() + " has " + ChatColor.RED + "joined" + ChatColor.GRAY + " the arena!");

		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());

		if(p.getGameMode() != GameMode.SURVIVAL)
			p.setGameMode(GameMode.SURVIVAL);

		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setLevel(0);
		p.setExp(0);

		p.teleport(lobbySpawn);

		players.add(p.getUniqueId());

		MessageManager.getInstance().sendMessage(p, "You have joined " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + "!");

		p.setAllowFlight(false);

		currentPlayers++;

		if(currentPlayers == neededPlayers){
			if(this.state != ArenaState.COUNTING_DOWN) start();
		}

	}

	public void addSpectator(Player p){

		players.remove(p.getUniqueId());
		spectators.add(p.getUniqueId());

		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));

		for(ItemStack i : p.getInventory().getContents()){

			if(i != null) p.getInventory().remove(i);

		}

		for(ItemStack i : p.getInventory().getArmorContents()){

			if(i != null) p.getInventory().remove(i);
		}

		p.setAllowFlight(true);

		currentPlayers--;

		if (currentPlayers == 1) {
			stop();
		} else if (currentPlayers == 0) {
			stop();
		}

	}

	public void removePlayer(Player p) {
		if(PlayerData.getInstance().hasPlayerData(p)){
			PlayerData.getInstance().restorePlayerData(p);
			PlayerData.getInstance().removePlayerData(p);
		}

		for(PotionEffect effect : p.getActivePotionEffects()){
			p.removePotionEffect(effect.getType());
		}
		
		p.setFlying(false);
		p.setAllowFlight(false);

		if(players.contains(p))
			MessageManager.getInstance().messageArena(this, p.getName() + " has " + ChatColor.RED + "left" + ChatColor.GRAY + " the arena!");

		if(players.contains(p.getUniqueId())) {
			players.remove(p.getUniqueId());
			currentPlayers--;
		}

		if(spectators.contains(p.getUniqueId())) spectators.remove(p.getUniqueId());


		if (currentPlayers == 1 && this.state == ArenaState.STARTED) {
			if(this.getMurderer() == p){
				MessageManager.getInstance().messageArena(this, "The murderer " + ChatColor.RED + "(" + p.getName() + ")" + ChatColor.GRAY + " has disconnected!");
				stop();
			}
			if(this.getDetective() == p){
				MessageManager.getInstance().messageArena(this, "The detective " + ChatColor.RED + "(" + p.getName() + ")" + ChatColor.GRAY + " has disconnected!");
				stop();
			}
			if(this.getDetective() != p && this.getMurderer() != p){
				MessageManager.getInstance().messageArena(this, p.getName() + " has " + ChatColor.RED + "disconnected" + ChatColor.GRAY + "!");
				stop();
			}
		} 
		if (currentPlayers == 0 && this.state == ArenaState.STARTED) {
			stop();
		}

	}

	public void start() {
		this.state = ArenaState.COUNTING_DOWN;

		new Countdown(
				Murder.getPlugin().getConfig().getInt("Numbers.GameCountdown"),
				"Game starting in %t seconds!",
				this,
				30,
				20,
				10,
				5,
				4,
				3,
				2,
				1
				).runTaskTimer(Murder.getPlugin(), 0, 20);

	}
	public void startGold() {
		new Gold(this).runTaskTimer(Murder.getPlugin(), 0, 20);
	}

	public void stop() {
		
		MessageManager.getInstance().broadcast("Arena " + id + " has " + ChatColor.RED + "ended" + ChatColor.GRAY +"!");

		for(UUID uuid : players){

			PlayerData.getInstance().restorePlayerData(Bukkit.getServer().getPlayer(uuid));
			PlayerData.getInstance().removePlayerData(Bukkit.getServer().getPlayer(uuid));

		}
		for(UUID uuid : spectators){

			PlayerData.getInstance().restorePlayerData(Bukkit.getServer().getPlayer(uuid));
			PlayerData.getInstance().removePlayerData(Bukkit.getServer().getPlayer(uuid));

			for (PotionEffect effect : Bukkit.getServer().getPlayer(uuid).getActivePotionEffects())
				Bukkit.getServer().getPlayer(uuid).removePotionEffect(effect.getType());

		}

		spectators.clear();

		players.clear();
		currentPlayers = 0;

		this.state = ArenaState.WAITING;
	}

	public boolean containsPlayer(Player p) {
		for(Arena a : ArenaManager.getInstance().getArenas()){

			if(a.getPlayers().contains(p.getUniqueId()) || a.getSpectators().contains(p.getUniqueId())) return true;
		}
		return false;
	}

	private class Countdown extends BukkitRunnable {

		private int timer;
		private String msg;
		private Arena a;
		private ArrayList<Integer> countingNums;

		public Countdown(int start, String msg, Arena a, int... countingNums) {
			this.timer = start;
			this.msg = msg;
			this.a = a;
			this.countingNums = new ArrayList<Integer>();
			for (int i : countingNums) this.countingNums.add(i);
		}

		public void run() {

			if(a.getState() == ArenaState.WAITING || a.getState() == ArenaState.DISABLED || a.getCurrentPlayers() == 0){
				cancel();
				return;
			}

			if(timer > 0){
				for(UUID uuid : players){
					Player p = Bukkit.getServer().getPlayer(uuid);
					p.setLevel(timer);
					p.setExp(0);
				}
			}

			if(currentPlayers == 0){
				cancel();
				state = ArenaState.WAITING;
				return;
			}

			if (timer == 0) {

				if(neededPlayers > currentPlayers){

					for(UUID uuid : players){

						PlayerData.getInstance().restorePlayerData(Bukkit.getServer().getPlayer(uuid));
						PlayerData.getInstance().removePlayerData(Bukkit.getServer().getPlayer(uuid));

					}

					MessageManager.getInstance().messageArena(a, "There are not enough players to start!");

					a.state = ArenaState.WAITING;


					cancel();
					return;
				}
				for (UUID uuid : players) {
					Bukkit.getServer().getPlayer(uuid).teleport(spawnPoint);
					Bukkit.getServer().getPlayer(uuid).setExp(0);
					Bukkit.getServer().getPlayer(uuid).setLevel(0);

				}

				MessageManager.getInstance().messageArena(a, "The game has started! Roles will be decided in " + 
						Murder.getPlugin().getConfig().getInt("Numbers.RoleCountdown") + " seconds!");

				a.state = ArenaState.STARTED;

			}
			if(timer < 0){
				for(UUID uuid : players){
					Player p = Bukkit.getServer().getPlayer(uuid);
					p.setLevel(Math.abs(timer));
				}
			}
			int x = Murder.getPlugin().getConfig().getInt("Numbers.RoleCountdown");
			if(timer == -x ){
				Roles.getInstance().chooseRoles(id);

				a.state = ArenaState.STARTED;

				startGold();

				cancel();
			}

			if (countingNums.contains(timer)) {
				MessageManager.getInstance().messageArena(a, msg.replace("%t", timer + ""));
			}

			timer--;
		}
	}

	private class Gold extends BukkitRunnable{

		private int countdown;
		private Arena a;

		public Gold(Arena a){
			this.countdown = Murder.getPlugin().getConfig().getInt("Numbers.Scrap");
			this.a = a;
		}

		@Override
		public void run() {

			if(countdown == 0){
				if(a.getState() != ArenaState.STARTED){
					cancel();
					return;
				}

				countdown = Murder.getPlugin().getConfig().getInt("Numbers.Scrap");


				Player p = Bukkit.getServer().getPlayer(players.get(new Random().nextInt(players.size())));

				p.getInventory().addItem(Murder.getInstance().getItems().scrap());
				
				if(p == a.getMurderer() || p == a.getDetective() || p.getInventory().contains(Material.BOW)) 
					return;

				if(p.getInventory().contains(Material.GOLD_INGOT, Murder.getPlugin().getConfig().getInt("Numbers.RequiredScrap"))){
					
					p.getInventory().clear();

					if (p.getInventory().getHeldItemSlot() == 0 || p.getInventory().getHeldItemSlot() == 1) {

						p.getInventory().setItem(p.getInventory().getHeldItemSlot() + 1, Murder.getInstance().getItems().pistol());
						p.getInventory().setItem(p.getInventory().getHeldItemSlot() + 2, Murder.getInstance().getItems().arrow());

					} else {

						p.getInventory().setItem(p.getInventory().getHeldItemSlot() - 2, Murder.getInstance().getItems().pistol());
						p.getInventory().setItem(p.getInventory().getHeldItemSlot() - 1, Murder.getInstance().getItems().arrow());

					}

					MessageManager.getInstance().sendMessage(p, "You have collected " + ChatColor.RED + Murder.getPlugin().getConfig().getInt("Numbers.RequiredScrap")
							+ " Scrap" + ChatColor.GRAY + "! You now have a " + ChatColor.RED + "Pistol" + "!" );

				}else
					MessageManager.getInstance().sendMessage(p, "You have received " + ChatColor.RED + "1 Scrap" + ChatColor.GRAY + "!");


			}

			countdown--;

		}
	}

}