package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.vars.Vars;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickCTF {

	public static void onTick(TickEvent event) {
		KillTimerCTF.check();
		//KillSpree.check();
		StatsCTF.getStats();
	}
	
}
