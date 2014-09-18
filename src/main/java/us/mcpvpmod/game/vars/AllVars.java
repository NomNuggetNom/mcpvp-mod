package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.Main;
import us.mcpvpmod.game.info.AllInfo;
import us.mcpvpmod.game.info.InfoCTF;

/**
 * Variables for every server.
 * @author NomNuggetNom
 */
public class AllVars {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
		vars.put("x", "" + AllInfo.getX());
		vars.put("y", "" + AllInfo.getY());
		vars.put("z", "" + AllInfo.getZ());
		vars.put("ip", AllInfo.getIP());
		vars.put("fps", Main.mc.debug.split(",")[0].replaceAll("fps", ""));
		vars.put("direction", AllInfo.getFacing());
		vars.put("f", AllInfo.getFacing());
		vars.put("players", "" + AllInfo.getPlayersOnline());
		//Main.mc.s
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
