package me.fudged.murder;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ArenaManager {
	private ArenaManager() { }

	private static ArenaManager instance = new ArenaManager();

	public static ArenaManager getInstance() {
		return instance;
	}

	public ArrayList<Arena> arenas = new ArrayList<Arena>();
	

	public void setupArenas() {
		if (SettingsManager.getArenas().<ConfigurationSection>get("arenas") == null) SettingsManager.getArenas().createConfigurationSection("arenas");

		arenas.clear();

		for (String key : SettingsManager.getArenas().<ConfigurationSection>get("arenas").getKeys(false)) {
			arenas.add(new Arena(Integer.parseInt(key)));
		}
	}

	public ArrayList<Arena> getArenas() {
		return arenas;
	}

	public Arena getArena(int id) {
		for (Arena a : arenas) {
			if (a.getID() == id) return a;
		}
		return null;
	}

	public Arena getArena(Player p) {
		for (Arena a : arenas) {
			if (a.containsPlayer(p)) return a;
		}
		return null;
	}
	
	public String getArenasIDs(){
		StringBuilder buffer = new StringBuilder();
		
		for(int x = 1; x <= arenas.size(); x++){
			buffer.append(" " + x + ",");
		}
		
		String s = StringUtils.chop(buffer.toString());
		
		return s;
	}
}
