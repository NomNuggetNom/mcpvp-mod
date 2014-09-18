package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.game.core.CoreSab;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatSab {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);

		// Sabotage state detection relies on chat messages.
		if (message.matches(CoreSab.reWelcome) 
				|| message.matches(CoreSab.reVoting)
				|| message.matches(CoreSab.reStarting)
				|| message.matches(CoreSab.reWait)) {
			StateSab.state = StateSab.PRE;
		}
		
		if (message.matches(CoreSab.reRole)) {
			StateSab.state = StateSab.PLAY;
		}
		
		if (message.matches(CoreSab.reSpectate)) {
			StateSab.state = StateSab.DEAD;
		}
 	}
	
}
