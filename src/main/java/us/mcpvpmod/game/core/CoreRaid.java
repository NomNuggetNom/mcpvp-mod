package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreRaid {
	
	public static String reDeposit = "\u00A7r\u00A77You now have (.*) gold in your bank account.\u00A7r";
	public static String reBalance = "\u00A7r\u00A77You have (.*) gold in your bank account.\u00A7r";
	public static String reRaid = "\u00A7r\u00A7c(.*) is raiding your base!\u00A7r";

	public static void setup() {
		
		new ChatTracker(reBalance, Server.RAID,
				new String[]{"raid:balance", "$1"});
		
		new ChatTrigger(reDeposit, "raid.deposit", Server.RAID, 
				new String[]{"balance", "$1"});
		
		new ChatTrigger(reRaid, "raid.raid", Server.RAID,
				new String[]{"player", "$1"});
		
	}
	
}
