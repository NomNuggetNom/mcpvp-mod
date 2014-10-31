package us.mcpvpmod.game.stats;

import us.mcpvpmod.util.BoardHelper;
import us.mcpvpmod.util.MCPVPMath;

public class StatsKit extends AllStats {

	public static int credits;
	public static int kills;
	public static int deaths;
	public static int worth;
	public static int streak;
	public static int assists;
	public static String kit = "Heavy";
	
	public static void reset() {
		streak = 0;
		assists = 0;
		kit = "Heavy";
	}
	
	/**
	 * Called every tick to update stats from the scoreboard.
	 */
	public static void getStats() {
		credits = BoardHelper.getScore("Credits:");
		kills 	= BoardHelper.getScore("Kills:");
		deaths 	= BoardHelper.getScore("Deaths:");
		worth 	= BoardHelper.getScore("Your worth:");
		streak  = BoardHelper.getScore("Killstreak:");
	}
	
	public static double getKD() {
		if (deaths != 0)
			return MCPVPMath.round((double)kills/(double)deaths, 3);
		return 0;
	}
}
