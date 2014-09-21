package us.mcpvpmod.game.core;

import us.mcpvpmod.game.info.InfoSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreSab {

	public static String reJoin = "Now Logged in!§r";
	public static String reWait = "§r§cWaiting for more Players...§r";
	public static String reWelcome = "§r§cWelcome to Sabotage!§r";
	public static String reVoting = "§r§6Currently voting for: §r";
	public static String reStarting = "§.§.Sabotage will start in 1 minute§.";
	public static String reStart = "§r§6Sabotage... Begins!§r";
	public static String reRole = "§.§.§.You are (?:a|the)*\\s*(§.§.§..*§.§.§.) this game!§.";
	public static String reDetective = "§r§6The detective is: §r(§.*)§r";
	public static String reMurdered = "§r§bA player just got murdered...§r";
	public static String reRemain = "§r§3(\\d*) players remaining.§r";
	public static String reInspector = "§rSaboteur Inspecter for another §r§315 seconds§r";
	public static String reSpectate = "§r§6You are now spectating!§r";
	public static String reWin = "§r§cThe (.*) wins*!§r";
	public static String reChestWave = "§r§aChest Wave (.)/2 has hit! Better drops!§r";
	
	public static void setup() {

		new ChatTracker(reRole, new String[]{"$1", "sab:role"});
		
		new ChatTracker(reRemain, new String[]{"$1", "sab:remain"});
		
		new ChatTracker(reDetective, new String[]{"$1", "sab:detective"});
		
		new ChatTracker(reWin, new String[]{"$1", "sab:winner"});
		
		new ChatTrigger(reRole, "sab.start", 
				new String[]{"$1", "role"});
		
		new ChatTrigger(reRemain, "sab.death", 
				new String[]{"$1", "remain"});
		
		new ChatTrigger(reWin, "sab.end",
				new String[]{"var:sab:winner", "winner"});
		
		new ChatTrigger(reChestWave, "sab.chest",
				new String[]{"$1", "wave"});
		
	}
	
}
