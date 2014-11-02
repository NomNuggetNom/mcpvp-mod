package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickCTF {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		KillTimerCTF.check();
		//KillSpree.check();
		StatsCTF.getStats();
	}
	
}
