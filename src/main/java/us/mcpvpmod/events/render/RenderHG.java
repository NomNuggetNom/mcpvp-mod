package us.mcpvpmod.events.render;

import us.mcpvpmod.Main;
import us.mcpvpmod.game.state.StateHG;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderHG {

	public static void onRender(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		StateHG.getState().render();
	}
	
}
