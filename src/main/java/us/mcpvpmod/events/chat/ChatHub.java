package us.mcpvpmod.events.chat;

import us.mcpvpmod.triggers.ChatTrigger;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatHub {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		for (ChatTrigger trigger : ChatTrigger.triggers) {
			trigger.check(message);
		}
	}
	
}
