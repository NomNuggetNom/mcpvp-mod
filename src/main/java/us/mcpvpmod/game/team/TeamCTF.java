package us.mcpvpmod.game.team;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.util.BoardHelper;

public enum TeamCTF implements Team {
	BLUE, RED, NONE;
	
	@Override
	public String toString() {
		switch (this) {
		case BLUE:
			return "Blue";
		case RED:
			return "Red";
		default:
			return "None";
		}
	}
	
	public static Minecraft mc = Minecraft.getMinecraft();

	/**
	 * Returns the team of the player.
	 * @param player
	 * @return
	 */
	public static TeamCTF getTeam(String player) {
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		if (board.getPlayersTeam(player) == null) return NONE;
		if (board.getPlayersTeam(player).getRegisteredName().equals("BLUE")) {
			return BLUE;
		} else if (board.getPlayersTeam(player).getRegisteredName().equals("RED")) {
			return RED;
		} else {
			return NONE;
		}
	}
	
	public static TeamCTF teamFromString(String teamString) {
		if (TeamCTF.BLUE.toString().equalsIgnoreCase(teamString)) {
			return BLUE;
		} else if (TeamCTF.RED.toString().equalsIgnoreCase(teamString)){
			return RED;
		} else {
			return NONE;
		}
	}
	
	/**
	 * Returns the team of the current player.
	 */
	public static TeamCTF usersTeam() {
		return getTeam(Minecraft.getMinecraft().thePlayer.getDisplayName());
	}
	
	/**
	 * Returns the team that the player is not on.
	 */
	public static TeamCTF otherTeam() {
		if (usersTeam().equals(BLUE)) {
			return RED;
		} else if (usersTeam().equals(RED)) {
			return BLUE;
		}
		return NONE;
	}
	
	/**
	 * Returns the color code of the team.
	 * @return
	 */
	public String color() {
		switch (this) {
		case BLUE:
			return Format.process("#blue#");
		case RED:
			return Format.process("#red#");
		default:
			return Format.process("#white#");
		}
	}
	
	public int getWins() {
		switch (this) {
		case BLUE:
			return BoardHelper.getScore("Blue");
		case RED:
			return BoardHelper.getScore("Red");
		default:
			return -1;
		}
	}
}
