package us.mcpvpmod.events.render;

import us.mcpvpmod.game.state.StateHG;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderHG {

	public static void onRender(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		StateHG.getState().render();
	}
	
}
