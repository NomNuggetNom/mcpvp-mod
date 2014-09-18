package us.mcpvpmod.events.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.gui.FriendsBlock;

/**
 * Render handling for all servers.
*/
public class AllRender {
	
	public static void onRender(RenderGameOverlayEvent event) {
		// If we don't render during the TEXT phase, we'll screw up other displays due to OpenGL settings.
		// TODO: fix OpenGL interference.
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		// Display our FriendsBlock regardless of server.
		FriendsBlock.display();
	}
	
}
