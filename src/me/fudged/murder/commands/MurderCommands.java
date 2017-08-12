package me.fudged.murder.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.fudged.murder.Arena;
import me.fudged.murder.ArenaManager;
import me.fudged.murder.MessageManager;
import me.fudged.murder.Murder;
import me.fudged.murder.Arena.ArenaState;

public class MurderCommands implements CommandExecutor {

	private static MurderCommands instance = new MurderCommands();

	public static MurderCommands getInstance() {
		return instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.getInstance().sendMessage(sender, "You must be a player to do this!");
			return true;
		}

		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("murder")){

			//Create arena.
			if(args.length == 2 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("create")){
				if(p.hasPermission("murder.arena.create")){
					Murder.getInstance().getArenaEditor().createArena(p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to create an arena (murder.arena.create)");
					return true;
				}
			}

			// Delete arena.
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("delete")){
				if(p.hasPermission("murder.arena.delete")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[2]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}
					Murder.getInstance().getArenaEditor().deleteArena(p, id);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to delete arenas (murder.arena.delete)");
					return true;
				}
			}

			// Force start an arena.
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("start")){
				if(p.hasPermission("murder.arena.start")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[2]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}
					if(ArenaManager.getInstance().getArena(id) == null ){
						MessageManager.getInstance().sendMessage(p, "No arena found with the id of " + id);
						return true;
					}
					if(ArenaManager.getInstance().getArena(id).getState() != ArenaState.WAITING){
						MessageManager.getInstance().sendMessage(p, "Arena is already in game!");
						return true;
					}
					MessageManager.getInstance().sendMessage(p, "You have started " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + "!");
					ArenaManager.getInstance().getArena(id).start();
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to start games (murder.arena.start)");
					return true;
				}
			}

			// Force stop an arena.
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("stop")){
				if(p.hasPermission("murder.arena.stop")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[2]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}
					if(ArenaManager.getInstance().getArena(id) == null ){
						MessageManager.getInstance().sendMessage(p, "No arena found with the id of " + id);
						return true;
					}
					MessageManager.getInstance().sendMessage(p, "You have stopped " + ChatColor.RED + "Arena " + id + ChatColor.GRAY + "!");
					ArenaManager.getInstance().getArena(id).stop();
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to stop arenas (murder.arena.stop)");
					return true;
				}
			}

			//Enable arena
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("enable")){
				if(p.hasPermission("murder.arena.enable")){
					Murder.getInstance().getArenaEditor().enable(p, args[2]);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to enable arenas (murder.arena.enable)");
					return true;
				}
			}

			//Disable arena
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("disable")){
				if(p.hasPermission("murder.arena.disable")){
					Murder.getInstance().getArenaEditor().disable(p, args[2]);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to disable arenas (murder.arena.disable)");
					return true;
				}
			}

			// Set spawn
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("setarena")){
				if(p.hasPermission("murder.arena.setspawn")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[2]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}

					Murder.getInstance().getArenaEditor().setSpawn(id, p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to do this (murder.arena.setspawn)");
					return true;	
				}
			}

			// Set lobby
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("setlobby")){
				if(p.hasPermission("murder.arena.setspawn")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[2]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}

					Murder.getInstance().getArenaEditor().setLobby(id, p);
					return true;
				} else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to do this (murder.arena.setspawn)");
					return true;
				}
			}

			// Set spectate
			if(args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("setspectate")){
				if(p.hasPermission("murder.arena.setspawn")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[2]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}

					Murder.getInstance().getArenaEditor().setSpectateSpawn(id, p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to do this (murder.arena.setspawn)");
					return true;
				}
			}
			// Teleport to an arenas spawn
			if(args.length == 4 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("tp") && args[2].equalsIgnoreCase("arena")){
				if(p.hasPermission("murder.arena.tp")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[3]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}

					Murder.getInstance().getArenaEditor().teleportArena(id, p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to do this (murder.arena.tp)");
					return true;
				}
			}
			// Teleport to an arenas lobby
			if(args.length == 4 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("tp") && args[2].equalsIgnoreCase("lobby")){
				if(p.hasPermission("murder.arena.tp")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[3]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}

					Murder.getInstance().getArenaEditor().teleportLobby(id, p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to do this (murder.arena.tp)");
					return true;
				}
			}
			// Teleport to an arenas spectate
			if(args.length == 4 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("tp") && args[2].equalsIgnoreCase("spectate")){
				if(p.hasPermission("murder.arena.tp")){
					int id = 0;
					try{ 
						id = Integer.parseInt(args[3]); 
					}catch (Exception e){
						MessageManager.getInstance().sendMessage(p, "Please enter a valid number!"); return true;
					}

					Murder.getInstance().getArenaEditor().teleportSpectatorSpawn(id, p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to do this (murder.arena.tp)");
					return true;
				}
			}

			// Reload arenas
			if(args.length == 2 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("reload")){
				if(p.hasPermission("murder.arena.reload")){
					MessageManager.getInstance().sendMessage(p, "All arenas have been reloaded!");
					for(Arena a : ArenaManager.getInstance().arenas){
						a.stop();
					}
					ArenaManager.getInstance().setupArenas();
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to reload MCMurder (murder.arena.reload)");
					return true;
				}
			}

			// Join help.
			if((args.length == 1 && args[0].equalsIgnoreCase("join")) || (args.length == 2 && args[0].equalsIgnoreCase("join") && args[1].equalsIgnoreCase("help"))){
				MessageManager.getInstance().sendMessage(p, ChatColor.GRAY + "/murder join help" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows this message");
				MessageManager.getInstance().sendMessage(p, ChatColor.GRAY + "/murder join random" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Join a random arena");
				MessageManager.getInstance().sendMessage(p, ChatColor.GRAY + "/murder join <arenaId>" +  ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Join the specified arena");
				MessageManager.getInstance().sendMessage(p, ChatColor.GRAY + "Possible arena ids:" + ChatColor.GRAY + ArenaManager.getInstance().getArenasIDs());
				return true;
			}

			// Random join.
			if(args.length == 2 && args[0].equalsIgnoreCase("join") && args[1].equalsIgnoreCase("random")){
				if(p.hasPermission("murder.arena.join")){
					Murder.getInstance().getArenaEditor().randomJoin(p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to join an arena (murder.arena.join)");
					return true;
				}
			}
			// Join by ID
			if(args.length == 2 && args[0].equalsIgnoreCase("join")){
				if(p.hasPermission("murder.arena.join")){
					Murder.getInstance().getArenaEditor().join(p, args[1]);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to join an arena (murder.arena.join)");
					return true;
				}
			}
			// Leave
			if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
				if(p.hasPermission("murder.arena.leave")){
					Murder.getInstance().getArenaEditor().leave(p);
					return true;
				}else{
					MessageManager.getInstance().sendMessage(p, "You do not have permission to leave this arnea (murder.arena.leave)");
					return true;
				}
			}
			// Help
			if(args.length == 1 && args[0].equalsIgnoreCase("help")){
				Murder.getInstance().getArenaEditor().helpDefault(p);
				return true;
			}
			// Help Admin
			if(args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("admin")){
				Murder.getInstance().getArenaEditor().helpAdmin(p);
				return true;
			}

			if(args.length == 0){
				Murder.getInstance().getArenaEditor().helpDefault(p);
			}

		}

		//Unknown command
		if(args.length >= 1){
			MessageManager.getInstance().sendMessage(p, "Error: Make sure you are using the command correctly and have the correct permission!");
			return true;
		}
		return true;
	}

}
