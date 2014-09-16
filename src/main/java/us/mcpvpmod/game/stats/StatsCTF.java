package us.mcpvpmod.game.stats;

import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.MCPVPMath;

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
	
	public static void reset() {
		kills = 0;
		streak = 0;
		deaths = 0;
		steals = 0;
		caps = 0;
		recovers = 0;
		headshots = 0;
		assists = 0;
		streakName = "null";
		KillTimerCTF.lastKillTime = 0;
		KillTimerCTF.killsInARow = 0;
	}
	
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
