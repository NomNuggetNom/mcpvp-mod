package us.mcpvpmod.game.info;

import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.util.BoardHelper;

public class InfoHG {

	public static String reTime = "Starting In (.*)";
	
	/**
	 * @return The time as an integer. E.g. 5:00 left would be 500.
	 */	
	public static int getTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null) {
			String timeLeft = boardTitle.replaceAll(reTime, "$1");
			if (timeLeft.replaceAll("\\D", "").equals("")) return -1;
			return Integer.parseInt(timeLeft.replaceAll("\\D", ""));
		}
		return -1;
	}
	
	/**
	 * @return The time, E.g. 5:00.
	 */
	public static String getFormattedTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle == null) return "";
		
		String timeLeft = boardTitle.replaceAll(reTime, "$1");
			
		if (getTime() <= 130 && getTime() > 100) {
			return (Format.process("#yellow#" + timeLeft));
		} else if (getTime() <= 30) {
			return (Format.process("#red#" + timeLeft));
		} else {
			return timeLeft;
		}
	}
}
