package me.fudged.murder.utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.fudged.murder.Arena;
import me.fudged.murder.ArenaManager;
import me.fudged.murder.MessageManager;
import me.fudged.murder.Murder;

public class Roles {

	private static Roles instance = new Roles();

	public static Roles getInstance() {
		return instance;
	}

	public Player getMurderer(Arena a) {
		return a.getMurderer();
	}

	public Player getDetective(Arena a) {
		return a.getDetective();
	}

	public void chooseRoles(Integer id) {
		List<UUID> players = ArenaManager.getInstance().getArena(id).getPlayers();

		if (players.isEmpty())
			return;
		Player m = Bukkit.getServer().getPlayer(players.get(new Random().nextInt(players.size())));

		MessageManager.getInstance().sendMessage(m, "You are the " + ChatColor.RED + "murderer" + ChatColor.GRAY + "!");
		ArenaManager.getInstance().getArena(id).setMurderer(m);

		if (m.getInventory().getHeldItemSlot() == 0) {
			
			m.getInventory().setItem(m.getInventory().getHeldItemSlot() + 1, Murder.getInstance().getItems().knife());
			
		} else
			
			m.getInventory().setItem(m.getInventory().getHeldItemSlot() - 1, Murder.getInstance().getItems().knife());

		players.remove(m.getUniqueId());
		
		Player d = Bukkit.getServer().getPlayer(players.get(new Random().nextInt(players.size())));

		MessageManager.getInstance().sendMessage(d, "You are the " + ChatColor.RED + "detective" + ChatColor.GRAY + "!");
		ArenaManager.getInstance().getArena(id).setDetective(d);

		if (d.getInventory().getHeldItemSlot() == 0 || d.getInventory().getHeldItemSlot() == 1) {
			
			d.getInventory().setItem(d.getInventory().getHeldItemSlot() + 1, Murder.getInstance().getItems().pistol());
			d.getInventory().setItem(d.getInventory().getHeldItemSlot() + 2, Murder.getInstance().getItems().arrow());
			
		} else {
			
			d.getInventory().setItem(d.getInventory().getHeldItemSlot() - 2, Murder.getInstance().getItems().pistol());
			d.getInventory().setItem(d.getInventory().getHeldItemSlot() - 1, Murder.getInstance().getItems().arrow());
			
		}

		players.remove(d.getUniqueId());

		for (UUID uuid : players) {
			MessageManager.getInstance().sendMessage(Bukkit.getServer().getPlayer(uuid),
					"You are a " + ChatColor.RED + "bystander" + ChatColor.GRAY + "!");
		}
		
		players.add(m.getUniqueId());
		players.add(d.getUniqueId());

		return;

	}

}
