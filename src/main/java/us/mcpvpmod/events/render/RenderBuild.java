package us.mcpvpmod.events.render;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.gui.InfoBlock;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderBuild {

	public static void onRender(RenderGameOverlayEvent event) {
		if (Main.mc.gameSettings.showDebugInfo) return;
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		// Render our InfoBlocks.
		for (InfoBlock block : InfoBlock.get(Server.BUILD, DummyState.NONE)) {
			block.display();
		}
	}
	
}
