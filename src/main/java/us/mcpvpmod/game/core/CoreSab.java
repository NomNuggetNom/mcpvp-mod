package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.game.info.InfoSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import us.mcpvpmod.util.Format;

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

		new ChatTracker(reRole, Server.SAB,
				new String[]{"sab:role", "$1"});
		
		new ChatTracker(reRemain, Server.SAB,
				new String[]{"sab:remain", "$1"});
		
		new ChatTracker(reDetective, Server.SAB,
				new String[]{"sab:detective", "$1"});
		
		new ChatTracker(reWin, Server.SAB,
				new String[]{"sab:winner", "$1"});
		
		new ChatTrigger(reRole, "sab.start", Server.SAB,
				new String[]{"role", "$1"});
		
		new ChatTrigger(reRemain, "sab.death", Server.SAB,
				new String[]{"remain", "$1"});
		
		new ChatTrigger(reWin, "sab.end", Server.SAB,
				new String[]{"var:sab:winner", "winner"});
		
		new ChatTrigger(reChestWave, "sab.chest", Server.SAB,
				new String[]{"wave", "$1"});
		
	}
	
}
