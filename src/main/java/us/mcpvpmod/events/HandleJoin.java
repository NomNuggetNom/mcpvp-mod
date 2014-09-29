package us.mcpvpmod.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.all.ConfigSelect;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.alerts.FriendAlerts;
import us.mcpvpmod.game.vars.Vars;

public class HandleJoin {
	
	public static void onJoin(EntityJoinWorldEvent event) {
		if (!(event.entity instanceof EntityPlayer)) return;
		
		if (((EntityPlayer)event.entity).getDisplayName().equals(Main.mc.thePlayer.getDisplayName())) {
			//System.out.println("Joined.");
			
			// Auto-tagging implementation
			if (Server.getServer() != Server.NONE) {
				if (ConfigSelect.autoTagB) {
					Main.mc.thePlayer.sendChatMessage("/tag " + ConfigSelect.autoTag);
				}
			}
		}
		
		if (ConfigFriends.onlineNotifications) {
			FriendAlerts.check(event);
		}
		
	}
	
}
