package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.game.info.AllInfo;
import us.mcpvpmod.gui.InfoBox;

/**
 * Variables for every server. Used in processing
 * an {@link InfoBox}, whereas {@link Vars} are
 * not. In an {@link InfoBox}, usage of <code>{var_name}</code>
 * is translated into a direct calling of {@link #get(String)} where
 * the argument is <code>var_name</code>. Identical calls are performed
 * in the server-dependent variable class if null is returned
 * from this class.
 */
public class AllVars implements IVarProvider {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public void putVars() {
		vars.put("x", "" + AllInfo.getX());
		vars.put("y", "" + AllInfo.getY());
		vars.put("z", "" + AllInfo.getZ());
		vars.put("ip", AllInfo.getIP());
		vars.put("short ip", this.get("short-ip"));
		vars.put("short-ip", ServerHelper.shortServerIP());
		vars.put("short_ip", ServerHelper.shortServerIP());
		vars.put("s-ip", ServerHelper.shortServerIP());
		vars.put("s_ip", ServerHelper.shortServerIP());
		vars.put("sip", ServerHelper.shortServerIP());
		vars.put("fps", Main.mc.debug.split(",")[0].replaceAll("fps", ""));
		vars.put("direction", AllInfo.getDirection());
		vars.put("dir", "" + AllInfo.getDirectionChar());
		vars.put("f", "" + AllInfo.getF());
		vars.put("f.", "" + AllInfo.getF());
		vars.put("fdec", "" + AllInfo.getFDecimal());
		vars.put("players", "" + AllInfo.getPlayersOnline());
	}
	
	/**
	 * Puts a value into the variable storage.
	 * @param key The key of the value.
	 * @param value The value.
	 */
	public void put(String key, String value) {
		vars.put(key, value);
	}
	
	/**
	 * Used to get variables. Avoids returning null.
	 * @param string The key of the variable to get.
	 * @return The value of the stored variable.
	 */
	public String get(String string) {
		if (vars.keySet().contains(string))
			return vars.get(string);
		return "";
	}
	
	/**
	 * Resets the variable storage by clearing it. 
	 */
	public void reset() {
		vars.clear();
	}
	
}
