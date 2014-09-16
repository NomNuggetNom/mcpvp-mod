package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.BoardTracker;
import cpw.mods.fml.common.gameevent.TickEvent;

public class AllTick {
	
	public static void onTick(TickEvent event) {
		if (event.type == TickEvent.Type.RENDER && event.phase == event.phase.END) {
			Alerts.alert.showAlerts();
			Medal.showAll();
		}
		
		for (BoardTracker tracker : BoardTracker.boardTrackers) {
			tracker.update();
		}
	}
	
}
