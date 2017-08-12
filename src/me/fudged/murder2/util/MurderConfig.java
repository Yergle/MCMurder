package me.fudged.murder2.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.fudged.murder2.MCMurder;

public class MurderConfig {
	
	private FileConfiguration config;
	
	public static int MAXPLAYERS = 8;
	public static int NEEDEDPLAYERS = 3;
	public static int GAMETIME = 300;
	public static int COUNTDOWNTIME = 15;
	public static String PREFIX = ChatColor.RED + "" + ChatColor.BOLD + "MCMurder";
	public static String BOARDHEADER = ChatColor.RED + "MCMurder";
	public static ChatColor PRIMARY = ChatColor.DARK_RED;
	public static ChatColor SECONDARY = ChatColor.GRAY;
	public static boolean USEBOARD = true;
	
	public MurderConfig(MCMurder murder){
		this.config = murder.getConfig();
		
		this.loadConfig();
	}
	
	public void loadConfig(){
		if(this.config.contains("MAX_PLAYERS")){
			MAXPLAYERS = this.config.getInt("MAX_PLAYERS");
		}
		if(this.config.contains("NEEDED_PLAYERS_TO_START")){
			MAXPLAYERS = this.config.getInt("NEEDED_PLAYERS_TO_START");
		}
		if(this.config.contains("GAMETIME")){
			GAMETIME = this.config.getInt("GAMETIME");
		}
		if(this.config.contains("COUNTDOWNTIME")){
			GAMETIME = this.config.getInt("COUNTDOWNTIME");
		}
		if(this.config.contains("PREFIX")){
			PREFIX = ChatColor.translateAlternateColorCodes('&', this.config.getString("PREFIX"));
		}
		if(this.config.contains("SCOREBOARD_HEADER")){
			BOARDHEADER = ChatColor.translateAlternateColorCodes('&', this.config.getString("SCOREBOARD_HEADER"));
		}
		if(this.config.contains("PRIMARY_COLOR")){
			PRIMARY = ChatColor.valueOf(this.config.getString("PRIMARY_COLOR").toUpperCase());
		}
		if(this.config.contains("SECONDARY_COLOR")){
			SECONDARY = ChatColor.valueOf(this.config.getString("SECONDARY_COLOR").toUpperCase());
		}
		if(this.config.contains("USE_SCOREBOARD")){
			USEBOARD = this.config.getBoolean("USE_SCOREBOARD");
		}
	}

}
