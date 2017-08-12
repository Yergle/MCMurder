package me.fudged.murder;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {
	private static SettingsManager arenas = new SettingsManager("arenas");


	private File file;
	private FileConfiguration config;
	
	public static SettingsManager getArenas() {
		return arenas;
	}


	private SettingsManager(String fileName) {
		System.out.println(Murder.getPlugin());

		if (!Murder.getPlugin().getDataFolder().exists()) Murder.getPlugin().getDataFolder().mkdir();

		file = new File(Murder.getPlugin().getDataFolder(), fileName + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(file);
	}

	public void set(String path, Object value) {
		config.set(path, value);
		try { config.save(file); }
		catch (Exception e) { e.printStackTrace(); }
	}

	public ConfigurationSection createConfigurationSection(String path) {
		ConfigurationSection cs = config.createSection(path);
		try { config.save(file); }
		catch (Exception e) { e.printStackTrace(); }
		return cs;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		return (T) config.get(path);
	}

	public boolean contains(String path) {
		return config.contains(path);
	}
}
