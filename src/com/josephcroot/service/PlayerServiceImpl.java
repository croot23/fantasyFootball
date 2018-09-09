package com.josephcroot.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josephcroot.dao.PlayerDAO;
import com.josephcroot.entity.Player;
import com.josephcroot.fantasyfootball.GetJSONFromFantasyFootballAPI;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDAO playerDAO;

	@Transactional
	@Override
	public Player getPlayer(int playerId) throws JSONException, IOException {

		// If Player already exists in the database, get it from there
		Player dbPlayer = playerDAO.getPlayer(playerId);
		if (dbPlayer != null) {
			return dbPlayer;
		}
		// If Player doesn't exist, get it from the fantasy football API
		else {
			// Get JSON for the Player
			Player tmpPlayer = getPlayerFromAPI(playerId);
			playerDAO.addplayer(tmpPlayer);
			return tmpPlayer;
		}
	}

	@Override
	@Transactional
	public void deletePlayer(int playerId) {
		playerDAO.deletePlayer(playerId);
	}
/*
	@Override
	@Transactional
	public void updatePlayer(int playerId) throws JSONException, IOException  {
		Player tmpPlayer = getPlayerFromAPI(playerId);
		playerDAO.updatePlayer(tmpPlayer);
	}*/
	
	public Player getPlayerFromAPI(int playerId) throws JSONException, IOException {
		JSONObject player = GetJSONFromFantasyFootballAPI.getPlayer(playerId);
		// Create Player
		Player tmpPlayer = new Player();
		// Set Player Details
		tmpPlayer.setFantasyFootballId(playerId);
		tmpPlayer.setFirstName(player.getString("first_name"));
		tmpPlayer.setLastName(player.getString("second_name"));
		tmpPlayer.setForm(player.getDouble("form"));
		tmpPlayer.setTotalPoints(player.getInt("total_points"));
		tmpPlayer.setPrice(player.getDouble("now_cost")/10);
		tmpPlayer.setGameweekPoints(player.getInt("event_points"));
		tmpPlayer.setBonusPoints(player.getDouble("bonus"));
		tmpPlayer.setPointsPerGame(player.getDouble("points_per_game"));
		tmpPlayer.setPosition(player.getInt("element_type"));
		tmpPlayer.setWebName(player.getString("web_name"));
		tmpPlayer.setTeam(player.getInt("team"));
		return tmpPlayer;
	}

}
