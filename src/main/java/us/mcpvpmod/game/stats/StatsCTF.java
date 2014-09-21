package us.mcpvpmod.game.stats;

import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.MCPVPMath;

/**
 * Contains all statistics for CTF.
 */ 
public class StatsCTF extends Stats {

	public static int kills = 0;
	public static int streak = 0;
	public static int deaths = 0;
	public static int steals = 0;
	public static int caps = 0;
	public static int recovers = 0;
	public static int headshots = 0;
	public static int assists = 0;
	public static String streakName = "null";
	
	/**
	 * Resets statics.
	 * Happens during pre-game or when we join a new server.
	 */ 
	public static void reset() {
		Vars.put("ctf:i.kills", "0");
		kills = 0;
		Vars.put("ctf:i.streak", "0");
		streak = 0;
		Vars.put("ctf:i.detahs", "0");
		deaths = 0;
		Vars.put("ctf:i.steals", "0");
		steals = 0;
		Vars.put("ctf:i.caps", "0");
		caps = 0;
		recovers = 0;
		headshots = 0;
		assists = 0;
		streakName = "null";
		KillTimerCTF.lastKillTime = System.currentTimeMillis();
		KillTimerCTF.killsInARow = 0;
	}
	
	/**
	 * Called every tick to get stats from the Trackers.
	 */ 
	// TODO: Replace the variables with direct reference to the variables.
	public static void getStats() {
		kills = Vars.getInt("ctf:i.kills");
		streak = Vars.getInt("ctf:i.streak");
		deaths = Vars.getInt("ctf:i.deaths");
		steals = Vars.getInt("ctf:i.steals");
		caps = Vars.getInt("ctf:i.caps");
	}
	
	public static double getKD() {
		if (deaths != 0) {
			return MCPVPMath.round((double)kills/(double)deaths, 3);
		} else {
			return 0;
		}
	}
	
	public static float netKD() {
		return kills - deaths;
	}
		
}
