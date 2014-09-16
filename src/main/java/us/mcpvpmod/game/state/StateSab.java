package us.mcpvpmod.game.state;

public enum StateSab implements State {
	WAIT, PRE, PLAY, DEAD, NONE;
	
	public static StateSab getState() {
		return NONE;
	}
}
