package us.mcpvpmod.events.render;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.gui.screen.GuiIngameMCPVP;

/**
 * Render handling for all servers.
*/
public class AllRender {
	
	public static void onRender(RenderGameOverlayEvent event) {
		
		// If we don't render during the TEXT phase, we'll screw up other displays due to OpenGL settings.
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		// Draw the split chat.
		Main.secondChat.drawChat(Main.mc.ingameGUI.getUpdateCounter());
		
		// Prevent everything else from being rendered when debug is showing.
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		// Display our FriendsBlock.
		//FriendsBlock.display();
		
		// Render our armor and potion display.
		if (ConfigHUD.showArmor)
			Main.armorDisplay.renderArmor();
		if (ConfigHUD.showPotion) {
			Main.potionDisplay.displayPotions(event);
			PotionDisplay.displayStrings();
		}

		if (Selectable.selected != null) {
			Selectable.selected.outline();
		}
		
		if (ServerHelper.onMCPVP())
			Main.friendsList.display();
		
		if (Main.mc.currentScreen instanceof GuiIngameMenu) {
			Main.mc.displayGuiScreen(new GuiIngameMCPVP());
		}
	}
	
}
