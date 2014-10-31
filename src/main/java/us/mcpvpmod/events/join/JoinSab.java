package us.mcpvpmod.events.join;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.sab.ConfigSabSelect;
import us.mcpvpmod.game.state.StateSab;

public class JoinSab {

	public static void onJoin() {
		
		// Sab has trouble detecting states, so it is set
		// to PRE on joining.
		StateSab.state = StateSab.PRE;

		// Auto-sab.
		if (ConfigSabSelect.autoSab)
			Main.mc.thePlayer.sendChatMessage("/sab");
		
	}
	
}
