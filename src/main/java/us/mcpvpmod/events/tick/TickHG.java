package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.checks.CheckTimeHG;
import us.mcpvpmod.game.vars.VarsHG;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHG {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		VarsHG.putVars();
		CheckTimeHG.updateTime();
	}
	
}
