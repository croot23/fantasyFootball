package com.josephcroot.fantasyfootball;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetJSONFromFantasyFootballAPI {

	private static JSONObject playerInfo;
	private static int gameweek = 1;
	
	@Scheduled(fixedDelay = 300000)
	public void scheduleFixedDelayTask() throws JSONException, IOException {
		updatePlayerInfo();
	}
	
	@Scheduled(fixedDelay = 300000)
	public void updateGameweek() throws JSONException, IOException {
		setGameweek();
	}

	public static int getGameweek() {
		return gameweek;
	}

	public static JSONObject getPlayerInfo() throws JSONException, IOException {
		if (playerInfo == null)
			playerInfo = fromUrl("bootstrap-static");
		return playerInfo;
	}

	public static JSONObject updatePlayerInfo() throws JSONException, IOException {
		playerInfo = fromUrl("bootstrap-static");
		return playerInfo;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject fromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL("https://fantasy.premierleague.com/drf/" + url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static JSONObject getLeagueInfo(int league) throws JSONException, IOException {
		JSONObject json = fromUrl(
				"leagues-classic-standings/" + Integer.toString(league));
		JSONObject arr = json.getJSONObject("league");
		return arr;
	}

	public static JSONArray getTeams(int league) throws JSONException, IOException {
		JSONObject json = fromUrl(
				"leagues-classic-standings/" + Integer.toString(league));
		JSONObject standings = (JSONObject) json.get("standings");
		JSONArray arr = standings.getJSONArray("results");
		return arr;
	}

	public static JSONArray getTeamPlayers(int team) throws JSONException, IOException {
		JSONObject json = fromUrl("entry/" + Integer.toString(team) + "/event/"+getGameweek()+"/picks");
		JSONArray arr = json.getJSONArray("picks");
		return arr;
	}

	public static JSONObject getTeamInfo(int team) throws JSONException, IOException {
		JSONObject json = fromUrl("entry/" + Integer.toString(team));
		JSONObject obj = json.getJSONObject("entry");
		return obj;
	}

	public static JSONArray getTeamChipsInfo(int team) throws JSONException, IOException {
		JSONObject json = fromUrl("entry/" + Integer.toString(team) + "/history");
		JSONArray arr = json.getJSONArray("chips");
		return arr;
	}

	public static JSONObject getPlayer(int id) throws JSONException, IOException {
		JSONObject json = getPlayerInfo();
		JSONArray arr = json.getJSONArray("elements");
		for (int j = 0; j < arr.length(); j++) {
			JSONObject currentPlayer = arr.getJSONObject(j);
			if (currentPlayer.getInt("id") == id) {
				return currentPlayer;
			}
		}
		return null;
	}
	
	public static JSONArray getTransfers(int team) throws JSONException, IOException {
		JSONObject json = fromUrl("entry/" + Integer.toString(team) + "/transfers");
		JSONArray arr = json.getJSONArray("history");
		return arr;
	}
	
	//following is pretty trash, need to add a better way of handling the fantasy API when it's an Array not an Object
	public static void setGameweek() throws JSONException, IOException {
		JSONArray gameweekInfo = getGameweekInfo();
		for (int i = 0; i < gameweekInfo.length(); i++) {
			JSONObject current = gameweekInfo .getJSONObject(i);
			if (current.getBoolean("is_next")==true) {
				gameweek = current.getInt("id")-1;
			}
		}
	}
	public static JSONArray getGameweekInfo() throws JSONException, IOException {
		InputStream is = new URL("https://fantasy.premierleague.com/drf/events").openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

}
