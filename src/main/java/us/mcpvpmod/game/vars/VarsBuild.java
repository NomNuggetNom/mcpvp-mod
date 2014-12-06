package us.mcpvpmod.game.vars;

import java.util.HashMap;

public class VarsBuild {
	
	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("map", Vars.get("build:map.name"));
		vars.put("map-name", Vars.get("build:map.name"));
		vars.put("id", Vars.get("build:map.id"));
		vars.put("map-id", Vars.get("build:map.id"));
		vars.put("rank", Vars.get("build:map.rank"));
		vars.put("map-rank", Vars.get("build:map.rank"));
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
