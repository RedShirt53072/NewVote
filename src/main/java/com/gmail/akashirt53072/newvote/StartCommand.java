package com.gmail.akashirt53072.newvote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;




public class StartCommand implements TabExecutor{
	
	private Main plugin;
	
	public StartCommand(Main plugin) {
	    this.plugin = plugin;
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
            Player player = (Player) sender;
            for(Vote v : plugin.getVotes()) {
            	if(v.isStarterMatch(player)) {
            		player.sendMessage("[error]投票の開催は同時には1人1つしか行えません。"); ;
                    return true;
            	}
            }
            
            if(args.length < 4) {
            	player.sendMessage("[error]必要な項目をスペースで区切って入力してください。"); ;
            	return true;
            }
            Integer i = TextManager.toNumber(player, args[0]);
            if(i == null) {
                return true;
            }
            ArrayList<String> data = new ArrayList<String>();
            for(int j = 1; args.length > j;j++) {
            	data.add(args[j]);
            }
            
            new Vote(plugin,player,data,i);
            
            return true;
		}
        return false;
    }
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		if (sender instanceof Player) {
			List<String> tab = new ArrayList<String>();
            if(args.length == 1) {
            	tab.add("整数で秒数");
            }else if(args.length == 2) {
            	tab.add("質問");
            }else if(args.length >= 3) {
    			tab.add("投票候補" + (args.length - 1));
    		}

            return tab;
		}
		return null;
	}
}
