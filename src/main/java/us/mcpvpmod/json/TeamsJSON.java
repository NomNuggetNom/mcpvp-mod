package us.mcpvpmod.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.game.team.CTFTeam;
import us.mcpvpmod.util.Format;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cpw.mods.fml.common.FMLLog;

public class TeamsJSON {

	public static void run() {
		String json = getJson();
		Gson gson = new Gson();
		
		// Type token specifies which class we are creating.
		//Type type = new TypeToken<List<CTFTeam>>(){}.getType();
		Type type = new TypeToken<List<MCPVPServer>>(){}.getType();
		
		// Create an ArrayList using the magical fromJson. 
		// This auto-magically sorts through the JSON,
		// creates MCPVPServers and sets all the values in the new MCPVPServer
		CTFTeam.teams = gson.fromJson(json, type);
	}

	public static String getJson() {
		
		String json = "";
		BufferedReader in = null;
		
		try {
			// Open the connection to the ping API.
			URLConnection connection = new URL("https://raw.githubusercontent.com/NomNuggetNom/mcpvp-mod/nom/ctf-teams.json").openConnection();                        
			connection.setReadTimeout(10 * 1000);
			
			// Handle the formatting of the page and establish the BufferedReader.
			if (connection.getHeaderField("Content-Encoding")!=null && connection.getHeaderField("Content-Encoding").equals("gzip")) {
				in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));            
			} else {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));            
			}
			
			// Cycle through each line on the page and add it to our final output.
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				json+=inputLine;
			}
			
			// Close the connection and return the string.
			in.close();
			return json;
			
		} catch (IOException ex) {
			FMLLog.info(Format.s("error.json.server"));
			ex.printStackTrace();
		}
		FMLLog.info(Format.s("error.json.server"));
		return "";
	}
	
	public static ArrayList<String> getPlayersOnTeam(String teamName) {
		for (CTFTeam team : CTFTeam.teams) {
			if (team.name.equalsIgnoreCase(teamName))
				return (ArrayList<String>) team.members;
		}
		return null;
	}
	
	
}
