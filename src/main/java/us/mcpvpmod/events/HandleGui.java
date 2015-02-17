package us.mcpvpmod.events;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigMisc;
import us.mcpvpmod.gui.screen.GuiMultiplayerMCPVP;
import us.mcpvpmod.gui.screen.GuiServerList;

public class HandleGui {

	public static void onOpen(GuiOpenEvent event) {
		if (event.gui == null) return;
		if (Main.mc.currentScreen == null) return;
		
		if (event.gui instanceof GuiMultiplayer 
				&& Main.mc.currentScreen instanceof GuiMainMenu) {
			if (ConfigMisc.defaultMenu.equalsIgnoreCase("vanilla"))
				event.gui = new GuiMultiplayerMCPVP(Main.mc.currentScreen);
			else
				event.gui = new GuiServerList(Main.mc.currentScreen);
		}
	
	}
	
}
