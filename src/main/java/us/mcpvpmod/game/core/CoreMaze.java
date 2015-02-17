package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreMaze {

	public static final String 
		RE_KIT = "\u00A7.\u00A7.You are now a (.*)\u00A7.",
		RE_JOIN_TEAM = "\u00A7.\u00A7.You have joined the team \"(.*)\".\u00A7.",
		RE_TEAM_OUT = "\u00A7.(\u00A7..*) is out. (\\d+) teams left.\u00A7.";

	public static void setup() {
		
		new ChatTracker(RE_KIT, Server.MAZE, 
				new String[]{"maze:kit", "$1"});
		
		new ChatTracker(RE_JOIN_TEAM, Server.MAZE, 
				new String[]{"maze:team", "$1"});
		
		new ChatTracker(RE_TEAM_OUT, Server.MAZE,
				new String[]{"maze:remain", "$1"});
		
		new ChatTrigger(RE_KIT, "maze.kit", Server.MAZE,  
				new String[]{"kit", "$1"});
		
		new ChatTrigger(RE_JOIN_TEAM, "maze.team.join", Server.MAZE,  
				new String[]{"team", "$1"});
		
		new ChatTrigger(RE_TEAM_OUT, "maze.team.out", Server.MAZE, 
				new String[]{"team", "$1"},
				new String[]{"remain", "$2"});

		new BoardTracker("maze:base.x", "Base X Cord:");
		new BoardTracker("maze:base.z", "Base Z Cord:");
		new BoardTracker("maze:princess.health", "Princess Health%");
		new BoardTracker("maze:princess.hunger", "Princess Hunger%");
	}
}