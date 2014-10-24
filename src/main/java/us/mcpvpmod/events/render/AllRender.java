package us.mcpvpmod.events.render;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.CustomTexture;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.gui.screen.GuiIngameMCPVP;
import us.mcpvpmod.util.Data;

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
		if (ConfigHUD.showArmor || (Data.get("showArmor") != null && Boolean.valueOf(Data.get("showArmor"))))
			Main.armorDisplay.renderArmor();
		if (ConfigHUD.showPotion || (Data.get("showPotion") != null && Boolean.valueOf(Data.get("showPotion")))) {
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
		
		/*
		for (EntityPlayer player : ServerHelper.getPlayers()) {
			if (player == null || player.getDisplayName() == null) return;
			if (((AbstractClientPlayer)player).getLocationSkin() != null) {
		        ((AbstractClientPlayer)player).func_152121_a(MinecraftProfileTexture.Type.SKIN, 
		        		CustomTexture.get(player.getDisplayName() + ".skin", "http://skins.minecraft.net/MinecraftSkins/" + player.getDisplayName() + ".png"));
			} else {
				System.out.println(player.getDisplayName() + " has no skin!");
			}

			/*
	        ((AbstractClientPlayer)player).func_152121_a(MinecraftProfileTexture.Type.SKIN, 
	        		CustomTexture.get(player.getDisplayName() + ".skin", "http://skins.minecraft.net/MinecraftSkins/" + player.getDisplayName() + ".png"));
	        */
			/*
	        ((AbstractClientPlayer)player).func_152121_a(MinecraftProfileTexture.Type.SKIN, 
	        		((AbstractClientPlayer)player).getLocationSkin());
	        		*/
		//}
	


        //Minecraft.getMinecraft().renderEngine.loadTexture(location, this.getTexture());
	}
	
}
