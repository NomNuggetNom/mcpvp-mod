package us.mcpvpmod.game.info;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.util.BoardHelper;
import us.mcpvpmod.util.Format;

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
		} else {
			// Return null so it isn't rStateCTF.ENDered.
			return null;
		}
	}
	
	public static String getIP() {
		return Main.mc.func_147104_D().serverIP;
	}
	
	public static int getPing() {
		return (int) Main.mc.func_147104_D().pingToServer;
	}
	
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
