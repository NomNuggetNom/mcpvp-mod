package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.core.CoreSab;
import us.mcpvpmod.game.info.InfoSab;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatSab {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		ChatTracker.checkAll(message);
		
		if (message.matches(CoreSab.RE_WIN)) {
			Vars.put("sab:winner", InfoSab.formatWinners());
		}
		
		if (message.matches(CoreSab.RE_ROLE)) {
			Vars.put("sab:role", InfoSab.formatRole());
			
			// If the user is the detective, set the detective variable
			// to reflect it.
			if (message.contains("Detective")) {
				Vars.put("sab:detective", "You!");
			}
		}
		
		ChatTrigger.checkAll(message);
		
		// Sabotage state detection relies on chat messages.
		
		if (message.matches(CoreSab.MSG_WELCOME) 
				|| message.matches(CoreSab.MSG_VOTING)
				|| message.matches(CoreSab.MSG_STARTING)
				|| message.matches(CoreSab.MSG_WAITING)) {
			StateSab.state = StateSab.PRE;
		}
		
		if (message.matches(CoreSab.RE_ROLE) || message.matches(CoreSab.MSG_START)) {
			StateSab.state = StateSab.PLAY;
		}
		
		if (message.matches(CoreSab.MSG_SPECTATE)) {
			StateSab.state = StateSab.DEAD;
		}
		
		if (message.matches(CoreSab.RE_WIN)) {
			StateSab.state = StateSab.POST;
		}
		
		// Dinging noises before the game starts.
		if (message.matches(CoreSab.RE_TIME)) {
			int time = Integer.valueOf(message.replaceAll(CoreSab.RE_TIME, "$1"));
			if (time <= 5 && time != 1)
				Main.mc.thePlayer.playSound("note.pling", 1.0F, 0.5F);
			else if (time == 1)
				Main.mc.thePlayer.playSound("note.pling", 1.0F, 0.9F);
		}

 	}
	
}
