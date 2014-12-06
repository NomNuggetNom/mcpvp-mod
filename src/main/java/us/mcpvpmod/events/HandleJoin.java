package us.mcpvpmod.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.game.alerts.FriendAlerts;

public class HandleJoin {
	
	public static void onJoin(EntityJoinWorldEvent event) {
		if (!(event.entity instanceof EntityPlayer)) return;
		
		if (((EntityPlayer)event.entity).getDisplayName().equals(Main.mc.thePlayer.getDisplayName())) {
			Main.secondChat.clearChatMessages();
		}
		
		if (ConfigFriends.onlineNotifications) {
			FriendAlerts.check(event);
		}

	}
	
}
