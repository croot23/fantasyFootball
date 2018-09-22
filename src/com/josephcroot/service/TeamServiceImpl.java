package com.josephcroot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.josephcroot.dao.TeamDAO;
import com.josephcroot.entity.Player;
import com.josephcroot.entity.Team;
import com.josephcroot.fantasyfootball.GetJSONFromFantasyFootballAPI;

@Component
@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamDAO teamDAO;

	@Autowired
	private PlayerService playerService;
	
	@Transactional
	@Scheduled(fixedDelay = 30000)
	public void scheduleFixedDelayTask() throws JSONException, IOException {
		updateTeamInfo();
	}
	
	@Transactional
	@Override
	public void updateTeamInfo() throws JSONException, IOException {
		List<Integer> result = new ArrayList<Integer>(teamDAO.getTeamIds());
		for (int teamId : result) {
			Team updatedTeam = teamDAO.getTeam(teamId);
			updateTeam(updatedTeam);
			teamDAO.updateTeam(updatedTeam);
		}
	}

	@Transactional
	@Override
	public Team getTeam(int teamId) throws JSONException, IOException {

		Team dbTeam = teamDAO.getTeam(teamId);
		if (dbTeam != null) {
			return dbTeam;
		} else {
			Team newTeam = new Team();
			newTeam.setFantasyFootballId(teamId);
			updateTeam(newTeam);
			teamDAO.addTeam(newTeam);
			return newTeam;
		}
	}
	
	public void updateTeam(Team newTeam) throws JSONException, IOException {
		try {

			JSONObject teamInfo = GetJSONFromFantasyFootballAPI.getTeamInfo(newTeam.getFantasyFootballId());
			newTeam.setTotalPoints(teamInfo.getInt("summary_overall_points"));
			newTeam.setTeamName(teamInfo.getString("name"));
			newTeam.setTeamValue(teamInfo.getDouble("value"));
			newTeam.setBank(teamInfo.getDouble("bank"));
			newTeam.setTotalTransfers(teamInfo.getInt("total_transfers"));
			newTeam.setFreeHit(false);
			newTeam.setManagerName(
					teamInfo.getString("player_first_name") + " " + teamInfo.getString("player_last_name"));
			JSONArray chipInfo = GetJSONFromFantasyFootballAPI.getTeamChipsInfo(newTeam.getFantasyFootballId());
			for (int i = 0; i < chipInfo.length(); i++) {
				JSONObject currentChip = chipInfo.getJSONObject(i);
				if (currentChip.getString("name").equals("freehit")) {
					newTeam.setFreeHit(true);
				}
				if (currentChip.getString("name").equals("wildcard")) {
					newTeam.setWildcard(true);
				}
				if (currentChip.getString("name").equals("3xc")) {
					newTeam.setTripleCaptain(true);
				}
				if (currentChip.getString("name").equals("bboost")) {
					newTeam.setBenchBoost(true);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Player> players = new HashSet<>();
		try {
			JSONArray playersJSON = GetJSONFromFantasyFootballAPI.getTeamPlayers(newTeam.getFantasyFootballId());
			for (int i = 0; i < playersJSON.length(); i++) {
				// Create Player
				JSONObject currentPlayer = playersJSON.getJSONObject(i);
				Player player = playerService.getPlayer(currentPlayer.getInt("element"));
				if (currentPlayer.getBoolean("is_captain")) {
					newTeam.setCaptain(currentPlayer.getInt("element"));
				}
				players.add(player);
			}
			newTeam.setPlayers(players);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTeam(int teamId) {
		teamDAO.deleteTeam(teamId);
	}

}
