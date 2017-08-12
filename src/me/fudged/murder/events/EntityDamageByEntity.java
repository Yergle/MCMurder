package me.fudged.murder.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.fudged.murder.Arena;
import me.fudged.murder.ArenaManager;
import me.fudged.murder.MessageManager;
import me.fudged.murder.Murder;

public class EntityDamageByEntity implements Listener{

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		// Player vs Player events
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){

			Player damager = (Player) e.getDamager();
			Player damagee = (Player) e.getEntity();

			// Not in arena
			if(ArenaManager.getInstance().getArena(damager) == null || ArenaManager.getInstance().getArena(damagee) == null){
				return;
			}else
				e.setCancelled(true);

			Arena a = ArenaManager.getInstance().getArena(damager);
			
			damager.getInventory().getItemInHand().setDurability((short) 0);

			if(a.getSpectators().contains(damager.getUniqueId())){
				e.setCancelled(true);
				return;
			}
			
			if(a.getSpectators().contains(damagee.getUniqueId())){
				e.setCancelled(true);
				damagee.setVelocity(damagee.getLocation().getDirection().multiply(.75D).setY(.75D));
				return;
			}


			// Murderer killed player
			if(a.getMurderer() == damager && a.containsPlayer(damagee) && damager.getInventory().getItemInHand().equals(Murder.getInstance().getItems().knife())){

				MessageManager.getInstance().messageArena(a, "A player has been " + ChatColor.RED + "eliminated" + ChatColor.GRAY + "!");

				if(a.getCurrentPlayers() == 1){

					a.stop();

					e.setCancelled(true);
					return;

				}
				if(a.getCurrentPlayers() == 0){

					a.stop();

					e.setCancelled(true);
					return;
				}

				a.addSpectator(damagee);

				e.setCancelled(true);

				return;

			}

		}

		// Arrow vs Player event
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Player){

			Arrow arrow = (Arrow) e.getDamager();

			if(arrow.getShooter() instanceof Player){

				Player shooter = (Player) arrow.getShooter();
				Player target = (Player) e.getEntity();

				// Outside player hitting someone in the arena
				if(ArenaManager.getInstance().getArena(shooter) == null || ArenaManager.getInstance().getArena(target) == null) {
					return;
				}else
					e.setCancelled(true);

				Arena a = ArenaManager.getInstance().getArena(shooter);
				
				shooter.getItemInHand().setDurability((short) 0);
				shooter.updateInventory();

				if(a.getSpectators().contains(shooter.getUniqueId())){

					arrow.remove();

					e.setCancelled(true);
					return;
				}
				
				if(a.getSpectators().contains(target.getUniqueId())){
					shooter.getInventory().addItem(Murder.getInstance().getItems().arrow());
					
					e.setCancelled(true);
					
					target.setVelocity(target.getLocation().getDirection().multiply(.75D).setY(.75D));
					
					arrow.remove();
					return;
				}

				// Detective kill murderer event
				if(a.getMurderer() == target && a.containsPlayer(target)){

					a.stop();
					arrow.remove();
					e.setCancelled(true);
					return;

				}

				// Detective shot an innocent
				if(a.getMurderer() != target && a.containsPlayer(target) && !a.getSpectators().contains(target.getUniqueId())){

					for(UUID p : a.getPlayers()){
						MessageManager.getInstance().sendMessage(Bukkit.getServer().getPlayer(p), "A player has missed his shot!");
					}

					if(a.getCurrentPlayers() == 1){

						a.stop();

						e.setCancelled(true);
						return;

					}
					if(a.getCurrentPlayers() == 0){

						a.stop();

						e.setCancelled(true);
						return;
					}

					a.addSpectator(shooter);

					e.setCancelled(true);

					arrow.remove();

					return;
				}


			}
		}
	}

}

