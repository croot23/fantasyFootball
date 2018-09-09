package com.josephcroot.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.josephcroot.entity.Player;

@Service
public interface PlayerService {
	
	public Player getPlayer(int playerId) throws JSONException, IOException;
	
	public void deletePlayer(int playerId);

}
