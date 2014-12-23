package us.mcpvpmod.game.info;

import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.vars.VarsCTF;
import us.mcpvpmod.util.BoardHelper;
import us.mcpvpmod.util.Format;

public class InfoCTF {

	/*
	 * StateCTF.PRE-game Info
	 */
	public static String chosenClass = "Heavy";
	public static String currentMap = "Unknown";
	public static boolean freeDay = false;
	public static String reMap = ".*Map: (.*)";//�eVisit �9www.mcpvp.com�r";
	
	/*
	 * Match Info
	 */
	public static int gameNum = 1;
	
	public static void reset() {
		chosenClass = "Heavy";
		currentMap = "Unknown";
	}
	
	/**
	 * @return The time as an integer. E.g. 5:00 left would be 500.
	 */	
	public static int getTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null && StateCTF.getState() != StateCTF.WAIT) {
			String timeLeft = boardTitle.replaceAll(StateCTF.getState().regex(), "$2");
			return Integer.parseInt(timeLeft.replaceAll("\\D", ""));
		}
		return -1;
	}
	
	/**
	 * @return The time, E.g. 5:00.
	 */
	public static String getFormattedTime() {
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
	
	public static String getTimeString() {
		if (StateCTF.getState() == StateCTF.WAIT) {
			return "Waiting...";
		}
		
		if (StateCTF.getState() == StateCTF.PRE) {
			return "Begins in " + getFormattedTime();
		} 
		
		if (StateCTF.getState() == StateCTF.PLAY) {
			return getFormattedTime() + Format.process(" #r#remaining!");
		}
		
		if (StateCTF.getState() == StateCTF.POST) {
			return "Next game in " + getFormattedTime();
		}
		
		if (StateCTF.getState() == StateCTF.END) {
			return "Restarting in " + getFormattedTime();
		}
		return "null";
	}
	
	public static String getGameNum() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null) {
			if (StateCTF.getState() != StateCTF.WAIT) {
				return boardTitle.replaceAll(StateCTF.getState().regex(), "$1");
			}
		}
		return "-1";
	}
	
	public static int getRedWins() {
		return BoardHelper.getScore("Red");
	}
	
	public static int getBlueWins() {
		return BoardHelper.getScore("Blue");
	}
	
	public static String getFreeDay() {
		if (freeDay) {
			return Format.process("#green#Free day!");
		}
		// Return null so it isn't rendered.
		return null;
	}
	
	public static String getMap() {
		return currentMap;
	}
	
	public static String getRedTimer() {
		if (VarsCTF.get("red-flag").contains("s")) {
			return VarsCTF.get("red-flag").replaceAll(".* (\\d+)s.*", "$1");
		}
		return "";
	}
	
	public static String getBlueTimer() {
		if (VarsCTF.get("blue-flag").contains("s")) {
			return VarsCTF.get("blue-flag").replaceAll(".* (\\d+)s.*", "$1");
		}
		return "";
	}
	
}
