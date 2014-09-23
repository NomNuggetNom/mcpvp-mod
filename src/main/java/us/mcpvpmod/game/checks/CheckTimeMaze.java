package us.mcpvpmod.game.checks;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.game.info.InfoMaze;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.vars.Vars;

public class CheckTimeMaze {
	
	public static int oldTime = 0;

	public static void updateTime() {
		int time = InfoMaze.getTime();
		if (oldTime != time) {
			checkTime();
			oldTime = time;
		}
	}
	
	public static void checkTime() {
		
		if (oldTime <= 5 
				&& oldTime != -1 
				&& StateMaze.getState().equals(StateMaze.PRE)
				&& ConfigMazeSelect.selectMode.equals("Select Before Start")) {
			
			if (Vars.get("maze:kit").equals("")) {
				Main.mc.thePlayer.sendChatMessage("/kit " + ConfigMazeSelect.selectClass);
			}
			
			if (Vars.get("maze:team").equals("")) {
				Main.mc.thePlayer.sendChatMessage("/team " + ConfigMazeSelect.selectTeam);
			}
			
		}
	}
	
	

}
