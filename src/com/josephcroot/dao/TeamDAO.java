package com.josephcroot.dao;

import java.util.List;

import com.josephcroot.entity.Team;

public interface TeamDAO {
	
	public Team getTeam(int teamId);
	
	public void deleteTeam(int teamId);
	
	public void addTeam(Team team);
	
	public void updateTeam(Team team);
	
	public List<Integer> getTeamIds();
	
}
