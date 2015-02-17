package us.mcpvpmod.events.render;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.gui.InfoBlock;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderRaid {

	public static void onRender(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		// Render our InfoBlocks.
		for (InfoBlock block : InfoBlock.get(Server.RAID, DummyState.NONE)) {
			block.display();
		}
	}
	
}
