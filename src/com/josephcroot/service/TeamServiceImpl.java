package com.josephcroot.service;

import java.io.IOException;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josephcroot.dao.TeamDAO;
import com.josephcroot.entity.Player;
import com.josephcroot.entity.Team;
import com.josephcroot.fantasyfootball.GetJSONFromFantasyFootballAPI;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private PlayerService playerService;

	@Transactional
	@Override
	public Team getTeam(int teamId) {

		Team dbTeam = teamDAO.getTeam(teamId);
		if (dbTeam != null) {
			return dbTeam;
		} else {
			Team newTeam = new Team();
			try {

				JSONObject teamInfo = GetJSONFromFantasyFootballAPI.getTeamInfo(teamId);
				newTeam.setFantasyFootballId(teamId);
				newTeam.setManagerName(
						teamInfo.getString("player_first_name") + " " + teamInfo.getString("player_last_name"));
				newTeam.setTotalPoints(teamInfo.getInt("summary_overall_points"));
				newTeam.setTeamName(teamInfo.getString("name"));
				newTeam.setTeamValue(teamInfo.getDouble("value"));
				newTeam.setBank(teamInfo.getDouble("bank"));
				newTeam.setTotalTransfers(teamInfo.getInt("total_transfers"));
				newTeam.setFreeHit(false);
				JSONArray chipInfo = GetJSONFromFantasyFootballAPI.getTeamChipsInfo(teamId);
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

			HashSet<Player> players = new HashSet<Player>();
			try {
				JSONArray playersJSON = GetJSONFromFantasyFootballAPI.getTeamPlayers(teamId);
				for (int i = 0; i < playersJSON.length(); i++) {
					// Create Player
					JSONObject currentPlayer = playersJSON.getJSONObject(i);
					Player player = playerService.getPlayer(currentPlayer.getInt("element"));
					players.add(player);
				}
				newTeam.setPlayers(players);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// save Team
			teamDAO.addTeam(newTeam);
			return newTeam;
		}
	}

	@Override
	public void deleteTeam(int teamId) {
		teamDAO.deleteTeam(teamId);
	}

}
