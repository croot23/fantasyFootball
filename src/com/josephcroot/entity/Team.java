package com.josephcroot.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

	@Id
	@Column(name = "fantasy_football_id")
	private int fantasyFootballId;

	@Column(name = "team_name")
	private String teamName;

	@Column(name = "manager_name")
	private String managerName;

	@Column(name = "total_points")
	private int totalPoints;

	@Column(name = "form")
	private double form;

	@Column(name = "team_value")
	private double teamValue;

	@Column(name = "total_transfers")
	private int totalTransfers;

	@Column(name = "bank")
	private double bank;

	@Column(name = "gameweek_points")
	private int gameweekPoints;

	@Column(name = "wildcard")
	private boolean wildcard;

	@Column(name = "bench_boost")
	private boolean benchBoost;

	@Column(name = "free_hit")
	private boolean freeHit;

	@Column(name = "triple_captain")
	private boolean tripleCaptain;
	
	@Column(name = "overall_rank")
	private int overallRank;

	@ManyToOne
	@JoinColumn(name = "captain_id")
	private Player captain;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, })
	@JoinTable(name = "team_player", 
	joinColumns = @JoinColumn(name = "team_id"), 
	inverseJoinColumns = @JoinColumn(name = "player_id"))
	private Set<Player> players;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, })
	@JoinTable(name = "team_substitutes", 
	joinColumns = @JoinColumn(name = "team_id"), 
	inverseJoinColumns = @JoinColumn(name = "player_id"))
	private Set<Player> substitutes;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, })
	@JoinTable(name="team_transfers", 
	joinColumns=@JoinColumn(name="team_id"),
	inverseJoinColumns=@JoinColumn(name="player_out"))
	@MapKeyJoinColumn(name="player_in")
	private Map<Player, Player> weeklyTransfers;

	public Set<Player> getAllPlayers() {
		Set<Player> allPlayers = new HashSet<Player>(getPlayers());
		allPlayers.addAll(getSubstitutes());
		return allPlayers;
	}
	
	public int getFirstElevenGameweekPoints() {
		int firstElevenGameweekPoints = 0;
		Set<Player> firstEleven = new HashSet<Player>(getPlayers());
		for (Player player : firstEleven) {
			firstElevenGameweekPoints += player.getGameweekPoints();
			//If it's the captain the points are doubled remember!
			if (player == captain)
				firstElevenGameweekPoints += player.getGameweekPoints();
		}
		return firstElevenGameweekPoints;
	}
	
	public int getGameweekPoints() {
		int firstElevenPoints = getFirstElevenGameweekPoints();
		if (gameweekPoints != firstElevenPoints)
			return firstElevenPoints;
		else
			return gameweekPoints;
	}
	
	public int getCurrentFantasyGameweekPoints() {
		return gameweekPoints;
	}
	

	public int getTotalPoints() {
		int firstElevenPoints = getFirstElevenGameweekPoints();
		int gameweekPoints = getCurrentFantasyGameweekPoints();
		//if gameweek points haven't updated, return combined first 11 player points (player points are updated in real time)
		if (gameweekPoints == firstElevenPoints) {
			return totalPoints;
		}
		else {
			return totalPoints + (firstElevenPoints - gameweekPoints);
		}
	}
	
	public int getSubstitutePoints() {
		int substitutePoints = 0;
		Set<Player> substitutes = new HashSet<Player>(getSubstitutes());
		for (Player player : substitutes) {
			substitutePoints += player.getGameweekPoints();
		}
		return substitutePoints;
	}

	public Set<Player> getSubstitutes() {
		return substitutes;
	}

	public void setSubstitutes(Set<Player> substitutes) {
		this.substitutes = substitutes;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public int getFantasyFootballId() {
		return fantasyFootballId;
	}

	public void setFantasyFootballId(int fantasyFootballId) {
		this.fantasyFootballId = fantasyFootballId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public double getForm() {
		return form;
	}

	public void setForm(double form) {
		this.form = form;
	}

	public double getTeamValue() {
		return teamValue;
	}

	public void setTeamValue(double teamValue) {
		this.teamValue = teamValue;
	}

	public int getTotalTransfers() {
		return totalTransfers;
	}

	public void setTotalTransfers(int totalTransfers) {
		this.totalTransfers = totalTransfers;
	}

	public double getBank() {
		return bank;
	}

	public void setBank(double d) {
		this.bank = d;
	}

	public void setGameweekPoints(int gameweekPoints) {
		this.gameweekPoints = gameweekPoints;
	}

	public boolean isWildcard() {
		return wildcard;
	}

	public void setWildcard(boolean wildcard) {
		this.wildcard = wildcard;
	}

	public boolean isBenchBoost() {
		return benchBoost;
	}

	public void setBenchBoost(boolean benchBoost) {
		this.benchBoost = benchBoost;
	}

	public boolean isFreeHit() {
		return freeHit;
	}

	public void setFreeHit(boolean freeHit) {
		this.freeHit = freeHit;
	}

	public boolean isTripleCaptain() {
		return tripleCaptain;
	}

	public void setTripleCaptain(boolean tripleCaptain) {
		this.tripleCaptain = tripleCaptain;
	}

	public Player getCaptain() {
		return captain;
	}

	public void setCaptain(Player captain) {
		this.captain = captain;
	}
	
	public int getOverallRank() {
		return overallRank;
	}

	public void setOverallRank(int overallRank) {
		this.overallRank = overallRank;
	}
	
	
	public Map<Player, Player> getWeeklyTransfers() {
		return weeklyTransfers;
	}

	public void setWeeklyTransfers(Map<Player, Player> weeklySubstitutes) {
		this.weeklyTransfers = weeklySubstitutes;
	}

}
