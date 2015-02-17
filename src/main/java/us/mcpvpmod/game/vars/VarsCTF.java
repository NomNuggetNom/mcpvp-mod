package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.team.TeamCTF;

public class VarsCTF implements IVarProvider {
	
	private static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public void putVars() {
		vars.put("kills", Vars.get("ctf:kills"));
		vars.put("streak", Vars.get("ctf:streak"));
		vars.put("deaths", Vars.get("ctf:deaths"));
		vars.put("caps", Vars.get("ctf:caps"));
		vars.put("steals", Vars.get("ctf:steals"));
		vars.put("recovers", "" + StatsCTF.recovers);
		vars.put("headshots", "" + StatsCTF.headshots);
		vars.put("kd", "" + StatsCTF.getKD());
		vars.put("net kd", "" + StatsCTF.netKD());
		vars.put("assists", "" + StatsCTF.assists);
		vars.put("streak name", StatsCTF.streakName);
		vars.put("class", Vars.get("ctf:class"));
		vars.put("time", InfoCTF.getTimeString());
		vars.put("map", Vars.get("ctf:map"));
		vars.put("game", InfoCTF.getGameNum());
		vars.put("free day", InfoCTF.getFreeDay());
		vars.put("total-caps", Vars.get("ctf:totalcaps"));
		vars.put("total_caps", Vars.get("ctf:totalcaps"));
		vars.put("blue-wins", Vars.get("ctf:team.blue.wins"));
		vars.put("blue_wins", Vars.get("ctf:team.blue.wins"));
		vars.put("blue-caps", Vars.get("ctf:team.blue.caps"));
		vars.put("blue_caps", Vars.get("ctf:team.blue.caps"));
		vars.put("blue-flag", Vars.get("ctf:team.blue.flag"));
		vars.put("blue_flag", Vars.get("ctf:team.blue.flag"));
		vars.put("blue-timer", InfoCTF.getRedTimer());
		vars.put("blue_timer", InfoCTF.getRedTimer());
		vars.put("blue-players", Vars.get("ctf:team.blue.players"));
		vars.put("blue_players", Vars.get("ctf:team.blue.players"));
		vars.put("red-wins", Vars.get("ctf:team.red.wins"));
		vars.put("red_wins", Vars.get("ctf:team.red.wins"));
		vars.put("red-caps", Vars.get("ctf:team.red.caps"));
		vars.put("red_caps", Vars.get("ctf:team.red.caps"));
		vars.put("red-flag", Vars.get("ctf:team.red.flag"));
		vars.put("red_flag", Vars.get("ctf:team.red.flag"));
		vars.put("red-players", Vars.get("ctf:team.red.players"));
		vars.put("red_players", Vars.get("ctf:team.red.players"));
		vars.put("red-timer", InfoCTF.getRedTimer());
		vars.put("red_timer", InfoCTF.getRedTimer());
		vars.put("team", TeamCTF.usersTeam().toString());
		vars.put("winner", Vars.get("ctf:winner"));
		//vars.put("time streak", "" + KillTimer.killsInARow);
		vars.put("state", StateCTF.getState().toString());
		vars.put("friends", "friends");
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