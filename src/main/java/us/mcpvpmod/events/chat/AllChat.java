package us.mcpvpmod.events.chat;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.config.mcpvp.ConfigChat;
import us.mcpvpmod.mgi.MGIEvent;
import us.mcpvpmod.triggers.ChatTrigger;

public class AllChat {

	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		if (MGIEvent.isMGIEvent(message)) {
			MGIEvent.decompile(message).handle();
		}

		
		if (removeChat(message)) {
			event.setCanceled(true);
		} else {
			event.message = new ChatComponentText(censorChat(event.message.getFormattedText().replaceAll("§", "\u00A7")));
		}
	}
	
	/**
	 * Removing chat.
	 * @param message
	 * @return
	 */
	public static boolean removeChat(String message) {
		for (String string : ConfigChat.removeWords) {
			if (message.contains(string)) {
				return true;
			}
		}
		
		if (ConfigCTFChat.removeTips && message.startsWith("\u00A73")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Filtering (censoring) chat.
	 * @param message
	 * @return
	 */
	public static String censorChat(String message) {
		String text = message;
		for (String string : ConfigChat.filterWords) {
				text = text.replaceAll("(?i)" + string, "*****");
		}
		return text;
	}
	
}
