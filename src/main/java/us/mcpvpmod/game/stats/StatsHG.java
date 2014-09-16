package us.mcpvpmod.game.stats;

import us.mcpvpmod.util.MCPVPMath;

public class StatsHG extends Stats {

	public static int kills = 0;
	public static int timeLeft = 0;
	public static String kit;
	public static String lastKilled;
	
	public static void reset() {
		kills = 0;
		timeLeft = 0;
		kit = "None";
		lastKilled = "";
	}
	
}
