package com.gmail.akashirt53072.newvote;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;



public class Vote {
	private Player starter;
	private ArrayList<Player> voters = new ArrayList<Player>();
	private Main plugin;
	private HashMap<String,Integer> data = new HashMap<String,Integer>();
	private int voteid;
	
	public Vote(Main plugin,Player p,ArrayList<String> data,int timer) {
	    this.plugin = plugin;
	    this.starter = p;
	    for(int i = 1;data.size() > i;i++) {
		    this.data.put(data.get(i), 0);
	    }
	    if(timer > 120) {
	    	timer = 120;
	    }
	    BukkitRunnable e1 = new BukkitRunnable() {
            @Override
            public void run() {
            	end();
        	}
		};
		e1.runTaskLater(plugin, timer * 20);
	    
		//登録
		voteid = plugin.addVote(this);
	    
		//チャット
		ArrayList<TextComponent> allText = new ArrayList<TextComponent>();
	    for(String key : this.data.keySet()) {
	    
	    	TextComponent general = new TextComponent();
			general.addExtra(key);
	    	general.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/clickvote check " + voteid  + " " + key));
			allText.add(general);
	    }
	    
	    for(Player pl : starter.getWorld().getPlayers()) {
			pl.sendMessage(starter.getName() + "さんが投票を始めました。");
			pl.sendMessage("以下の候補をクリックで1人1回投票ができます。");
			pl.sendMessage("投票できるのは今から" + ChatColor.AQUA + timer + "秒間です。");
			pl.sendMessage("質問：" + data.get(0));
			for(TextComponent t : allText){
				pl.spigot().sendMessage(t);
			}
		}
	}
	
	public boolean isStarterMatch(Player p) {
		return starter.equals(p);
	}
	
	public void check(Player voter,String vote) {
		if(voters.contains(voter)) {
			SoundManager.sendCancel(voter);
			return;
		}
		SoundManager.sendClick(voter);
		
		voter.sendMessage("[確認]本当に" + vote + "に投票しますか?");
		TextComponent general = new TextComponent();
		general.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/clickvote vote " + voteid  + " " + vote));
		general.addExtra(ChatColor.GREEN + "  はい  ");
		
		
		
		voter.spigot().sendMessage(general);
    	
	}
	
	public void vote(Player voter,String vote) {
		if(voters.contains(voter)) {
			SoundManager.sendCancel(voter);
			return;
		}
		voters.add(voter);
		if(data.containsKey(vote)){
			data.replace(vote, data.get(vote) + 1);
		}else {
			voter.sendMessage("[error]無効な投票先です。");	
		}
		voter.sendMessage(vote + "に投票しました。");
		SoundManager.sendSelect(voter);
		
	}
	
	private void end() {
		//得票計算
		ArrayList<String> winner = new ArrayList<String>();
		int now = 0;
		ArrayList<String> allText = new ArrayList<String>();
		for(String key : data.keySet()) {
			int value = data.get(key);
			allText.add(key + "：" + ChatColor.GOLD + value + "票");
			if(value == now) {
				winner.add(key);
				continue;
			}
			if(value > now) {
				winner.clear();
				winner.add(key);
				now = value;
			}
		}
		
		String winText = winner.get(0);
		for(int i = 1;winner.size() > i;i++) {
			winText = winText + "と" + winner.get(i);
		}
		
		for(Player p : starter.getWorld().getPlayers()) {
			SoundManager.sendSelect(p);
			p.sendMessage(starter.getName() + "さんの投票が締め切られました。");
			p.sendMessage("投票数は" + ChatColor.GOLD + voters.size() + "票" + ChatColor.WHITE + "で、内訳は以下の通りです。");
			for(String t : allText){
				p.sendMessage(t);
			}
			p.sendMessage("最も得票を集めたのは" + ChatColor.GOLD + now + "票の" + ChatColor.LIGHT_PURPLE + winText + ChatColor.WHITE + "でした。");
			
		}
		plugin.removeVote(voteid);
	}
	
}
