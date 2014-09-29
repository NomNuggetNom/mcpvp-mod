package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateMaze;

public class RenderMaze {

	public static void onRender(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;		
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		StateMaze.getState().render();
	}
	
}
