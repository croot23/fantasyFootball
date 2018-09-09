package com.josephcroot.service;

import com.josephcroot.entity.Team;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
	
	public Team getTeam(int teamId);
	
	public void deleteTeam(int teamId);
	
}
