package us.mcpvpmod.events.tick;

import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.BoardTracker;

public class AllTick {

	public static void onTick(TickEvent event) {
		
		if (System.currentTimeMillis() % 10 == 0) {
			Main.start("mcpvp", "vars");
			Server.ALL.varProvider.putVars();
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
