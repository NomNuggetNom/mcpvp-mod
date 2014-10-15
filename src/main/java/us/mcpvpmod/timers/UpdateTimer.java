package us.mcpvpmod.timers;

import java.util.TimerTask;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigVersion;
import us.mcpvpmod.util.Format;
import us.mcpvpmod.version.MCPVPVersion;

public class UpdateTimer extends TimerTask {

	public void run() {
		if (!MCPVPVersion.updateAvailable()) return;
		
		if (Main.mc.thePlayer != null && Main.mc.theWorld != null && ConfigVersion.updateNotifications) {
			String msg = "";
			
			// Send update message.
			if (ConfigVersion.channel.equalsIgnoreCase("Main"))
				msg = Format.s("update-msg").replace("<VERSION>", Main.mcpvpVersion.main.mod).replace("<MCVERSION>", Main.mcpvpVersion.main.mc);
			 else if (ConfigVersion.channel.equalsIgnoreCase("Beta")) 
				msg = Format.s("update-msg").replace("<VERSION>", Main.mcpvpVersion.beta.mod).replace("<MCVERSION>", Main.mcpvpVersion.beta.mc);
			
			IChatComponent send = new ChatComponentText(Format.process(msg));
			send.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Format.s("update-url")));
			Main.mc.thePlayer.addChatMessage(send);
		}
	}
	
}
