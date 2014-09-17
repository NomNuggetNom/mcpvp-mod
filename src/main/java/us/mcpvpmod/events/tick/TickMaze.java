package us.mcpvpmod.events.tick;

import us.mcpvpmod.Main;
import us.mcpvpmod.game.checks.CheckTimeMaze;
import us.mcpvpmod.game.info.InfoMaze;
import us.mcpvpmod.game.stats.StatsMaze;
import us.mcpvpmod.game.vars.VarsMaze;
import us.mcpvpmod.util.BoardHelper;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickMaze {

	public static void onTick(TickEvent event) {
		VarsMaze.putVars();
		StatsMaze.getStats();
		CheckTimeMaze.updateTime();
	}
	
}
