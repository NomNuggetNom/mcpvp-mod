package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.gui.Format;

public class VarsSab {
	
	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("karma", "");
		vars.put("role", Format.process("#r#") + Vars.get("sab:role") + Format.process("#r#"));
		vars.put("remain", Vars.get("sab:remain"));
		vars.put("detective", Vars.get("sab:detective"));
		vars.put("winner", Vars.get("sab:winner"));
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
