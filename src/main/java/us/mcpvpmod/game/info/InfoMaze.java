package us.mcpvpmod.game.info;

import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.util.BoardHelper;

public class InfoMaze {

	public static String reTime = "Starting in (.*)";
	
	public static int getTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null) {
			String timeLeft = boardTitle.replaceAll(reTime, "$1");
			if (timeLeft.replaceAll("\\D", "").equals("")) return -1;
			return Integer.parseInt(timeLeft.replaceAll("\\D", ""));
		}
		return -1;
	}
	
}
