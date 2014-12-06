package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.gui.InfoBlock;

public class RenderKit {

	public static void onRender(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		// Render our InfoBlocks.
		for (InfoBlock block : InfoBlock.get(Server.KIT, StateKit.PLAY)) {
			block.display();
		}
	}
	
}
