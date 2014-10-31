package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.stats.StatsKit;

public class VarsKit {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("kills", Vars.get("kit:kills"));
		vars.put("deaths", Vars.get("kit:deaths"));
		vars.put("worth", Vars.get("kit:worth"));
		vars.put("credits", Vars.get("kit:credits"));
		vars.put("streak", Vars.get("kit:ks"));
		vars.put("killstreak", Vars.get("kit:ks"));
		vars.put("ks", Vars.get("kit:ks"));
		vars.put("kd", "" + StatsKit.getKD());
	}
	
	/**
	 * Used to get variables. Avoids returning null.
	 * @param string The key of the variable to get.
	 * @return The value of the stored variable.
	 */
	public static String get(String string) {
		if (vars.keySet().contains(string))
			return vars.get(string);
		return "";
	}
	
	/**
	 * Resets the variable storage by clearing it. 
	 */
	public static void reset() {
		vars.clear();
	}
	
}