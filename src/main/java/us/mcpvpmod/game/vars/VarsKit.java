package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.stats.StatsKit;
import us.mcpvpmod.util.MCPVPMath;

public class VarsKit {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	public static HashMap<String, Boolean> alerts = new HashMap<String, Boolean>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("kills", "" + StatsKit.kills);
		vars.put("deaths", "" + StatsKit.deaths);
		vars.put("worth", "" + StatsKit.worth);
		vars.put("credits", "" + StatsKit.credits);
		vars.put("streak", "" + StatsKit.streak);
		vars.put("killstreak", "" + StatsKit.streak);
		vars.put("ks", "" + StatsKit.streak);
		vars.put("kd", "" + StatsKit.getKD());
		//vars.put("assists", "" + StatsKit.assists);
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
