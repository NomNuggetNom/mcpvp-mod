package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.info.InfoHG;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.stats.StatsKit;
import us.mcpvpmod.util.MCPVPMath;

public class VarsHG {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		//vars.put("kills", Vars.get("kit:kills"));
		vars.put("time", InfoHG.getFormattedTime());
		vars.put("kit", Vars.get("hg:kit"));
	}
	
	/**
	 * Used to get variables. Avoids returning null.
	 * @param string The key of the variable to get.
	 * @return The value of the stored variable.
	 */
	public static String get(String string) {
		if (vars.keySet().contains(string)) {
			return vars.get(string);
		} else {
			return "";
		}
	}

}