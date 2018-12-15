package com.josephcroot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
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

	final static Logger log = Logger.getLogger(TeamServiceImpl.class);

	@Autowired
	private TeamDAO teamDAO;

	@Autowired
	private PlayerService playerService;

	@Transactional
	@Scheduled(fixedDelay = 60000)
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

			// General team info
			JSONObject teamInfo = GetJSONFromFantasyFootballAPI.getTeamInfo(newTeam.getFantasyFootballId());
			newTeam.setTeamName(teamInfo.getString("name"));
			newTeam.setTeamValue(teamInfo.getDouble("value"));
			newTeam.setBank(teamInfo.getDouble("bank"));
			newTeam.setTotalTransfers(teamInfo.getInt("total_transfers"));
			newTeam.setGameweekPoints(teamInfo.getInt("summary_event_points"));
			newTeam.setOverallRank(teamInfo.getInt("summary_overall_rank"));
			newTeam.setManagerName(
					teamInfo.getString("player_first_name") + " " + teamInfo.getString("player_last_name"));

			// Chips info
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

			// T-1 Total Points (we add current live points to last weeks score for live
			// totals)
			JSONArray totalPointsInfo = GetJSONFromFantasyFootballAPI.getTeamPoints(newTeam.getFantasyFootballId());
			for (int i = 0; i < totalPointsInfo.length(); i++) {
				if (i == totalPointsInfo.length() - 2) {
					JSONObject lastGameweek = totalPointsInfo.getJSONObject(i);
					newTeam.setTotalPoints(lastGameweek.getInt("total_points"));
				}
			}

			// Players and substitutes info
			Set<Player> players = new HashSet<>();
			Set<Player> substitutes = new HashSet<>();
			int defenders = 0;
			int forwards = 0;
			JSONArray playersJSON = GetJSONFromFantasyFootballAPI.getTeamPlayers(newTeam.getFantasyFootballId());
			for (int i = 0; i < playersJSON.length(); i++) {
				// Create Player
				JSONObject currentPlayer = playersJSON.getJSONObject(i);
				Player player = playerService.getPlayer(currentPlayer.getInt("element"));
				if (currentPlayer.getBoolean("is_captain")) {
					newTeam.setCaptain(player);
				}
				if (currentPlayer.getBoolean("is_vice_captain")) {
					newTeam.setViceCaptain(player);
				}
				if (currentPlayer.getInt("position") < 12) {
					players.add(player);
					if (player.getPosition() == 2)
						defenders++;
					else if (player.getPosition() == 4)
						forwards++;
				} else {
					substitutes.add(player);
				}
			}

			// Automatic substitutes
			for (Player player : players) {
				if (player.didNotPlay() == true) {

					// Goalkeepers
					if (player.getPosition() == 1) {
						for (Player substitute : substitutes) {
							if (substitute.getPosition() == 1 && substitute.didNotPlay() == false) {
								players.add(substitute);
								substitutes.remove(substitute);
							}
						}
					}

					// Defenders
					if (player.getPosition() == 2) {
						for (Player substitute : substitutes) {
							if (substitute.didNotPlay() == false) {
								if (defenders > 3) {
									players.add(substitute);
									substitutes.remove(substitute);
									defenders = (substitute.getPosition() == 2) ? defenders + 1 : defenders - 1;
									break;
								} else {
									if (substitute.getPosition() == 2 && substitute.didNotPlay() == false) {
										players.add(substitute);
										substitutes.remove(substitute);
										break;
									}
								}
							}
						}
					}

					// Midfielders
					if (player.getPosition() == 3) {
						for (Player substitute : substitutes) {
							if (substitute.didNotPlay() == false) {
								players.add(substitute);
								substitutes.remove(substitute);
								break;
							}
						}

						// Forwards
						if (player.getPosition() == 4) {
							for (Player substitute : substitutes) {
								if (substitute.didNotPlay() == false) {
									if (forwards > 1) {
										players.add(substitute);
										substitutes.remove(substitute);
										forwards = (substitute.getPosition() == 4) ? forwards + 1 : forwards - 1;
										break;
									} else {
										if (substitute.getPosition() == 4 && substitute.didNotPlay() == false) {
											players.add(substitute);
											substitutes.remove(substitute);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			newTeam.setPlayers(players);
			newTeam.setSubstitutes(substitutes);

			// Transfer info
			Map<Player, Player> transfers = new HashMap<Player, Player>();
			JSONArray transfersJSON = GetJSONFromFantasyFootballAPI.getTransfers(newTeam.getFantasyFootballId());
			for (int i = 0; i < transfersJSON.length(); i++) {
				JSONObject currentTransfer = transfersJSON.getJSONObject(i);
				if (currentTransfer.getInt("event") == GetJSONFromFantasyFootballAPI.getGameweek()) {
					Player playerOut = playerService.getPlayer(currentTransfer.getInt("element_out"));
					Player playerIn = playerService.getPlayer(currentTransfer.getInt("element_in"));
					transfers.put(playerOut, playerIn);
				}
			}
			newTeam.setWeeklyTransfers(transfers);

			// Transfer hits
			JSONObject teamHits = GetJSONFromFantasyFootballAPI.getTeamHits(newTeam.getFantasyFootballId());
			newTeam.setTransferHits(teamHits.getInt("event_transfers_cost"));

		} catch (JSONException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}

	@Transactional
	@Override
	public void deleteTeam(int teamId) {
		teamDAO.deleteTeam(teamId);
	}

}
