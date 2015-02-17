package us.mcpvpmod.events.render;

import us.mcpvpmod.game.state.StateHS;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderHS {

	public static void onRender(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		StateHS.getState().render();
	}
	
}
