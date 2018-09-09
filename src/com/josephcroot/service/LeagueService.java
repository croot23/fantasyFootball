package com.josephcroot.service;

import org.springframework.stereotype.Service;

import com.josephcroot.entity.League;

@Service
public interface LeagueService {
	
	public League getLeague(int leagueId);
	
	public void deleteLeague(int leagueId);

	
}
