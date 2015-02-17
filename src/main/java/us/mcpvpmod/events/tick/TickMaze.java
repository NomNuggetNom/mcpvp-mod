package us.mcpvpmod.events.tick;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.checks.CheckTimeMaze;

public class TickMaze {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		Server.MAZE.varProvider.putVars();
		CheckTimeMaze.updateTime();
	}

	
}
