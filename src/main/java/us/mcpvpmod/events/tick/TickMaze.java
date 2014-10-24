package us.mcpvpmod.events.tick;

import net.minecraft.client.gui.GuiPlayerInfo;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.game.checks.CheckTimeMaze;
import us.mcpvpmod.game.stats.StatsMaze;
import us.mcpvpmod.game.vars.VarsMaze;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickMaze {

	public static void onTick(TickEvent event) {
		VarsMaze.putVars();
		StatsMaze.getStats();
		CheckTimeMaze.updateTime();
	}

	
}
