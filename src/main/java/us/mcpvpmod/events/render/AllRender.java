package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.info.DisplayAnchor;
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
		
		// Render our armor and potion display.
		Main.armorDisplay.renderArmor();
		Main.potionDisplay.displayPotions(event);
		PotionDisplay.displayStrings();
		
		if (Selectable.selected != null) {
			Selectable.selected.outline();
		}
		
		Main.friendsList.display();
		//System.out.println(InfoBlock.get(Format.process("#bold##u#CTF")).getHeight());
		//DisplayAnchor.anchors.clear();
		//Main.friendsList.anchorTo(InfoBlock.get(Format.process("#bold##u#CTF")), 'r');
		//Main..anchorTo(InfoBlock.get(Format.process("#bold##u#CTF")), 'd');
		


	}
	
}
