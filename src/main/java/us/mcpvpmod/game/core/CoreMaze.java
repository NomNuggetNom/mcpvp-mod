package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import cpw.mods.fml.common.FMLLog;

public class CoreMaze {

	public static String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	public static String reJoinTeam = "\u00A7.\u00A7.You have joined the team \"(.*)\".\u00A7.";
	public static String reTeamOut = "\u00A7.(\u00A7..*) is out. (\\d+) teams left.\u00A7.";
	
	public static void setup() {
		
		new ChatTracker(reKit, Server.MAZE, 
				new String[]{"maze:kit", "$1"});
		
		new ChatTracker(reJoinTeam, Server.MAZE, 
				new String[]{"maze:team", "$1"});
		
		new ChatTracker(reTeamOut, Server.MAZE,
				new String[]{"maze:remain", "$1"});
		
		new ChatTrigger(reKit, "maze.kit", Server.MAZE,  
				new String[]{"kit", "$1"});
		
		new ChatTrigger(reJoinTeam, "maze.team.join", Server.MAZE,  
				new String[]{"team", "$1"});
		
		new ChatTrigger(reTeamOut, "maze.team.out", Server.MAZE, 
				new String[]{"team", "$1"},
				new String[]{"remain", "$2"});

		new BoardTracker("maze:base.x", "Base X Cord:");
		new BoardTracker("maze:base.z", "Base Z Cord:");
		new BoardTracker("maze:princess.health", "Princess Health%");
		new BoardTracker("maze:princess.hunger", "Princess Hunger%");
	}
}