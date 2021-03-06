package us.mcpvpmod.events.tick;

import net.minecraft.client.gui.GuiChat;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.BoardTracker;
import cpw.mods.fml.common.gameevent.TickEvent;

public class AllTick {

	public static void onTick(TickEvent event) {
		
		if (System.currentTimeMillis() % 10 == 0) {
			Main.start("mcpvp", "vars");
			AllVars.putVars();
			Main.end(2);
		}
		
		if (event.type == TickEvent.Type.RENDER && event.phase == event.phase.END) {
			if (Main.mc.currentScreen == null || Main.mc.currentScreen instanceof GuiChat) {
				Main.start("mcpvp", "alerts");
				Alerts.alert.showAlerts();
				Medal.showAll();
				Main.end(2);
			}
		}
		
		for (BoardTracker tracker : BoardTracker.boardTrackers) {
			Main.start("mcpvp", "trackers");
			tracker.update();
			Main.end(2);
		}
		
	}
	
}
