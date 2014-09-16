package us.mcpvpmod.game.alerts;

import us.mcpvpmod.triggers.ChatTrigger;


public class AlertsKit {

	public static String reStreak = "\u00A7.\u00A7.Woah, player \u00A7.\u00A7.(\\w+)\u00A7.\u00A7. just got a killstreak of (\\d+)\u00A7.";
	public static String reStreakEnd = "\u00A7.\u00A7.Player \u00A7.\u00A7.(\\w+)\u00A7.\u00A7. just ended the (\\d+) killstreak of (\\w+)\u00A7.";
	public static String reRestart = "\u00A7.\u00A7.Scheduled restart in 1 minute.*";
	
	/**
	 * Sets the TriggerAlerts for Kit.
	 * Called during Sync.
	 */
	public static void setAlerts() {
		new ChatTrigger(reStreak, "kit.streak.get", 
				new String[]{"$1", "player"}, 
				new String[]{"$2", "streak"});
		new ChatTrigger(reStreakEnd, "kit.streak.end", 
				new String[]{"$1", "killer"}, 
				new String[]{"$2", "streak"},
				new String[]{"$3", "killed"});
		new ChatTrigger(reRestart, "kit.restart");
	}
	
}
