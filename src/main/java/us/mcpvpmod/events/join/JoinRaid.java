package us.mcpvpmod.events.join;

import us.mcpvpmod.events.chat.IgnoreResult;

public class JoinRaid {

	public static void onJoin() {
		
		new IgnoreResult("/balance", ".*Balance:.*");
		
	}
	
}
