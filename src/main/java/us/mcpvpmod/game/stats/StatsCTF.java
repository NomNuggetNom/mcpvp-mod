package us.mcpvpmod.game.stats;

import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.MCPVPMath;

/**
 * Contains all statistics for CTF.
 */ 
public class StatsCTF extends AllStats {

	public static int recovers = 0;
	public static int headshots = 0;
	public static int assists = 0;
	public static String streakName = "null";
	
	/**
	 * Resets statics such as kills, streak, deaths, etc.
	 * Happens during pre-game or when a new server is joined.
	 */ 
	public static void reset() {
		Vars.put("ctf:i.kills", "0");
		Vars.put("ctf:i.streak", "0");
		Vars.put("ctf:i.deaths", "0");
		Vars.put("ctf:i.steals", "0");
		Vars.put("ctf:i.caps", "0");
		//caps = 0;
		recovers = 0;
		headshots = 0;
		assists = 0;
		streakName = "null";
		KillTimerCTF.lastKillTime = System.currentTimeMillis();
		KillTimerCTF.killsInARow = 0;
	}
	
	public static double getKD() {
		if (Vars.getInt("ctf:i.deaths") != 0)
			return MCPVPMath.round((double)Vars.getInt("ctf:i.kills")/(double)Vars.getInt("ctf:i.deaths"), 3);
		return 0;
	}
	
	public static float netKD() {
		return Vars.getInt("ctf:i.kills") - Vars.getInt("ctf:i.deaths");
	}
		
}
