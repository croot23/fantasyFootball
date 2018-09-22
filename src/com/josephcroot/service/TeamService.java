package com.josephcroot.service;

import com.josephcroot.entity.Team;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
	
	public Team getTeam(int teamId)  throws JSONException, IOException;
	
	public void deleteTeam(int teamId);
	
	public void updateTeamInfo() throws JSONException, IOException;
	
	public void scheduleFixedDelayTask() throws JSONException, IOException;
	
}
