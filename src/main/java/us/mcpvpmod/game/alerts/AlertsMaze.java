package us.mcpvpmod.game.alerts;

import java.util.HashMap;

import cpw.mods.fml.common.FMLLog;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class AlertsMaze {

	public static String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	public static String reTeam = "\u00A7.\u00A7.You have joined the team \"(.*)\".\u00A7.";
	
	/**
	 * Sets the TriggerAlerts for Maze.
	 * Called during Sync.
	 */
	public static void setAlerts() {
		FMLLog.info("[MCPVP] Syncing setup for Maze");
		
		new ChatTrigger(reKit, "maze.kit", 
				new String[]{"$1", "kit"});
		
		new ChatTracker(reKit, 
				new String[]{"$1", "maze:i.kit"});
		
		new ChatTracker(reTeam, 
				new String[]{"$1", "maze:i.team"});
		
		new BoardTracker("Base X Cord:", "maze:i.base.x");
		new BoardTracker("Base Z Cord:", "maze:i.base.z");
		new BoardTracker("Princess Health%", "maze:i.princess.health");
		new BoardTracker("Princess Hunger%", "maze:i.princess.hunger");
	}
	
}
