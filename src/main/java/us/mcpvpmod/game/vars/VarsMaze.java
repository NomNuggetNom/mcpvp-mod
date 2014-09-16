package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.stats.StatsKit;
import us.mcpvpmod.game.stats.StatsMaze;

public class VarsMaze {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	public static HashMap<String, Boolean> alerts = new HashMap<String, Boolean>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("kills", "" + StatsMaze.kills);
		vars.put("hunger", "" + StatsMaze.princessHunger);
		vars.put("princesshunger", "" + StatsMaze.princessHunger);
		vars.put("princess_hunger", "" + StatsMaze.princessHunger);
		vars.put("princess-hunger", "" + StatsMaze.princessHunger);
		vars.put("health", "" + StatsMaze.princessHealth);
		vars.put("princesshealth", "" + StatsMaze.princessHealth);
		vars.put("princess_health", "" + StatsMaze.princessHealth);
		vars.put("princess-health", "" + StatsMaze.princessHealth);
		vars.put("baseX", "" + StatsMaze.baseX);
		vars.put("base x", "" + StatsMaze.baseX);
		vars.put("baseZ", "" + StatsMaze.baseZ);
		vars.put("base z", "" + StatsMaze.baseZ);
		vars.put("team", Vars.get("team"));
		vars.put("kit", Vars.get("kit"));
		//vars.put("assists", "" + StatsMaze.assists);
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
