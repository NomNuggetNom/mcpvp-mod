package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreRaid {
	
	public static String reNewBalance = "\u00A7r\u00A7aNew Balance: (.*)G\u00A7r";
	public static String reBalance = "\00A7r\u00A76Balance: \u00A7r\u00A76\u00A7o(.*)G\u00A7r";
	public static String reRaid = "\u00A7r\u00A7c(.*) is raiding your base!\u00A7r";

	public static void setup() {
		
		new ChatTracker(reNewBalance, Server.RAID,
				new String[]{"raid:balance", "$1"});
		
		new ChatTracker(reBalance, Server.RAID,
				new String[]{"raid:balance", "$1"});
		
		new ChatTrigger(reNewBalance, "raid.balance", Server.RAID, 
				new String[]{"balance", "$1"});
		
		new ChatTrigger(reRaid, "raid.raid", Server.RAID,
				new String[]{"player", "$1"});
		
	}
	
}
