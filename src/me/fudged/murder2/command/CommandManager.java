package me.fudged.murder2.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.fudged.murder2.util.MurderConfig;

public class CommandManager implements CommandExecutor {

	public ArrayList<MurderCommand> cmds = new ArrayList<MurderCommand>();
	
	public CommandManager(){
		cmds.add(new Create());
		cmds.add(new Join());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " This commands can only be sent in game");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("murder")){
			if(args.length == 0){
				for(MurderCommand mc : cmds){
					p.sendMessage(MurderConfig.PREFIX + MurderConfig.PRIMARY + " /murder " + mc.getUsage() + " : " + MurderConfig.SECONDARY + mc.getMessage());
				}
				
				return true;
			}
			
			MurderCommand c = getCommand(args[0]);
			
			if(c == null){
				
				return true;
			}
			
			Vector<String> a = new Vector<String>(Arrays.asList(args));
			a.remove(0);
			args = a.toArray(new String[a.size()]);
			
			c.onCommand(p, args);
			return true;
		}
		return false;
	}

	public MurderCommand getCommand(String name){
		 for(MurderCommand cmd : cmds){
			 if(cmd.getClass().getSimpleName().equalsIgnoreCase(name)) return cmd;
		 }
		 return null;
	}
	
}
