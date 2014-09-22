package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreHG {
	
	public static String msgWelcome = "§r§cWelcome to the Hardcore Games!§r";
	public static String msgChoose = "§r§cChoose your kit now!§r";
	public static String msgBegin = "§r§cThe Tournament has begun!§r";
	public static String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	
	public static void setup() {
		
		new ChatTracker(reKit, Server.HG,
				new String[]{"$1", "hg:kit"});
		
		new ChatTrigger(reKit, "hg.kit", Server.HG,  
				new String[]{"$1", "kit"});
		
	}
}
