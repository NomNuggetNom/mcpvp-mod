package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreSab {

	public static String reJoin = "Now Logged in!\u00A7r";
	public static String reWait = "\u00A7r\u00A7cWaiting for more Players...\u00A7r";
	public static String reWelcome = "\u00A7r\u00A7cWelcome to Sabotage!\u00A7r";
	public static String reVoting = "\u00A7r\u00A76Currently voting for: \u00A7r";
	public static String reStarting = "\u00A7.\u00A7.Sabotage will start in 1 minute\u00A7.";
	public static String reStart = "\u00A7r\u00A76Sabotage... Begins!\u00A7r";
	public static String reRole = "\u00A7.\u00A7.\u00A7.You are (?:a|the)*\\s*(\u00A7.\u00A7.\u00A7..*\u00A7.\u00A7.\u00A7.) this game!\u00A7.";
	public static String reDetective = "\u00A7r\u00A76The detective is: \u00A7r(\u00A7.*)\u00A7r";
	public static String reMurdered = "\u00A7r\u00A7bA player just got murdered...\u00A7r";
	public static String reRemain = "\u00A7r\u00A73(\\d*) players remaining.\u00A7r";
	public static String reInspector = "\u00A7rSaboteur Inspecter for another \u00A7r\u00A7315 seconds\u00A7r";
	public static String reSpectate = "\u00A7r\u00A76You are now spectating!\u00A7r";
	public static String reWin = "\u00A7r\u00A7cThe (.*) wins*!\u00A7r";
	public static String reChestWave = "\u00A7r\u00A7aChest Wave (.)/2 has hit! Better drops!\u00A7r";

	public static void setup() {

		new ChatTracker(reRole, Server.SAB,
				new String[]{"sab:role", "$1"});
		
		new ChatTracker(reRemain, Server.SAB,
				new String[]{"sab:remain", "$1"});
		
		new ChatTracker(reDetective, Server.SAB,
				new String[]{"sab:detective", "$1"});
		
		new ChatTracker(reWin, Server.SAB,
				new String[]{"sab:winner", "$1"});
		
		new ChatTrigger(reRole, "sab.start", Server.SAB,
				new String[]{"role", "sab:role"});
		
		new ChatTrigger(reRemain, "sab.death", Server.SAB,
				new String[]{"remain", "$1"});
		
		new ChatTrigger(reWin, "sab.end", Server.SAB,
				new String[]{"winner", "sab:winner"});
		
		new ChatTrigger(reChestWave, "sab.chest", Server.SAB,
				new String[]{"wave", "$1"});
		
	}
	
}
