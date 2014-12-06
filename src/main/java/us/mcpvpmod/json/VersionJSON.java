package us.mcpvpmod.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

import us.mcpvpmod.Main;
import us.mcpvpmod.version.MCPVPVersion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.common.FMLLog;

public class VersionJSON extends TimerTask {

	@Override
	public void run() {
		String json = getJson();
		Gson gson = new GsonBuilder().create();
		MCPVPVersion v = gson.fromJson(json, MCPVPVersion.class);
		Main.mcpvpVersion = v;
	}

	public String getJson() {
		
		String json = "";
		BufferedReader in = null;
		
		try {
			// Open the connection to the ping API.
			URLConnection connection = new URL("https://raw.githubusercontent.com/NomNuggetNom/mcpvp-mod-installer/master/version.json").openConnection();                        
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
			FMLLog.info("[MCPVP] Error getting JSON for servers!");
			ex.printStackTrace();
		}
		FMLLog.info("[MCPVP] Unable to get JSON for servers!");
		return "";
	}
	
}
