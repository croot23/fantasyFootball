package com.josephcroot.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josephcroot.dao.LeagueDAO;
import com.josephcroot.entity.League;
import com.josephcroot.entity.Team;
import com.josephcroot.fantasyfootball.GetJSONFromFantasyFootballAPI;

@Service
public class LeagueServiceImpl implements LeagueService {

	@Autowired
	private LeagueDAO leagueDAO;
	@Autowired
	private TeamService teamService;
	
	@Transactional
	@Override
	public League getLeague(int leagueId) {

		League dbLeague = leagueDAO.getLeague(leagueId);
		if (dbLeague != null) {
			return dbLeague;
		} else {
			League newLeague = new League();
			try {
				JSONObject leagueInfo = GetJSONFromFantasyFootballAPI.getLeagueInfo(leagueId);
				System.out.println(leagueInfo);
				newLeague.setName(leagueInfo.getString("name"));
				newLeague.setFantasyFootballId(leagueId);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Set<Team> teams = new HashSet<>();
			try {
				JSONArray teamsJSON = GetJSONFromFantasyFootballAPI.getTeams(leagueId);
				for (int i = 0; i < teamsJSON.length(); i++) {
					// Create Team
					JSONObject currentTeam = teamsJSON.getJSONObject(i);
					System.out.println(currentTeam);
					System.out.println(currentTeam.getInt("id"));
					Team team = teamService.getTeam(currentTeam.getInt("entry"));
					teams.add(team);
				}
				newLeague.setTeams(teams);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// save Team
			leagueDAO.addLeague(newLeague);
			return newLeague;
		}
	}

	@Override
	public void deleteLeague(int leagueId) {
		leagueDAO.deleteLeague(leagueId);
	}

}
