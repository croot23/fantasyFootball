package com.josephcroot.dao;

import com.josephcroot.entity.League;

public interface LeagueDAO {
	
	public League getLeague(int leagueId);
	
	public void deleteLeague(int leagueId);
	
	public void addLeague(League league);
	
}
