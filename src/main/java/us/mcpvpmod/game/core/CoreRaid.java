package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreRaid {
	
	public static final String 
		RE_NEW_BALANCE = "\u00A7r\u00A7aNew Balance: (.*)G\u00A7r",
		RE_BALANCE = "\00A7r\u00A76Balance: \u00A7r\u00A76\u00A7o(.*)G\u00A7r",
		RE_RAID = "\u00A7r\u00A7c(.*) is raiding your base!\u00A7r";

	public static void setup() {
		
		new ChatTracker(RE_NEW_BALANCE, Server.RAID,
				new String[]{"raid:balance", "$1"});
		
		new ChatTracker(RE_BALANCE, Server.RAID,
				new String[]{"raid:balance", "$1"});
		
		new ChatTrigger(RE_NEW_BALANCE, "raid.balance", Server.RAID, 
				new String[]{"balance", "$1"});
		
		new ChatTrigger(RE_RAID, "raid.raid", Server.RAID,
				new String[]{"player", "$1"});
		
	}
	
}
