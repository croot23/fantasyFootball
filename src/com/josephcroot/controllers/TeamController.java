package com.josephcroot.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.josephcroot.entity.League;
import com.josephcroot.entity.Team;
import com.josephcroot.service.LeagueService;

@Controller
@PropertySource("classpath:app.properties")
public class TeamController {
	
	@Value("${league}")
	private int league;
	
	@Autowired
	private LeagueService leagueService;
	
	@RequestMapping("/getTeamInfoAsJSON")
	public @ResponseBody String showFormForUpdate() throws JsonGenerationException, JsonMappingException, IOException {
		League newLeague = leagueService.getLeague(league);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(newLeague.getTeams());
	}
	
	@RequestMapping("/getTeamPoints")
	public @ResponseBody String showFormForUpdate2() throws JsonGenerationException, JsonMappingException, IOException {
		League newLeague = leagueService.getLeague(league);
		ArrayList<Team> teams = new ArrayList<Team>(newLeague.getTeams());
		JSONArray array = new JSONArray();
		for (Team team : teams) {
			JSONObject json = new JSONObject();
			json.put("label", team.getTeamName());
			json.put("y", team.getTotalPoints());
			array.put(json);
		}
		return array.toString();
	}

}
