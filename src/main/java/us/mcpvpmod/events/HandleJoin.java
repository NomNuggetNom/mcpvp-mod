package us.mcpvpmod.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.config.mcpvp.ConfigFriends;
import us.mcpvpmod.config.mcpvp.ConfigSelect;
import us.mcpvpmod.game.alerts.FriendAlerts;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.FriendsList;

public class HandleJoin {

	public static String send = "/59D8AE933F582F5441C6F5C8FA19CBA2";
	
	public static void onJoin(EntityJoinWorldEvent event) {
		if (!(event.entity instanceof EntityPlayer)) return;
		
		if (((EntityPlayer)event.entity).getDisplayName().equals(Main.mc.thePlayer.getDisplayName())) {
			System.out.println("Joined.");
			
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
