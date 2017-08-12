package me.fudged.murder2.storage;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.fudged.murder2.MCMurder;

public class MurderFiles {

	public MurderFiles(String name){

		if(!MCMurder.getInstance().getDataFolder().exists()){
			MCMurder.getInstance().getDataFolder().mkdir();
		}

		file = new File(MCMurder.getInstance().getDataFolder(), name + ".yml");

		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);

	}

	private File file;
	private FileConfiguration config;

	public void set(String path, Object obj){
		config.set(path, obj);
		try{
			config.save(file);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String path){
		return (T) config.get(path);
	}

	public void creatSection(String sectionName){
		config.createSection(sectionName);
		try{
			config.save(file);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public ConfigurationSection getSection(String sectionName){
		return config.getConfigurationSection(sectionName);
	}
	
	public List<String> getStringList(String path){
		return config.getStringList(path);
	}

}
