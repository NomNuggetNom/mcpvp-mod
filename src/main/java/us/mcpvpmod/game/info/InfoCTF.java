package us.mcpvpmod.game.info;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.util.BoardHelper;

public class InfoCTF {

	/*
	 * StateCTF.PRE-game Info
	 */
	public static String chosenClass = "Heavy";
	public static String currentMap = "Unknown";
	public static boolean freeDay = false;
	public static String reMap = ".*Map: (.*)";//§eVisit §9www.mcpvp.com§r";
	
	/*
	 * Match Info
	 */
	public static int gameNum = 1;
	
	public static void reset() {
		chosenClass = "Heavy";
		currentMap = "Unknown";
	}
	
	/*
	 * Team Info
	 */
	public static String blueCaps = "";
	public static String blueFlag = "";
	public static String bluePlayers = "";
	public static String redCaps = "";
	public static String redFlag = "";
	public static String redPlayers = "";
	public static String maxCaps = "";
	public static String gameWinner = "";
	
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
	 * @return Formatted time, including color code.
	 */ 
	public static String getFormattedTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		
		// WAIT has no time.
		if (StateCTF.getState().equals(StateCTF.WAIT)) return "-1";
		
		if (boardTitle != null) {
			String timeLeft = boardTitle.replaceAll(StateCTF.getState().regex(), "$2");
			
			// Less than 5:00 is yellow.
			if (getTime() <= 500 && getTime() > 100) {
				return (Format.process("#yellow#" + timeLeft));
			// Less than 1:00 is red.
			} else if (getTime() <= 100) {
				return (Format.process("#red#" + timeLeft));
			} else {
				return timeLeft;
			}
		}
		return "-1";
	}
	
	/**
	 * @return Returns text along with formatted time.
	 */ 
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
	
	/**
	 * @return The game number. 
	 */ 
	 // TODO: Use a BoardTracker.
	public static String getGameNum() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null) {
			if (StateCTF.getState() != StateCTF.WAIT) {
				return boardTitle.replaceAll(StateCTF.getState().regex(), "$1");
			}
		}
		return "-1";
	}
	
	// TODO: Use a BoardTracker.
	public static int getRedWins() {
		return BoardHelper.getScore("Red");
	}
	
	// TODO: Use a BoardTracker.
	public static int getBlueWins() {
		return BoardHelper.getScore("Blue");
	}
	
	/**
	 * @return The "free day" string to be rendered. 
	 */ 
	public static String getFreeDay() {
		if (freeDay) {
			return Format.process("#green#Free day!");
		} else {
			// Return null so it isn't rStateCTF.ENDered.
			return null;
		}
	}
	
	// TODO: Remove
	public static String getIP() {
		return Main.mc.func_147104_D().serverIP;
	}
	
	// TODO: Remove
	public static int getPing() {
		return (int) Main.mc.func_147104_D().pingToServer;
	}
	
	/**
	 * @return The winner of the game.
	 */ 
	// TODO: Fix
	public static String getWinner() {
		if (StateCTF.getState().equals(StateCTF.POST)) {
			return InfoCTF.gameWinner;
		} else {
			return "Nobody!";
		}
	}
	
	// TODO: Better map detection using MOTD or PingJSON
	public static String getMap() {
		return currentMap;
		/*
		if (mc.func_147104_D() != null) {
			if (mc.func_147104_D().serverMOTD != null && mc.func_147104_D().serverMOTD.contains("")) {
				return (mc.func_147104_D().serverMOTD.replaceAll(reMap, "$1").replaceAll("\n§eVisit §9www.mcpvp.com§r", ""));
			} else {
				return GameState.currentMap;
			}
		} else {
			return GameState.currentMap;
		}
		*/
	}
	
}
