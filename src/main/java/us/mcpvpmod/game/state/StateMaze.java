package us.mcpvpmod.game.state;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.util.BoardHelper;

public enum StateMaze implements State {
	WAIT, PRE, PLAY, DEAD, NONE;
	
	public static StateMaze getState() {
		if (Main.mc.theWorld == null) return NONE;
		if (Main.mc.theWorld.getScoreboard() == null) {
			return WAIT;
		} else if (BoardHelper.getBoardTitle().contains("Starting in")) {
			return PRE;
		} else if (BoardHelper.hasTeams()) {
			return PLAY;
		}
		return NONE;
	}
	
	public void render() {
		if (Main.mc.gameSettings.showDebugInfo || StateMaze.getState().equals(StateMaze.NONE)) return;
		Server.MAZE.varProvider.putVars();
		
		for (InfoBlock block : InfoBlock.get(Server.MAZE, this)) {
			block.display();
		}
	}
}
