package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.vars.VarsSab;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickSab {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		VarsSab.putVars();
	}
	
}
