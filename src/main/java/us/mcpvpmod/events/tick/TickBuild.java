package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.vars.VarsBuild;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickBuild {

	public static void onTick(TickEvent event) {
		// Update our variables.
		// For build, this might not be needed, since all variables rely on ChatTrackers.
		// TODO: Consider removing to improve performance.
		VarsBuild.putVars();
	}
	
}
