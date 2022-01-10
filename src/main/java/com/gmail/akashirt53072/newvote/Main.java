package com.gmail.akashirt53072.newvote;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	private HashMap<Integer,Vote> votes = new HashMap<Integer,Vote>();
	private int id = 0;
	
	@Override
	public void onEnable() {
		this.getCommand("vote").setExecutor(new StartCommand(this));
		this.getCommand("clickvote").setExecutor(new VoteCommand(this));
		
	}
	
	public ArrayList<Vote> getVotes() {
		ArrayList<Vote> vs = new ArrayList<Vote>();
		for(Integer key : this.votes.keySet()) {
			vs.add(votes.get(key));
		}
		return vs;
	}
	
	public void removeVote(int ID) {
		votes.remove(ID);
	}
	
	public Vote getVote(int ID) {
		return votes.get(ID);
	}
	
	public int addVote(Vote v) {
		id ++;
		votes.put(id,v);
		return id;
	}
}
