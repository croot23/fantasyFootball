package com.josephcroot.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.josephcroot.dao.PlayerDAO;
import com.josephcroot.entity.Player;
import com.josephcroot.fantasyfootball.GetJSONFromFantasyFootballAPI;

@Service
public class PlayerServiceImpl implements PlayerService {

	final static Logger log = Logger.getLogger(PlayerServiceImpl.class);

	@Autowired
	private PlayerDAO playerDAO;

	@Transactional
	@Scheduled(fixedDelay = 60000)
	public void scheduleFixedDelayTask() throws JSONException, IOException {
		log.info("Updating Players");
		try {
			updatePlayerInfo();
		} catch (JSONException e) {
			log.error(e);
		}
	}

	@Transactional
	@Override
	public void updatePlayerInfo() throws JSONException, IOException {
		List<Integer> result = new ArrayList<Integer>(playerDAO.getPlayerIds());
		for (int playerId : result) {
			Player updatedPlayer = playerDAO.getPlayer(playerId);
			updatePlayer(updatedPlayer);
			playerDAO.updatePlayer(updatedPlayer);
		}
	}

	@Transactional
	@Override
	public Player getPlayer(int playerId) throws JSONException, IOException {
		Player dbPlayer = playerDAO.getPlayer(playerId);
		if (dbPlayer != null) {
			return dbPlayer;
		} else {
			Player tmpPlayer = new Player();
			tmpPlayer.setFantasyFootballId(playerId);
			updatePlayer(tmpPlayer);
			playerDAO.addplayer(tmpPlayer);
			return tmpPlayer;
		}
	}

	public void updatePlayer(Player player) throws JSONException, IOException {
		JSONObject playerInfo = GetJSONFromFantasyFootballAPI.getPlayer(player.getFantasyFootballId());
		player.setFirstName(playerInfo.getString("first_name"));
		player.setLastName(playerInfo.getString("second_name"));
		player.setForm(playerInfo.getDouble("form"));
		player.setTotalPoints(playerInfo.getInt("total_points"));
		player.setPrice(playerInfo.getDouble("now_cost") / 10);
		player.setGameweekPoints(playerInfo.getInt("event_points"));
		player.setBonusPoints(playerInfo.getInt("bonus"));
		player.setPointsPerGame(playerInfo.getDouble("points_per_game"));
		player.setPosition(playerInfo.getInt("element_type"));
		player.setWebName(playerInfo.getString("web_name"));
		player.setTeam(playerInfo.getInt("team_code"));
		player.setDidNotPlay(didNotPlay(player.getFantasyFootballId()));
	}
	
	public static boolean didNotPlay(int id) throws JSONException, IOException {
		JSONArray playerGameweekInfo = GetJSONFromFantasyFootballAPI.getPlayerGameweekInfo(id);
		for (int i = 0; i < playerGameweekInfo.length(); i++) {
			JSONObject currentFixture = (JSONObject) playerGameweekInfo.getJSONObject(i).get("fixture");
			JSONObject explain = (JSONObject) playerGameweekInfo.getJSONObject(i).get("explain");
			JSONObject minutes = (JSONObject) explain.get("minutes");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			Date now = new Date();
			try {
				Date kickoff = df.parse(currentFixture.get("kickoff_time").toString());
				if ((now.getTime() - kickoff.getTime()) > 7200000 && minutes.getInt("value") == 0) {
					return true;
				}
			} catch (ParseException e) {
				log.error(e);
			}
		}
		return false;
	}

	@Override
	@Transactional
	public void deletePlayer(int playerId) {
		playerDAO.deletePlayer(playerId);
	}

}
