package us.mcpvpmod.timers;

import java.util.TimerTask;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.events.chat.IgnoreResult;

public class PingTimer extends TimerTask {

	public void run() {
		if (Main.mc.thePlayer == null 
				|| Main.mc.theWorld == null 
				|| Server.getServer() == Server.NONE) 
			return;
		
		new IgnoreResult("/pingtest", ".*Testing Ping.*", "\u00A7aPing: .*");
	}
	
}
