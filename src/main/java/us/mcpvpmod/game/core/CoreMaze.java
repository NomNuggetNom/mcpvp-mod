package us.mcpvpmod.game.core;

import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import cpw.mods.fml.common.FMLLog;

public class CoreMaze {

	public static String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	public static String reJoinTeam = "\u00A7.\u00A7.You have joined the team \"(.*)\".\u00A7.";
	public static String reTeamOut = "\u00A7.(\u00A7..*) is out. (\\d+) teams left.\u00A7.";
	
	public static void setup() {
		FMLLog.info("[MCPVP] Syncing setup for Maze");
		
		new ChatTracker(reKit, 
				new String[]{"$1", "maze:kit"});
		
		new ChatTracker(reJoinTeam, 
				new String[]{"$1", "maze:team"});
		
		new ChatTrigger(reKit, "maze.kit", 
				new String[]{"$1", "kit"});
		
		new ChatTrigger(reJoinTeam, "maze.team.join", 
				new String[]{"$1", "team"});
		
		new ChatTrigger(reTeamOut, "maze.team.out", 
				new String[]{"$1", "team"},
				new String[]{"$2", "remain"});

		new BoardTracker("Base X Cord:", "maze:base.x");
		new BoardTracker("Base Z Cord:", "maze:base.z");
		new BoardTracker("Princess Health%", "maze:princess.health");
		new BoardTracker("Princess Hunger%", "maze:princess.hunger");
	}
}