package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateSab;

public class RenderSab {

	public static void onRender(RenderGameOverlayEvent event) {	
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		StateSab.getState().render();
	}
	
}
