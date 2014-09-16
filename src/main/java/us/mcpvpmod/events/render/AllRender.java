package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.gui.FriendsBlock;


public class AllRender {
	
	public static void onRender(RenderGameOverlayEvent event) {
		if (Main.mc.isSingleplayer()) return;
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		FriendsBlock.display();
	}
	
}
