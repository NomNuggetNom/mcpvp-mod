package us.mcpvpmod.events.join;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.smash.ConfigSmashSelect;
import us.mcpvpmod.game.state.StateSmash;

public class JoinSmash {

	public static void onJoin() {
		StateSmash.state = StateSmash.PRE;

		if (ConfigSmashSelect.autoSelect)
			Main.mc.thePlayer.sendChatMessage("/kit " + ConfigSmashSelect.selectKit);
	}
	
}
