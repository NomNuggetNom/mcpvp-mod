package us.mcpvpmod.events.chat;

import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatBuild {

	public static String reMap = "\u00A7r\u00A7fYou have been teleported to: \u00A7r\u00A7a(.*) \u00A7r\u00A77\\(#(\\d*)\\)\u00A7r";
	
	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		if (message.equals("\u00A7r\u00A76\u00A7lWelcome to the \u00A7r\u00A76\u00A7lMinecraftBuild.com\u00A7r\u00A76\u00A7l Build Server!\u00A7r")) {
		}
	}
	
}
