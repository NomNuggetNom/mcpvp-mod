package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreKit {

	public static String reStreak = "\u00A7.\u00A7.Woah, player \u00A7.\u00A7.(\\w+)\u00A7.\u00A7. just got a killstreak of (\\d+)\u00A7.";
	public static String reStreakEnd = "\u00A7.\u00A7.Player \u00A7.\u00A7.(\\w+)\u00A7.\u00A7. just ended the (\\d+) killstreak of (\\w+)\u00A7.";
	public static String reRestart = "\u00A7.\u00A7.Scheduled restart in 1 minute.*";
	
	public static void setup() {
		
		new ChatTrigger(reStreak, "kit.streak.get", Server.KIT, 
				new String[]{"$1", "player"}, 
				new String[]{"$2", "streak"});
		
		new ChatTrigger(reStreakEnd, "kit.streak.end", Server.KIT,  
				new String[]{"$1", "killer"}, 
				new String[]{"$2", "streak"},
				new String[]{"$3", "killed"});
		
		new ChatTrigger(reRestart, "kit.restart", Server.KIT);
		
		new BoardTracker("Credits:", "kit:credits");
		new BoardTracker("Deaths:", "kit:deaths");
		new BoardTracker("Kills:", "kit:kills");
		new BoardTracker("Your worth:", "kit:worth");
		new BoardTracker("Killstreak:", "kit:ks");
	}
	
}