package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatRaid {
	
	public static boolean getBalance = false;

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		if (message.equals(AllChat.msgLogged + "§r")) {
			Main.mc.thePlayer.sendChatMessage("/balance");
			getBalance = true;
		}
		
		if (message.matches(AllChat.msgLogged) && getBalance) {
			event.setCanceled(true);
			getBalance = false;
		}

	}
	
}
