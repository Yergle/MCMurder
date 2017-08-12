package me.fudged.murder2.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import me.fudged.murder2.arena.Arena;
import me.fudged.murder2.util.Board;
import me.fudged.murder2.util.MurderConfig;
import me.fudged.murder2.util.Util;

public class GameTimer extends BukkitRunnable {
	
	private Arena a;
	private Board b;
	private int timeLeft;
	
	public GameTimer(Arena arena){
		this.a = arena;
		timeLeft = MurderConfig.GAMETIME;
		
		if(MurderConfig.USEBOARD){
			b = new Board(a);
		}
	}
	
	@Override
	public void run(){
		if(timeLeft == 0){
			a.stop();
			this.cancel();
		}
		
		timeLeft--;
		
		if(MurderConfig.USEBOARD){
			a.setTimeRemaining(timeLeft);
			a.setScoreBoardTime(Util.secondsToString(timeLeft));
			b.tick();
		}
		
	}

}
