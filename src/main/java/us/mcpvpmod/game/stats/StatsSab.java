package us.mcpvpmod.game.stats;

import us.mcpvpmod.game.vars.Vars;

public class StatsSab extends AllStats {
	
	public static void reset() {
		Vars.put("sab:role", "");
		Vars.put("sab:detective", "");
		Vars.put("sab:winner", "");
	}
	
}
