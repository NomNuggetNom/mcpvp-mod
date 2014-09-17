package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatMaze {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		for (ChatTrigger trigger : ChatTrigger.triggers) {
			trigger.check(message);
		}
		
		for (ChatTracker checker : ChatTracker.chatTrackers) {
			checker.check(message);
		}
		
		if (message.equals("§r§aClick on or type the kit name to pick a kit:§r")) {
			System.out.println("hi");
			if (Vars.get("maze:i.kit").equals("")) {
				Main.mc.thePlayer.sendChatMessage(ConfigMazeSelect.selectClass);
			}
			
		}
	}
	
}
