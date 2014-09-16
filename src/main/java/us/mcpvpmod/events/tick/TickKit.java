package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.stats.StatsKit;
import us.mcpvpmod.game.vars.VarsKit;
import us.mcpvpmod.gui.Medal;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickKit {

	public static void onTick(TickEvent event) {		
		VarsKit.putVars();
		StatsKit.getStats();
	}
	
}
