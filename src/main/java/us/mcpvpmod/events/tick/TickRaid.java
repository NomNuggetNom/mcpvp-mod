package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.vars.VarsRaid;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickRaid {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		VarsRaid.putVars();
	}
	
}
