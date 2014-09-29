package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.hg.ConfigHGSelect;
import us.mcpvpmod.game.core.CoreHG;
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatHG {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		if (message.matches(CoreHG.msgWelcome)) {
			StateHG.state = StateHG.WAIT;
		}
		
		if (message.matches(CoreHG.msgChoose)) {
			
			if (ConfigHGSelect.selectMode.equalsIgnoreCase("Select On Join")) {
				Main.mc.thePlayer.sendChatMessage("/kit " + ConfigHGSelect.selectKit);
			}
		}
		
		if (message.matches(CoreHG.msgVulernable)) {
			StateHG.state = StateHG.PLAY;
		}
	}
	
}
