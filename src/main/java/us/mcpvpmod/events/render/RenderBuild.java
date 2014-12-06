package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.gui.InfoBlock;

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
