package us.mcpvpmod.game.stats;

import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.BoardHelper;
import us.mcpvpmod.util.MCPVPMath;

public class StatsMaze extends Stats {

	public static int kills = 0;
	public static int assists = 0;
	public static int princessHealth = 0;
	public static int princessHunger = 0;
	public static int baseX = 0;
	public static int baseZ = 0;
	public static String kit = "None";
	public static String team = "";
	
	public static void reset() {
		kills = 0;
		assists = 0;
		princessHealth = 100;
		princessHunger = 100;
		baseX = 0;
		baseZ = 0;
		kit = "None";
		team = "";
	}
	
	/**
	 * Called every tick to update stats from the scoreboard.
	 */
	public static void getStats() {
		baseX = Vars.getInt("maze:i.base.x");
		baseZ = Vars.getInt("maze:i.base.z");
		princessHealth = Vars.getInt("maze:i.princess.health");
		princessHunger = Vars.getInt("maze:i.princess.hunger");
	}
	
}
