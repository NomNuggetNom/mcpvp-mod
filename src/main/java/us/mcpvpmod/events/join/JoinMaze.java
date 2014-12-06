package us.mcpvpmod.events.join;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.util.Format;

public class JoinMaze {
	
	public static void onJoin() {
		
		if (ConfigMazeSelect.selectMode.equals(Format.s("maze.config.select.selectMode.m.join"))) {
			Main.mc.thePlayer.sendChatMessage("/kit " + ConfigMazeSelect.selectClass);
			Main.mc.thePlayer.sendChatMessage("/team " + ConfigMazeSelect.selectTeam);
		}
		
	}

}
