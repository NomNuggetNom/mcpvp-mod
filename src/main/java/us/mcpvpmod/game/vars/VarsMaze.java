package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.info.InfoMaze;

public class VarsMaze implements IVarProvider  {

	private static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public void putVars() {
		vars.put("hunger", Vars.get("maze:princess.hunger"));
		vars.put("princesshunger", Vars.get("maze:princess.hunger"));
		vars.put("princess_hunger", Vars.get("maze:princess.hunger"));
		vars.put("princess-hunger", Vars.get("maze:princess.hunger"));
		vars.put("health", Vars.get("maze:princess.health"));
		vars.put("princesshealth", Vars.get("maze:princess.health"));
		vars.put("princess_health", Vars.get("maze:princess.health"));
		vars.put("princess-health", Vars.get("maze:princess.health"));
		vars.put("baseX", Vars.get("maze:base.x"));
		vars.put("base x", Vars.get("maze:base.x"));
		vars.put("baseZ", Vars.get("maze:base.z"));
		vars.put("base z", Vars.get("maze:base.z"));
		vars.put("team", Vars.get("maze:team"));
		vars.put("color", InfoMaze.getTeamColor());
		vars.put("kit", Vars.get("maze:kit"));
		vars.put("teams", "" + InfoMaze.getTeamsLeft());
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
