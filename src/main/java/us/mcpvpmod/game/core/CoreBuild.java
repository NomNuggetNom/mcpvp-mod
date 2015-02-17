package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;

public class CoreBuild {

	public static final String 
		RE_MAP = "\u00A7r\u00A7fYou have been teleported to: \u00A7r\u00A7a(.*) \u00A7r\u00A77\\(#(\\d*)\\)\u00A7r",
		RE_RANK = "\u00A7r\u00A7fYour rank on this map: \u00A7r\u00A7a(.*)\u00A7r";

	public static void setup() {
		
		new ChatTracker(RE_MAP, Server.BUILD,
				new String[]{"build:map.name", "$1"},
				new String[]{"build:map.id", "$2"});
		
		new ChatTracker(RE_RANK, Server.BUILD,
				new String[]{"build:map.rank", "$1"});
	}
	
}