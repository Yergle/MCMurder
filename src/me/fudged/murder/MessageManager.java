package me.fudged.murder;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

	private MessageManager() { }

	private static MessageManager instance = new MessageManager();

	public static MessageManager getInstance() {
		return instance;
	}

	private String prefix = ChatColor.RED + "" + ChatColor.BOLD + "MCMurder" + ChatColor.GRAY + " ";

	public void sendMessage(CommandSender sender, String... messages) {
		for (String msg : messages) {
			sender.sendMessage(prefix + msg);
		}
	}
	
	public void broadcast(String... messages) {
		for (Arena a : ArenaManager.getInstance().arenas) {

			for(UUID p : a.getPlayers()){
				for (String msg : messages) {
					Bukkit.getServer().getPlayer(p).sendMessage(prefix + msg);
				}
			}

			for(UUID p : a.getSpectators()){
				for(String msg : messages){
					Bukkit.getServer().getPlayer(p).sendMessage(prefix + msg);
				}
			}

		}
	}

	public void messageArena(Arena a, String... messages){
		for(UUID uuid : a.getPlayers()){
			for(String msg : messages){
				Bukkit.getServer().getPlayer(uuid).sendMessage(prefix + msg);
			}
		}
	}

}
