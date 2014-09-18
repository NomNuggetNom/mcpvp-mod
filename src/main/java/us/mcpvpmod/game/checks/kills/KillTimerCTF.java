package us.mcpvpmod.game.checks.kills;

import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.gui.Medal;
import cpw.mods.fml.common.FMLLog;


// TODO: Consider renaming to KillMedalsCTF
public class KillTimerCTF {
	
	/*
	 * Kill detection
	 */
	public static int killsOld = 0;
	public static long lastKillTime = 0;
	public static int resetTime = 1000*7;
	public static int killsInARow = 0;
	
	public static void check() {
		if (StatsCTF.kills == 0) {
			lastKillTime = System.currentTimeMillis();
			killsOld = 0;
			killsInARow = 0;
		}
		
		// Reset based on time.
		if (System.currentTimeMillis() - lastKillTime > resetTime) {
			lastKillTime = System.currentTimeMillis();
			killsInARow = 0;
		}
		
		// There is a difference in kills, so we have killed someone since the last check.
		if (killsOld != StatsCTF.kills) {
			if ((killsInARow == 0 && StatsCTF.kills != 0) || System.currentTimeMillis() - lastKillTime <= resetTime) {
				// Killed a player within the window of time required for achievements!
				FMLLog.info("Killed someone at %s", System.currentTimeMillis());
				killsInARow++;
				if (killsInARow == 2) {
					Medal.add(new Medal("doublekill"));
					
				} else if (killsInARow == 3) {
					Medal.add(new Medal("triplekill"));

				} else if (killsInARow == 4) {
					Medal.add(new Medal("overkill"));

				} else if (killsInARow == 5) {
					Medal.add(new Medal("killtacular"));

				} else if (killsInARow == 6) {
					Medal.add(new Medal("killtrocity"));

				} else if (killsInARow == 7) {
					Medal.add(new Medal("killimanjaro"));

				} else if (killsInARow == 8) {
					Medal.add(new Medal("killtastrophe"));

				} else if (killsInARow == 9) {
					Medal.add(new Medal("killpocalypse"));
					
				} else if (killsInARow == 10) {
					Medal.add(new Medal("killionaire"));

				}
			}
			killsOld = StatsCTF.kills;			
			lastKillTime = System.currentTimeMillis();
		}
	}
}
