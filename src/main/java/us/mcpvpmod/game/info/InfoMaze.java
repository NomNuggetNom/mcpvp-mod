package us.mcpvpmod.game.info;

import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.util.BoardHelper;

public class InfoMaze {

	public static String reTime = "Starting in (.*)";
	
	/**
	 * @return The time as an integer, e.g. 5:00 would return 500.
	 */ 
	public static int getTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		
		// Sometimes there is no ScoreBoard, even in pre-game.
		if (boardTitle == null) return -1;
		
		String timeLeft = boardTitle.replaceAll(reTime, "$1");
		if (timeLeft.replaceAll("\\D", "").equals("")) return -1;
		return Integer.parseInt(timeLeft.replaceAll("\\D", ""));
	}
	
}
