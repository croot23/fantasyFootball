package com.josephcroot.dao;

import com.josephcroot.entity.Team;

public interface TeamDAO {
	
	public Team getTeam(int teamId);
	
	public void deleteTeam(int teamId);
	
	public void addTeam(Team team);
	
}
