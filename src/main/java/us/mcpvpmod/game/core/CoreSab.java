package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreSab {

	public static final String 
		RE_JOIN = "Now Logged in!\u00A7r",
		MSG_WAITING = "\u00A7r\u00A7cWaiting for more Players...\u00A7r",
		MSG_WELCOME = "\u00A7r\u00A7cWelcome to Sabotage!\u00A7r",
		MSG_VOTING = "\u00A7r\u00A76Currently voting for: \u00A7r",
		MSG_STARTING = "\u00A7.\u00A7.Sabotage will start in 1 minute\u00A7.",
		MSG_START = "\u00A7r\u00A76Sabotage... Begins!\u00A7r",
		RE_ROLE = "\u00A7.\u00A7.\u00A7.You are (?:a|the)*\\s*(\u00A7.\u00A7.\u00A7..*\u00A7.\u00A7.\u00A7.) this game!\u00A7.",
		RE_DETECTIVE = "\u00A7r\u00A76The detective is: \u00A7r(\u00A7.*)\u00A7r",
		MSG_MURDERED = "\u00A7r\u00A7bA player just got murdered...\u00A7r",
		RE_REMAIN = "\u00A7r\u00A73(\\d*) players remaining.\u00A7r",
		MSG_INSPECTOR = "\u00A7rSaboteur Inspecter for another \u00A7r\u00A7315 seconds\u00A7r",
		MSG_SPECTATE = "\u00A7r\u00A76You are now spectating!\u00A7r",
		RE_WIN = "\u00A7r\u00A7cThe (.*) wins*!\u00A7r",
		RE_CHEST_WAVE = "\u00A7r\u00A7aChest Wave (.)/2 has hit! Better drops!\u00A7r",
		RE_TIME = "\u00A7r\u00A7cSabotage will start in (\\d+) seconds.\u00A7r";

	public static void setup() {

		new ChatTracker(RE_ROLE, Server.SAB,
				new String[]{"sab:role", "$1"});
		
		new ChatTracker(RE_REMAIN, Server.SAB,
				new String[]{"sab:remain", "$1"});
		
		new ChatTracker(RE_DETECTIVE, Server.SAB,
				new String[]{"sab:detective", "$1"});
		
		new ChatTracker(RE_WIN, Server.SAB,
				new String[]{"sab:winner", "$1"});
		
		new ChatTrigger(RE_ROLE, "sab.start", Server.SAB,
				new String[]{"role", "sab:role"});
		
		new ChatTrigger(RE_REMAIN, "sab.death", Server.SAB,
				new String[]{"remain", "$1"});
		
		new ChatTrigger(RE_WIN, "sab.end", Server.SAB,
				new String[]{"winner", "sab:winner"});
		
		new ChatTrigger(RE_CHEST_WAVE, "sab.chest", Server.SAB,
				new String[]{"wave", "$1"});
		
	}
	
}
