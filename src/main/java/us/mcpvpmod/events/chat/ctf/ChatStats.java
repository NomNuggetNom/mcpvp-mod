package us.mcpvpmod.events.chat.ctf;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.trackers.ChatTracker;

/**
 * Scans any chat outside of the "chatBox," like stats and team info.
 * @author NomNuggetNom
 *
 */
public class ChatStats {

	/**
	 * Handles the repeating stat and team messages.
	 * @param event
	 */
	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		for (ChatTracker checker : ChatTracker.chatTrackers) {
			checker.check(message);
		}

	}
	
}
