package us.mcpvpmod.events.chat;

import us.mcpvpmod.game.core.CoreSmash;
import us.mcpvpmod.game.state.StateSmash;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatSmash {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTrigger.checkAll(message);
		ChatTracker.checkAll(message);
		
		if (message.matches(CoreSmash.reRoundOver))
				StateSmash.state = StateSmash.POST;
	}
	
}