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
		vars.put("hunger", Vars.get("maze:i.princess.hunger"));
		vars.put("princesshunger", Vars.get("maze:i.princess.hunger"));
		vars.put("princess_hunger", Vars.get("maze:i.princess.hunger"));
		vars.put("princess-hunger", Vars.get("maze:i.princess.hunger"));
		vars.put("health", Vars.get("maze:i.princess.health"));
		vars.put("princesshealth", Vars.get("maze:i.princess.health"));
		vars.put("princess_health", Vars.get("maze:i.princess.health"));
		vars.put("princess-health", Vars.get("maze:i.princess.health"));
		vars.put("baseX", Vars.get("maze:i.base.x"));
		vars.put("base x", Vars.get("maze:i.base.x"));
		vars.put("baseZ", Vars.get("maze:i.base.z"));
		vars.put("base z", Vars.get("maze:i.base.z"));
		vars.put("team", Vars.get("maze:i.team"));
		vars.put("kit", Vars.get("maze:i.kit"));
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
