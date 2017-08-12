package me.fudged.murder2.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import me.fudged.murder2.arena.Arena;
import me.fudged.murder2.arena.Arena.State;
import me.fudged.murder2.util.MurderConfig;

public class Countdown extends BukkitRunnable {
	
	private Arena a;
	private int time;
	
	public Countdown(Arena arena){
		this.a = arena;
		this.time = MurderConfig.COUNTDOWNTIME; 
	}
	
	public void run(){
		if(time == 0){
			a.setState(State.STARTED);
			a.start();
			this.cancel();
		}
		// Add some form of way to show the countdown time
		time--;
	}

}
