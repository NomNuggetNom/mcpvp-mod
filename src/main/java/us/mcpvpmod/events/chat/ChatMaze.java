package us.mcpvpmod.events.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;
import us.mcpvpmod.util.Format;

public class ChatMaze {

	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTrigger.checkAll(message);
		ChatTracker.checkAll(message);
		
		// Auto-Select support
		if (message.equals("§r§aClick on or type the kit name to pick a kit:§r")
				&& ConfigMazeSelect.selectMode.equals(Format.s("maze.config.select.selectMode.m.join"))) {

			// Select kit
			if (Vars.get("maze:kit").equals("")) {
				Main.mc.thePlayer.sendChatMessage("/kit " + ConfigMazeSelect.selectClass);
			}
			
			// Select team
			if (Vars.get("maze:team").equals("")) {
				Main.mc.thePlayer.sendChatMessage("/team " + ConfigMazeSelect.selectTeam);
			}
			
		}
	}
	
}
