package us.mcpvpmod.game.state;

public enum StateHG implements State {
	WAIT, PRE, PLAY, NONE;
	
	public static StateHG getState() {
		return NONE;
	}
}
