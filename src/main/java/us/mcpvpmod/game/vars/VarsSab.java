package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.info.InfoSab;

public class VarsSab implements IVarProvider  {
	
	private static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public void putVars() {
		vars.put("karma", "");
		vars.put("role", InfoSab.formatRole());
		vars.put("remain", Vars.get("sab:remain"));
		vars.put("detective", Vars.get("sab:detective"));
		vars.put("winner", InfoSab.formatWinners());
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
