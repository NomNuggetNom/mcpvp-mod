package us.mcpvpmod.events.render;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.FriendsBlock;
import us.mcpvpmod.gui.info.Selectable;

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
		
		// Render our armor display.
		ArmorDisplay.renderArmor();
		
		if (Selectable.selected != null) {
			Selectable.selected.drawOutline();
		}

	}
	
}
