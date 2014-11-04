package us.mcpvpmod.events.render;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.CustomTextureAsync;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.gui.screen.GuiIngameMCPVP;
import us.mcpvpmod.util.Data;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

/**
 * Render handling for all servers.
*/
public class AllRender {
	
	public static void onRender(RenderGameOverlayEvent event) {
		
		// If this doesn't render during the TEXT phase, other displays will be screwed up due to OpenGL settings.
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		// Draw the split chat.
		Main.secondChat.drawChat(Main.mc.ingameGUI.getUpdateCounter());
		
		// Prevent everything else from being rendered when debug is showing.
		if (Main.mc.gameSettings.showDebugInfo)
			return;
		
		// Render the armor display.
		if (ConfigHUD.showArmor || (Data.get("showArmor") != null && Boolean.valueOf(Data.get("showArmor")))) {
			Main.start("mcpvp", "armorDisplay");
			Main.armorDisplay.renderArmor();
			Main.end(2);
		}
		
		// Render the potion display.
		if (ConfigHUD.showPotion || (Data.get("showPotion") != null && Boolean.valueOf(Data.get("showPotion")))) {
			Main.start("mcpvp", "potionDisplay");
			Main.potionDisplay.displayPotions(event);
			PotionDisplay.displayStrings();
			Main.end(2);
		}
		
		// Outline the current selectable.
		if (Selectable.selected != null)
			Selectable.selected.outline();
		
		// Display the friend's list.
		if (ServerHelper.onMCPVP()) {
			Main.start("mcpvp", "friendsList");
			Main.friendsList.display();
			Main.end(2);
		}

		// Hi-jack the GuiIngameMenu and inject a new one.
		if (Main.mc.currentScreen instanceof GuiIngameMenu)
			Main.mc.displayGuiScreen(new GuiIngameMCPVP());

		// Checking the time imposes a limit to the frequency of the event.
		if (ConfigHUD.fixSkins && System.currentTimeMillis() % 10 == 0)  {
			Main.start("mcpvp", "skinFixer");
			fixSkins();
			Main.end(2);
		}
		
	}
	
	/**
	 * Cycles through each player, downloads their skin, and assigns it to the location
	 * in their profile. The downloading is performed asynchronously, but can still
	 * cause lag due to the creation & reading of files. 
	 * <br><br>
	 * Called every render tick because the image has to download, and because Minecraft
	 * resets the location of the skin every tick.
	 */
	public static void fixSkins() {

		// Cycle through every player on the server.
		for (EntityPlayer player : ServerHelper.getPlayersFromWorld()) {
			// Safeguard because players are sometimes null...
			if (player == null) continue;
			
			// The name of the player without colors.
			String name = player.getDisplayName().replaceAll("\u00A7.", "");
			// The URL to download from.
			String url = "http://skins.minecraft.net/MinecraftSkins/" + name + ".png";
			// The Steve skin is the backup.
			ResourceLocation steve = ((AbstractClientPlayer) player).locationStevePng;
			
			 // Store it as "username.skin"
			ResourceLocation skin = CustomTextureAsync.get(name + ".skin", url, steve); 
			
			// Assign the location of the skin.
			((AbstractClientPlayer) player).func_152121_a(MinecraftProfileTexture.Type.SKIN, skin);
			
		}
	}
}
