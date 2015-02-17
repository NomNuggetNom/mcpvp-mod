package us.mcpvpmod.game.stats;

import us.mcpvpmod.util.BoardHelper;
import us.mcpvpmod.util.MCPVPMath;

public class StatsKit extends AllStats {

	public static double getKD() {
		if (BoardHelper.getScore("Deaths:") != 0)
			return MCPVPMath.round((double)BoardHelper.getScore("Kills:")/(double)BoardHelper.getScore("Deaths:"), 3);
		return 0;
	}
}
