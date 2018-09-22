package com.josephcroot.dao;

import java.util.List;

import com.josephcroot.entity.Player;

public interface PlayerDAO {
	
	public Player getPlayer(int playerId);
	
	public void deletePlayer(int playerId);
	
	public void addplayer(Player player);

	public void updatePlayer(Player tmpPlayer);

	public List<Integer> getPlayerIds();

}
