package me.fudged.murder2.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Util {
	
	public static String getLocationToString(Location l){
		String s = l.getWorld().getName().toString() + ";" + l.getX() + ";" + l.getY() + ";" + l.getZ() + ";" + l.getYaw() + ";" + l.getPitch();
		return s;
	}

	public static Location getLocationFromConfig(String path) {
		String [] locString = path.split(";");
		Location loc = new Location(Bukkit.getWorld(locString[0]), Double.parseDouble(locString[1]), 
				Double.parseDouble(locString[2]), Double.parseDouble(locString[3]));

		loc.setPitch(Float.parseFloat(locString[5]));
		loc.setYaw(Float.parseFloat(locString[4]));

		return loc;
	}
	
	public static String secondsToString(int pTime) {
	    return String.format("%02d:%02d", pTime / 60, pTime % 60);
	}

}
