package com.josephcroot.entity;

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
import javax.persistence.Table;

@Entity
@Table(name = "Team")
public class Team {
	
	@Id
	@Column(name="fantasy_football_id")
	private int fantasyFootballId;
	
	@Column(name="team_name")
	private String teamName;
	
	@Column(name="manager_name")
	private String managerName;
	
	@Column(name="total_points")
	private int totalPoints;
	
	@Column(name="form")
	private double form;
	
	@Column(name="team_value")
	private double teamValue;
	
	@Column(name="total_transfers")
	private int totalTransfers;
	
	@Column(name="bank")
	private double bank;
	
	@Column(name="gameweek_points")
	private int gameweekPoints;
	
	@Column(name="wildcard")
	private boolean wildcard;
	
	@Column(name="bench_boost")
	private boolean benchBoost;
	
	@Column(name="free_hit")
	private boolean freeHit;
	
	@Column(name="triple_captain")
	private boolean tripleCaptain;
	
	@ManyToOne
	@JoinColumn(name="captain_id")
	private Player captain;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, })
	@JoinTable(name="team_player", joinColumns=@JoinColumn(name="team_id"), inverseJoinColumns=@JoinColumn(name="player_id"))
	private Set<Player> players;
	
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
	public int getTotalPoints() {
		return totalPoints;
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

	public int getGameweekPoints() {
		return gameweekPoints;
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
	
}
