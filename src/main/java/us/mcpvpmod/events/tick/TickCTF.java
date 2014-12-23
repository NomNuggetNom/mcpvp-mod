package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickCTF {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {
		KillTimerCTF.check();
		//KillSpree.check();
	}
	
}
