package us.mcpvpmod.events.chat;

import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatMaze {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		for (ChatTrigger trigger : ChatTrigger.triggers) {
			trigger.check(message);
		}
		
		for (ChatTracker checker : ChatTracker.chatTrackers) {
			checker.check(message);
		}
		

		
	}
	
}
