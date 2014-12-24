package us.mcpvpmod.events.tick;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import us.mcpvpmod.Server;

public class TickSab {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		Server.SAB.varProvider.putVars();
	}
	
}
