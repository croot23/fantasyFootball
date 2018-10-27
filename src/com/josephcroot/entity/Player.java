package com.josephcroot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player {

	@Id
	@Column(name = "fantasy_football_id")
	private int fantasyFootballId;

	@Column(name = "web_name")
	private String webName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "total_points")
	private int totalPoints;

	@Column(name = "gameweek_points")
	private int gameweekPoints;

	@Column(name = "form")
	private double form;

	@Column(name = "price")
	private double price;

	@Column(name = "injured")
	private boolean injured;

	@Column(name = "points_per_game")
	private double pointsPerGame;

	@Column(name = "bonus_points")
	private int bonusPoints;

	@Column(name = "position")
	private double position;

	@Column(name = "team")
	private int team;
	
	public double getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public double getPointsPerGame() {
		return pointsPerGame;
	}

	public void setPointsPerGame(double pointsPerGame) {
		this.pointsPerGame = pointsPerGame;
	}

	public double getBonusPoints() {
		return bonusPoints;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getForm() {
		return form;
	}

	public void setForm(double form) {
		this.form = form;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isInjured() {
		return injured;
	}

	public void setInjured(boolean injured) {
		this.injured = injured;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public void setGameweekPoints(int gameweekPoints) {
		this.gameweekPoints = gameweekPoints;
	}

	public int getFantasyFootballId() {
		return fantasyFootballId;
	}

	public void setFantasyFootballId(int fantasyFootballId) {
		this.fantasyFootballId = fantasyFootballId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public int getGameweekPoints() {
		return gameweekPoints;
	}

}
