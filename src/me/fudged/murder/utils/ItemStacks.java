package me.fudged.murder.utils;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.fudged.murder.Murder;


public class ItemStacks {

	public ItemStack knife(){
		ItemStack knife = new ItemStack(Material.matchMaterial(Murder.getInstance().getConfig().getString("Items.Sword.Type").toUpperCase()));
		ItemMeta im = knife.getItemMeta();
		
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Murder.getInstance().getConfig().getString("Items.Sword.Name")));
		
		im.setLore(Arrays.asList("", ChatColor.RED + "MCMurder"));
		
		knife.setItemMeta(im);
		
		return knife;
	}
	
	public ItemStack pistol(){
		ItemStack pistol = new ItemStack(Material.matchMaterial(Murder.getInstance().getConfig().getString("Items.Gun.Type").toUpperCase()));
		ItemMeta im  = pistol.getItemMeta();
		
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Murder.getInstance().getConfig().getString("Items.Gun.Name")));
		
		im.setLore(Arrays.asList("", ChatColor.RED + "MCMurder"));
		
		pistol.setItemMeta(im);
		
		return pistol;
		
	}
	
	public ItemStack arrow(){
		ItemStack arrow = new ItemStack(Material.matchMaterial(Murder.getInstance().getConfig().getString("Items.Arrow.Type").toUpperCase()));
		ItemMeta im = arrow.getItemMeta();
		
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Murder.getInstance().getConfig().getString("Items.Arrow.Name")));
		
		im.setLore(Arrays.asList("", ChatColor.RED + "MCMurder"));
		
		arrow.setItemMeta(im);
		
		return arrow;
		
	}
	
	public ItemStack scrap(){
		ItemStack scrap = new ItemStack(Material.matchMaterial(Murder.getInstance().getConfig().getString("Items.Scrap.Type").toUpperCase()));
		ItemMeta im = scrap.getItemMeta();
		
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Murder.getInstance().getConfig().getString("Items.Scrap.Name")));
		
		im.setLore(Arrays.asList("", ChatColor.RED + "MCMurder"));
		
		scrap.setItemMeta(im);
		return scrap;
	}
}
