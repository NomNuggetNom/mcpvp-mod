package us.mcpvpmod.game.core;

import us.mcpvpmod.trackers.ChatTracker;

public class CoreSab {

	public static String reWait = "§r§cWaiting for more Players...§r";
	public static String reWelcome = "§r§cWelcome to Sabotage!§r";
	public static String reVoting = "§r§6Currently voting for: §r";
	public static String reStarting = "§.§.Sabotage will start in 1 minute§.";
	public static String reRole = "§.§.§.You are §.§.§(..*)§.§.§. this game!§.";
	public static String reDetective = "§r§6The detective is: §r§(.*)§r";
	public static String reMurdered = "§r§bA player just got murdered...§r";
	public static String reRemain = "§r§3(\\d*) players remaining.§r";
	public static String reInspector = "§rSaboteur Inspecter for another §r§315 seconds§r";
	public static String reSpectate = "§r§6You are now spectating!§r";
	
	public static void setup() {
		
		new ChatTracker(reRole, new String[]{"$1", "sab:role"});
		
		new ChatTracker(reRemain, new String[]{"$1", "sab:remain"});
		
	}
	
}
