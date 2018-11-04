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
	private static int gameweek;

	@Scheduled(fixedDelay = 300000)
	public void updatePlayers() throws JSONException, IOException {
		updatePlayerInfo();
	}

	public static JSONObject updatePlayerInfo() throws JSONException, IOException {
		return getJSONObject("bootstrap-static", null);
	}

	@Scheduled(fixedDelay = 300000)
	public void updateGameweek() throws JSONException, IOException {
		setGameweek();
	}

	public static int getGameweek() throws JSONException, IOException {
		if (gameweek == 0)
			setGameweek();
		return gameweek;
	}

	public static void setGameweek() throws JSONException, IOException {
		JSONArray gameweekInfo = getJSONArray("https://fantasy.premierleague.com/drf/events");
		for (int i = 0; i < gameweekInfo.length(); i++) {
			JSONObject current = gameweekInfo.getJSONObject(i);
			if (current.getBoolean("is_next") == true) {
				gameweek = current.getInt("id") - 1;
			}
		}
	}

	/* Generic method to get JSON Object from fantasy API */
	public static JSONObject getJSONObject(String url, String object) throws IOException, JSONException {
		InputStream is = new URL("https://fantasy.premierleague.com/drf/" + url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			if (object != null && !object.equals(""))
				json = json.getJSONObject(object);
			return json;
		} finally {
			is.close();
		}
	}

	/* Generic method to get JSONArray from fantasy API */
	public static JSONArray getJSONArray(String url) throws JSONException, IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	/* Generic method to get a JSONArray from a JSONObject retrieved from fantasy API */
	public static JSONArray getJSONArrayFromJSONObject(String url, String object, String array)
			throws IOException, JSONException {
		JSONObject jsonO = getJSONObject(url, object);
		return jsonO.getJSONArray(array);
	}

	/* Read the JSON from the API */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject getPlayerInfo() throws JSONException, IOException {
		if (playerInfo == null)
			playerInfo = getJSONObject("bootstrap-static", null);
		return playerInfo;
	}

	public static JSONObject getLeagueInfo(int league) throws JSONException, IOException {
		return getJSONObject("league-classic-standings/" + Integer.toString(league), "league");
	}

	public static JSONObject getTeamInfo(int team) throws JSONException, IOException {
		return getJSONObject("entry/" + Integer.toString(team), "entry");
	}

	public static JSONArray getTeams(int league) throws JSONException, IOException {
		return getJSONArrayFromJSONObject("leagues-classic-standings/" + Integer.toString(league), "standings",
				"results");
	}

	public static JSONArray getTeamPlayers(int team) throws JSONException, IOException {
		return getJSONArrayFromJSONObject("entry/" + Integer.toString(team) + "/event/" + getGameweek() + "/picks",
				null, "picks");
	}

	public static JSONArray getTeamChipsInfo(int team) throws JSONException, IOException {
		return getJSONArrayFromJSONObject("entry/" + Integer.toString(team) + "/history", null, "chips");
	}

	public static JSONArray getTransfers(int team) throws JSONException, IOException {
		return getJSONArrayFromJSONObject("entry/" + Integer.toString(team) + "/transfers", null, "history");
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
	
	public static JSONArray getTeamPoints(int team) throws JSONException, IOException {
		return getJSONArrayFromJSONObject("entry/" + Integer.toString(team) + "/history", null, "history");
	}
	
}
