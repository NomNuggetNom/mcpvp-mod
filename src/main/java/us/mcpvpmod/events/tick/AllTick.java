package us.mcpvpmod.events.tick;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiIngameMenu;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.all.ConfigVersion;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.gui.screen.GuiIngameMCPVP;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.version.MCPVPVersion;
import cpw.mods.fml.common.gameevent.TickEvent;

public class AllTick {
	
	public static void onTick(TickEvent event) {
		AllVars.putVars();
		
		if (event.type == TickEvent.Type.RENDER && event.phase == event.phase.END) {
			Alerts.alert.showAlerts();
			Medal.showAll();
		}
		
		for (BoardTracker tracker : BoardTracker.boardTrackers) {
			tracker.update();
		}
	}
	
}
