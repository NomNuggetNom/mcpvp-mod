package us.mcpvpmod.events.tick;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.checks.CheckTimeHG;

public class TickHG {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		Server.HG.varProvider.putVars();
		CheckTimeHG.updateTime();
	}
	
}
