package me.fudged.murder2.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.fudged.murder2.arena.Arena;
import net.md_5.bungee.api.ChatColor;

public class Board {
	
	private Scoreboard board;
	private Objective obj;
	private Arena a;
	private Score role, timeLeft, playersLeft, arena, spacer, spacer1, spacer2;
	
	public Board(Arena a){
		this.a = a;
		
		board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective(a.getName(), "dummy");
		obj.setDisplayName(MurderConfig.BOARDHEADER);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		spacer = obj.getScore(ChatColor.RED + "");
		spacer1 = obj.getScore(ChatColor.RED + " ");
		spacer2 = obj.getScore(ChatColor.RED + "  ");
		
		this.loadScoreboard();
	}
	
	public void loadScoreboard(){
		for(UUID uu : a.getPlayers()){
			role = obj.getScore(MurderConfig.PRIMARY + "Role: " + MurderConfig.SECONDARY + a.getRole(uu));
			timeLeft = obj.getScore(MurderConfig.PRIMARY + "Time Remaining: " + MurderConfig.SECONDARY + a.getScoreboardTime());
			arena = obj.getScore(MurderConfig.PRIMARY + "Arena: " + MurderConfig.SECONDARY + a.getName());
			playersLeft = obj.getScore(MurderConfig.PRIMARY + "Players Alive: " + MurderConfig.SECONDARY + a.getPlayers().size());
			
			role.setScore(1);
			spacer.setScore(2);
			arena.setScore(3);
			spacer1.setScore(4);
			playersLeft.setScore(5);
			spacer2.setScore(6);
			timeLeft.setScore(7);
			
			Bukkit.getServer().getPlayer(uu).setScoreboard(board);
		}
	}
	
	public void tick(){
		for(UUID uu : a.getPlayers()){
			role = obj.getScore(MurderConfig.PRIMARY + "Role: " + MurderConfig.SECONDARY + a.getRole(uu));
			timeLeft = obj.getScore(MurderConfig.PRIMARY + "Time Remaining: " + MurderConfig.SECONDARY + a.getScoreboardTime());
			arena = obj.getScore(MurderConfig.PRIMARY + "Arena: " + MurderConfig.SECONDARY + a.getName());
			playersLeft = obj.getScore(MurderConfig.PRIMARY + "Players Alive: " + MurderConfig.SECONDARY + a.getPlayers().size());
			
			for(String s : board.getEntries()){
				board.resetScores(s);
			}
			
			role.setScore(1);
			spacer.setScore(2);
			arena.setScore(3);
			spacer1.setScore(4);
			playersLeft.setScore(5);
			spacer2.setScore(6);
			timeLeft.setScore(7);
			
			Bukkit.getServer().getPlayer(uu).setScoreboard(board);
		}
	}
	

}
