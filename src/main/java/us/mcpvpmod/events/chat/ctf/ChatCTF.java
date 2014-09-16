package us.mcpvpmod.events.chat.ctf;

import us.mcpvpmod.triggers.ChatTrigger;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatCTF {

	public static void onChat(ClientChatReceivedEvent event) {
		ChatHistory.handleChat(event);
	}
	
}
