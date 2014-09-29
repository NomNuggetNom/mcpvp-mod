package us.mcpvpmod.events.chat;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.core.CoreRaid;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.game.vars.VarsRaid;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatRaid {
	
	public static boolean getBalance = false;

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		if (message.equals(AllChat.msgLogged + "§r")) {
			System.out.println("logged in");
			Main.mc.thePlayer.sendChatMessage("/balance");
			getBalance = true;
		}
		
		if (message.matches(AllChat.msgLogged) && getBalance) {
			System.out.println("get");
			event.setCanceled(true);
			getBalance = false;
		}
		
		//System.out.println(VarsRaid.get("balance"));
	}
	
}
