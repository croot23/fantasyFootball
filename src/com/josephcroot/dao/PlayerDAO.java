package com.josephcroot.dao;

import com.josephcroot.entity.Player;

public interface PlayerDAO {
	
	public Player getPlayer(int playerId);
	
	public void deletePlayer(int playerId);
	
	public void addplayer(Player player);

}
