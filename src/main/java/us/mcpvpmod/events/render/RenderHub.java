package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.InfoBox;

public class RenderHub {

	public static void onRender(@SuppressWarnings("unused") RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		for (InfoBox box : InfoBox.getShowingBoxes()) {
			box.display();
		}
	}
	
}
