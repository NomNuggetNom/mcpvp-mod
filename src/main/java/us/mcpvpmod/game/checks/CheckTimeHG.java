package us.mcpvpmod.game.checks;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.hg.ConfigHGSelect;
import us.mcpvpmod.game.info.InfoHG;
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.Format;

public class CheckTimeHG {

	public static int oldTime = 0;

	public static void updateTime() {
		int time = InfoHG.getTime();
		if (oldTime != time) {
			checkTime();
			oldTime = time;
		}
	}
	
	public static void checkTime() {

		if (oldTime <= 5
				&& oldTime != -1 
				&& StateHG.getState().equals(StateHG.PRE)
				&& ConfigHGSelect.selectMode.equals(Format.s("hg.config.select.selectMode.m.start"))) {
			
			if (Vars.get("hg:kit").equals("")) {
				Main.mc.thePlayer.sendChatMessage("/kit " + ConfigHGSelect.selectKit);
			}
		}
	}
	
}
