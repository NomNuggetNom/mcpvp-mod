package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.Main;
import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.team.TeamCTF;

/**
 * Variables for CTF.
 * @author NomNuggetNom
 */
public class VarsCTF {
	
	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Called every tick to update information.
	 */
	public static void putVars() {
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
		vars.put("blue wins", Vars.get("ctf:team.blue.wins"));
		vars.put("blue caps", Vars.get("ctf:team.blue.caps"));
		vars.put("blue flag", Vars.get("ctf:team.blue.flag"));
		vars.put("blue players", Vars.get("ctf:team.blue.players"));
		vars.put("red wins", Vars.get("ctf:team.red.wins"));
		vars.put("red caps", Vars.get("ctf:team.red.caps"));
		vars.put("red flag", Vars.get("ctf:team.red.caps"));
		vars.put("red players", Vars.get("ctf:team.red.players"));
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
	public static String get(String string) {
		if (vars.keySet().contains(string)) {
			return vars.get(string);
		} else {
			return "";
		}
	}
}