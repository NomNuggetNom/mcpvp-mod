package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.checks.CheckTimeMaze;
import us.mcpvpmod.game.stats.StatsMaze;
import us.mcpvpmod.game.vars.VarsMaze;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickMaze {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		VarsMaze.putVars();
		StatsMaze.getStats();
		CheckTimeMaze.updateTime();
	}

	
}
