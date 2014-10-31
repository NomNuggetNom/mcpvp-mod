package us.mcpvpmod.events;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.screen.GuiChooseMultiplayer;

public class HandleGui {

	public static void onOpen(GuiOpenEvent event) {
		if (event.gui == null) return;
		if (Main.mc.currentScreen == null) return;
		
		if (event.gui.toString().contains("GuiMultiplayer") 
				&& Main.mc.currentScreen.getClass() == GuiMainMenu.class) {
			// Opened the Multiplayer GUI from the main menu.
			event.gui = new GuiChooseMultiplayer(Main.mc.currentScreen);
		}
	}
	
}
