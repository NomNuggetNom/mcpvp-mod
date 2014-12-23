package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.vars.VarsKit;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickKit {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {		
		VarsKit.putVars();
	}
	
}
