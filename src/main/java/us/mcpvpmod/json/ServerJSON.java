package us.mcpvpmod.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.util.Format;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.minecraftforge.fml.common.FMLLog;

public class ServerJSON extends TimerTask {

	/**
	 * Original code by TISSIN.
	 * 
	 * Parses MCPVP's Server JSON and forms MCPVPServers out of it
	 * to be used in the server menu.
	 */
	@Override
	public void run() {
		String json = getJson();
		Gson gson = new Gson();
		
		// Type token specifies which class we are creating.
		Type type = new TypeToken<List<MCPVPServer>>(){}.getType();
		
		// Create an ArrayList using the magical fromJson. 
		// This auto-magically sorts through the JSON,
		// creates MCPVPServers and sets all the values in the new MCPVPServer
		ArrayList<MCPVPServer> servers = gson.fromJson(json, type);
		
		if (servers != null) {
			
			// Cycle through all the servers created and remove parkour servers.
			for (int i = servers.size() - 1; i != -1; i--) {
				if (servers.get(i).Server.endsWith(".parkour.mcpvp.com")) {
					servers.remove(i);
				}
			}
			// Update the reference.
			MCPVPServer.servers = servers;
		}
	}

	/**
	 * Code by TISSIN.
	 * @return A string of the ping.json MCPVP API.
	 */
	public String getJson() {
		
		String json = "";
		BufferedReader in = null;
		
		try {
			// Open the connection to the ping API.
			URLConnection connection = new URL("http://i01.gamebalancer.minecraftpvp.com/api/ping.json").openConnection();                        
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
	
}
