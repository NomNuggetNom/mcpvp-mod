package us.mcpvpmod.game.scoreboard;

import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.util.BoardHelper;

// TODO: Move to InfoCTF
public class BoardCTF {

	/**
	 * Gets the time as an integer. E.g. 5:00 left would be 500.
	 */
	public static int getTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null && StateCTF.getState() != StateCTF.WAIT) {
			String timeLeft = boardTitle.replaceAll(StateCTF.getState().regex(), "$2");
			return Integer.parseInt(timeLeft.replaceAll("\\D", ""));
		}
		return -1;
	}
	
	public static String getFormattedTime() {
		if (!Server.getServer().equals((Server.CTF))) return "-1";
		
		String boardTitle = BoardHelper.getBoardTitle();
		if (StateCTF.getState().equals(StateCTF.WAIT)) {
			return "-1";
		}
		
		if (boardTitle != null) {
			String timeLeft = boardTitle.replaceAll(StateCTF.getState().regex(), "$2");
			
			if (getTime() <= 500 && getTime() > 100) {
				return (Format.process("#yellow#" + timeLeft));
			} else if (getTime() <= 100) {
				return (Format.process("#red#" + timeLeft));
			} else {
				return timeLeft;
			}
		}
		return "-1";
	}
	
}
