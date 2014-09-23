package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.checks.CheckTimeHG;
import us.mcpvpmod.game.vars.VarsHG;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHG {

	public static void onTick(TickEvent event) {
		VarsHG.putVars();
		CheckTimeHG.updateTime();
	}
	
}
