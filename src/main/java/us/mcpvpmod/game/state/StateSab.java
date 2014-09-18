package us.mcpvpmod.game.state;

import us.mcpvpmod.game.vars.Vars;

public enum StateSab implements State {
	WAIT, PRE, PLAY, DEAD, NONE;
	
	public static StateSab getState() {
		if (Vars.get("sab:state").equals("pre")) return PRE;
		return NONE;
	}
}
