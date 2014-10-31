package us.mcpvpmod.game.vars;

import java.util.HashMap;

public class VarsRaid {
	
	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("balance", Vars.get("raid:balance"));
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
