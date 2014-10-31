package us.mcpvpmod.events.tick;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.gui.screen.GuiDisconnectedMCPVP;
import us.mcpvpmod.trackers.BoardTracker;
import cpw.mods.fml.common.gameevent.TickEvent;

public class AllTick {
	
	public static void onTick(TickEvent event) {
		AllVars.putVars();
		
		if (event.type == TickEvent.Type.RENDER && event.phase == event.phase.END) {
			if (Main.mc.currentScreen == null || Main.mc.currentScreen instanceof GuiChat) {
				Alerts.alert.showAlerts();
				Medal.showAll();
			}
		}
		
		for (BoardTracker tracker : BoardTracker.boardTrackers) {
			tracker.update();
		}
		
		if (Main.mc.currentScreen instanceof GuiDisconnected
				&& !(Main.mc.currentScreen instanceof GuiDisconnectedMCPVP)) {
			//Main.mc.displayGuiScreen(new GuiDisconnectedMCPVP(Main.mc.currentScreen, "a", new ChatComponentText("Hi")));
		}
	}
	
}
