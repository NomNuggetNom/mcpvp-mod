package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.vars.VarsBuild;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickBuild {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		VarsBuild.putVars();
	}
	
}
