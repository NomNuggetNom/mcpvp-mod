package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.stats.StatsKit;
import us.mcpvpmod.game.vars.VarsKit;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickKit {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {		
		VarsKit.putVars();
		StatsKit.getStats();
	}
	
}
