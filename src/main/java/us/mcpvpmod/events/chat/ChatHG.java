package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.core.CoreHG;
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatHG {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		if (message.matches(CoreHG.msgWelcome))
			StateHG.state = StateHG.WAIT;
		
		if (message.matches(CoreHG.msgVulernable))
			StateHG.state = StateHG.PLAY;
		
		if (message.startsWith("\00A7r\u00A7b" + Main.mc.thePlayer.getDisplayName())) {
			if (Vars.getInt("hg:kills") == -1)
				Vars.put("hg:kills", "" + 1);
			else
				Vars.put("hg:kills", "" + (Vars.getInt("hg:kills") + 1));
		}
	}
	
}
