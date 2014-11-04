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
		
		if (message.matches(CoreSab.reWin)) {
			Vars.put("sab:winner", InfoSab.formatWinners());
		}
		
		if (message.matches(CoreSab.reRole)) {
			Vars.put("sab:role", InfoSab.formatRole());
			
			// If the user is the detective, set the detective variable
			// to reflect it.
			if (message.contains("Detective")) {
				Vars.put("sab:detective", "You!");
			}
		}
		
		ChatTrigger.checkAll(message);
		
		// Sabotage state detection relies on chat messages.
		
		if (message.matches(CoreSab.reWelcome) 
				|| message.matches(CoreSab.reVoting)
				|| message.matches(CoreSab.reStarting)
				|| message.matches(CoreSab.reWait)) {
			StateSab.state = StateSab.PRE;
		}
		
		if (message.matches(CoreSab.reRole) || message.matches(CoreSab.reStart)) {
			StateSab.state = StateSab.PLAY;
		}
		
		if (message.matches(CoreSab.reSpectate)) {
			StateSab.state = StateSab.DEAD;
		}
		
		if (message.matches(CoreSab.reWin)) {
			StateSab.state = StateSab.POST;
		}
		
		// Dinging noises before the game starts.
		if (message.matches(CoreSab.reTime)) {
			int time = Integer.valueOf(message.replaceAll(CoreSab.reTime, "$1"));
			if (time <= 5 && time != 1)
				Main.mc.thePlayer.playSound("note.pling", 1.0F, 0.5F);
			else if (time == 1)
				Main.mc.thePlayer.playSound("note.pling", 1.0F, 0.9F);
		}

 	}
	
}
