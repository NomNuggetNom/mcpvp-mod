package us.mcpvpmod.trackers;

import java.util.ArrayList;

import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.BoardHelper;

public class BoardTracker {

	public static ArrayList<BoardTracker> boardTrackers = new ArrayList<BoardTracker>();
	
	public String displayName = "";
	public String varName = "";
	
	public BoardTracker(String displayName, String varName) {
		this.displayName = displayName;
		this.varName = varName;
		boardTrackers.add(this);
	}
	
	public void update() {
		Vars.put(varName, "" + BoardHelper.getScore(displayName));
	}
	
}
