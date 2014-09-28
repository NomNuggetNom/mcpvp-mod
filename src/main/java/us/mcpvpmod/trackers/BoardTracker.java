package us.mcpvpmod.trackers;

import java.util.ArrayList;

import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.BoardHelper;

public class BoardTracker {

	public static ArrayList<BoardTracker> boardTrackers = new ArrayList<BoardTracker>();
	
	public String displayName;
	public String varName;
	
	/**
	 * Used to track values on the scoreboard. Updated every tick.
	 * @param varName The key of the value to store.
	 * @param displayName The name of the scoreboard entry to track.
	 */
	public BoardTracker(String varName, String displayName) {
		this.displayName = displayName;
		this.varName = varName;
		boardTrackers.add(this);
	}
	
	public void update() {
		Vars.put(varName, "" + BoardHelper.getScore(displayName));
	}
	
}
