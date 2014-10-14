package us.mcpvpmod.trackers;

import java.util.ArrayList;

import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.BoardHelper;

public class BoardTracker {

	/** An array of every registered BoardTracker. */
	public static ArrayList<BoardTracker> boardTrackers = new ArrayList<BoardTracker>();
	
	/** The key of the value to store. */
	public String varName;
	/** The name of the scoreboard entry to track, as it appears on the scoreboard. */
	public String displayName;

	/**
	 * Used to track values on the scoreboard. Updated every tick.
	 * @param varName The key of the value to store.
	 * @param displayName The name of the scoreboard entry to track, as it appears on the scoreboard.
	 */
	public BoardTracker(String varName, String displayName) {
		this.displayName = displayName;
		this.varName = varName;
		boardTrackers.add(this);
	}
	
	/**
	 * Called every game tick. Stores the variable with the value 
	 * found from BoardHelper.getScore().
	 */
	public void update() {
		Vars.put(varName, "" + BoardHelper.getScore(displayName));
	}
	
}
