package us.mcpvpmod.game.state;

public enum StateHS implements State {
	WAIT, PRE, PLAY, POST, END, NONE;
	
	public static StateHS getState() {
		return NONE;
	}
}
