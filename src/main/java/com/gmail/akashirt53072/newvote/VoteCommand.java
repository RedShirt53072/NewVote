package com.gmail.akashirt53072.newvote;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;




public class VoteCommand implements CommandExecutor{
	
	private Main plugin;
	
	public VoteCommand(Main plugin) {
	    this.plugin = plugin;
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
            Player player = (Player) sender;
            
            if(args.length < 3) {
            	player.sendMessage("[error]手動入力しないでください。");
            	return true;
            }
            
            if(!(args[0].equals("vote") || args[0].equals("check"))) {
            	player.sendMessage("[error]手動入力しないでください。");
            	return true;
            }
            
            Integer i = TextManager.toNumber(player, args[1]);
            if(i == null) {
            	player.sendMessage("[error]手動入力しないでください。");
                return true;
            }
            Vote v = plugin.getVote(i);
            if(v == null) {
            	player.sendMessage("[error]無効な投票です。");
                return true;	
            }
            if(args[0].equals("vote")) {
            	v.vote(player, args[2]);
                	
            }else if(args[0].equals("check")) {
            	v.check(player, args[2]);
                
            }
            
            
            
            
            return true;
		}
        return false;
    }
	
}
