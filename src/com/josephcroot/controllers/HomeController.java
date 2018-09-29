package com.josephcroot.controllers;

import java.io.IOException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.josephcroot.entity.League;
import com.josephcroot.service.LeagueService;
//import com.josephcroot.service.PlayerService;

@Controller
@PropertySource("classpath:app.properties")
public class HomeController {
	@Autowired
	private LeagueService leagueService;
	//@Autowired
	//private PlayerService playerService;
	
	@Value("${league}")
	private int league;
	
	@RequestMapping("/")
	public String home(Model theModel) throws JSONException, IOException {

		League newLeague = leagueService.getLeague(league);
		theModel.addAttribute("teams", newLeague.getTeams());
		return "main-menu";
	}
	
	@RequestMapping("/all-teams")
	public String allTeams(Model theModel) throws JSONException, IOException {

		League newLeague = leagueService.getLeague(league);
		theModel.addAttribute("teams", newLeague.getTeams());
		return "all-teams";
	}
	
	@RequestMapping("/graph")
	public String graph() {
		return "graph";
	}

	/*
	@RequestMapping("/getPlayer")
	public String showFormForUpdate(@RequestParam("playerId") int theId, Model theModel)
			throws JSONException, IOException {
		Player tmp = playerService.getPlayer(theId);
		theModel.addAttribute("player", tmp);
		return "player";
	}
	*/
}

