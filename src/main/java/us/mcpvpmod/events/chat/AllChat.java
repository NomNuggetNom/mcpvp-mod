package us.mcpvpmod.events.chat;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.config.mcpvp.ConfigChat;
import us.mcpvpmod.mgi.MGIEvent;
import us.mcpvpmod.triggers.ChatTrigger;

/**
 * Chat handling for all servers.
 */ 
public class AllChat {

	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		// Check for removal of chat.
		if (removeChat(message)) {
			event.setCanceled(true);
		} else {
			// Censor chat.
			event.message = new ChatComponentText(censorChat(event.message.getFormattedText().replaceAll("§", "\u00A7")));
		}
		
		/*
		if (MGIEvent.isMGIEvent(message)) {
			MGIEvent.decompile(message).handle();
		}
		*/
	}
	
	/**
	 * @param message The message to consider moving.
	 * @return Whether the message should be removed in accordance with configuration settings.
	 */
	public static boolean removeChat(String message) {
		// Cycle through the config and check if our message contains any specified words.
		for (String string : ConfigChat.removeWords) {
			if (message.contains(string)) {
				return true;
			}
		}
		
		// This removes game tips. Might interfere un-intentionally with server messages that are blue.
		// TODO: Resolve possible conflics.
		if (ConfigCTFChat.removeTips && message.startsWith("\u00A73")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Censors chat by replacing filterWords with asterisks.
	 * @param message The message to censor.
	 * @return The censored chat.
	 */
	public static String censorChat(String message) {
		String text = message;
		for (String string : ConfigChat.filterWords) {
			// Use (?i) to ignore case. A little bit hacky.
			text = text.replaceAll("(?i)" + string, "*****");
		}
		return text;
	}
	
}
