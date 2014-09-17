package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.stats.StatsKit;

public class VarsBuild {
	
	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("map", Vars.get("build:i.map.name"));
		vars.put("map name", Vars.get("build:i.map.name"));
		vars.put("id", Vars.get("build:i.map.id"));
		vars.put("map id", Vars.get("build:i.map.id"));
		vars.put("rank", Vars.get("build:i.map.rank"));
		vars.put("map rank", Vars.get("build:i.map.rank"));
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
