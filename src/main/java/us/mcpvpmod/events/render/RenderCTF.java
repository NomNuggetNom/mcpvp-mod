package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateCTF;

public class RenderCTF {

	public static void onRender(RenderGameOverlayEvent.Post event) {	
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		StateCTF.getState().render();
	}
	
}
