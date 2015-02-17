package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.info.InfoSmash;

public class VarsSmash implements IVarProvider  {

	private static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public void putVars() {
		vars.put("kit", Vars.get("smash:kit"));
		vars.put("class", Vars.get("smash:kit"));
		vars.put("character", Vars.get("smash:kit"));
		vars.put("map", Vars.get("smash:map.name"));
		vars.put("map-name", Vars.get("smash:map.name"));
		vars.put("map-id", Vars.get("smash:map.id"));
		vars.put("map-author", Vars.get("smash:map.author"));
		vars.put("stage", Vars.get("smash:map"));
		vars.put("stage-name", Vars.get("smash:map"));
		vars.put("score", "" + InfoSmash.getScore());
		vars.put("time", InfoSmash.getTime());
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

	@Override
	public void put(String string, String level) {
		// TODO Auto-generated method stub
		
	}
	
}