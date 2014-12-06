package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;

public class CoreBuild {

	public static String reMap = "\u00A7r\u00A7fYou have been teleported to: \u00A7r\u00A7a(.*) \u00A7r\u00A77\\(#(\\d*)\\)\u00A7r";
	public static String reRank = "\u00A7r\u00A7fYour rank on this map: \u00A7r\u00A7a(.*)\u00A7r";

	public static void setup() {
		
		new ChatTracker(reMap, Server.BUILD,
				new String[]{"build:map.name", "$1"},
				new String[]{"build:map.id", "$2"});
		
		new ChatTracker(reRank, Server.BUILD,
				new String[]{"build:map.rank", "$1"});
	}
	
}