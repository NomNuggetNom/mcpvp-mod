package us.mcpvpmod.events.chat;

import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatHG {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
	}
	
}
