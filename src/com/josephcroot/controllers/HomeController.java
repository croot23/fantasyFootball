package com.josephcroot.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.josephcroot.entity.League;
import com.josephcroot.entity.Player;
import com.josephcroot.entity.Team;
import com.josephcroot.service.LeagueService;
import com.josephcroot.service.PlayerService;

@Controller
//@PropertySource("classpath:app.properties")
public class HomeController {
	@Autowired
	private LeagueService leagueService;
	@Autowired
	private PlayerService playerService;
	
	//@Value("${league}")
	//private int league;
	
	private int league = 148642;
	
	@RequestMapping("/")
	public String home(Model theModel) throws JSONException, IOException {

		League newLeague = leagueService.getLeague(league);
		theModel.addAttribute("teams", newLeague.getTeams());
		return "main-menu";
	}

	@RequestMapping("/form")
	public String checkPlayer(Model theModel) throws JSONException, IOException {
		System.out.println("here");
		//playerService.updatePlayer(267);
		return "form";
	}
	@RequestMapping("/graph")
	public String graph() {
		return "graph";
	}

	@GetMapping("/getPlayer")
	public String showFormForUpdate(@RequestParam("playerId") int theId, Model theModel)
			throws JSONException, IOException {
		Player tmp = playerService.getPlayer(theId);
		theModel.addAttribute("player", tmp);
		return "player";
	}
	
	@GetMapping("/getTeamInfoAsJSON")
	public @ResponseBody String showFormForUpdate() throws JsonGenerationException, JsonMappingException, IOException {
		League newLeague = leagueService.getLeague(league);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(newLeague.getTeams());
	}
	
	@GetMapping("/getTeamPoints")
	public @ResponseBody String showFormForUpdate2() throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("here");
		League newLeague = leagueService.getLeague(league);
		ArrayList<Team> teams = new ArrayList<Team>(newLeague.getTeams());
		System.out.println("size is "+teams.size());
		JSONArray array = new JSONArray();
		for (Team team : teams) {
			JSONObject json = new JSONObject();
			json.put("label", team.getTeamName());
			json.put("y", team.getTotalPoints());
			array.put(json);
		}
		//ObjectMapper mapper = new ObjectMapper();
		//return mapper.writeValueAsString(array);
		return array.toString();
	}
	
	
}

