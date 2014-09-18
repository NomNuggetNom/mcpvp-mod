package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.BoardTracker;
import cpw.mods.fml.common.gameevent.TickEvent;

/** 
* Tick handling for all servers. 
*/
public class AllTick {
	
	public static void onTick(TickEvent event) {
		// If we don't render at the end of the phase, we'll screw up other displays due to OpenGL settings.
		if (event.type == TickEvent.Type.RENDER && event.phase == event.phase.END) {
			// Show all Alerts and Medals
			Alerts.alert.showAlerts();
			Medal.showAll();
		}
		
		// Update our board trackers because they don't rely on events
		// TODO Make BoardTracker.updateAll()
		for (BoardTracker tracker : BoardTracker.boardTrackers) {
			tracker.update();
		}
	}
	
}
