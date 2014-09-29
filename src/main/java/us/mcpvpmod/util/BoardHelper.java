package us.mcpvpmod.util;

import java.util.Collection;
import java.util.Iterator;

import us.mcpvpmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class BoardHelper {

	/**
	 * Returns the "title" of the scoreboard's display.
	 */
	public static String getBoardTitle() {
		if (Main.mc.theWorld == null) return "";
		if (Main.mc.theWorld.getScoreboard() == null) return "";
		
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		if (board != null) {
			// Grab the main objective of the scoreboard.
	        ScoreObjective titleObjective = board.func_96539_a(1);
	        
	        // Null check. For some reason, sometimes _a(0) works and other times _a(1) works.
	        if (board.func_96539_a(0) != null) {
				return board.func_96539_a(0).getDisplayName();
	        } else if (board.func_96539_a(1) != null) {
	        	return board.func_96539_a(1).getDisplayName();
	        }
		}
		return "";
	}
	
	/**
	 * Returns the score of the "team" (really a fake player) on the scoreboard.
	 */
	public static int getScore(String displayName) {
		if (Main.mc.theWorld == null) return -1;
		if (Main.mc.theWorld.getScoreboard() == null) return -1;
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		
		if (board != null) {
			// Grab the main objective of the scoreboard.
	        ScoreObjective objective = board.func_96539_a(1);
	        
	        // Collection of all scoreboard teams.
	        Collection teams = board.func_96534_i(objective);
	        
	        // Iterate through the collection of entries.
	        Iterator iterator = teams.iterator();
	        while (iterator.hasNext()) {
		        Score score = (Score)iterator.next();
		        
		        // Check if the name of the "team" (player) is what we're looking for.
		        // In actuality, it would be more precise to use an equals check e.g.:
		        // if (score1.getPlayerName().equals(team)) {
		        if (score.getPlayerName().contains(displayName)) {
		        	
		        	// Get the score of the "team" (player) and return it.
			        return score.getScorePoints();
		        }
	        }
		}
	    return -1;
	}
	
	public static Collection getTeams() {
		if (Main.mc.theWorld == null) return null;
		if (Main.mc.theWorld.getScoreboard() == null) return null;
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		
		return board.func_96534_i(board.func_96539_a(1));
	}
	
	public static boolean hasTeams() {
		return (getTeams() != null);
	}
	
	/**
	 * Prints all the objectives.
	 */
	public static void printObjectives() {
		if (Main.mc.theWorld == null) return;
		if (Main.mc.theWorld.getScoreboard() == null) return;
		if (Main.mc.theWorld.getScoreboard().getObjectiveNames() == null) return;
		
		System.out.println(Main.mc.theWorld.getScoreboard().getObjectiveNames());
	}
	
	/**
	 * @param player The name of the player.
	 * @return The team name of the player.
	 */
	public static String getTeamName(String player) {
		if (Main.mc.theWorld == null) return "";
		if (Main.mc.theWorld.getScoreboard() == null) return "";
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		
		if (board.getPlayersTeam(player) == null) return "null";
		return board.getPlayersTeam(player).getRegisteredName();
	}
	
	/**
	 * @param player The team color of the player.
	 * @return The color prefix, if there is one.
	 */
	public static String getTeamColor(String player) {
		if (Main.mc.theWorld == null) return "";
		if (Main.mc.theWorld.getScoreboard() == null) return "";
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		
		if (board.getPlayersTeam(player) == null) return Format.process("#r#");
		return board.getPlayersTeam(player).getColorPrefix();
	}
	
}
