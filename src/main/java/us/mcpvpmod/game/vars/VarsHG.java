package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.info.InfoHG;

public class VarsHG {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("time", InfoHG.getFormattedTime());
		vars.put("kit", Vars.get("hg:kit"));
		vars.put("remain", Vars.get("hg:remain"));
		vars.put("tracking", Vars.get("hg:tracking"));
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