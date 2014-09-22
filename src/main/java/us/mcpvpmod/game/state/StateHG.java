package us.mcpvpmod.game.state;

import us.mcpvpmod.util.BoardHelper;

public enum StateHG implements State {
	WAIT, PRE, PLAY, NONE;
	
	public static StateHG getState() {
		String boardTitle = BoardHelper.getBoardTitle();

		if (boardTitle.contains("Starting In")) return PRE;
		if (boardTitle.contains("Invincible for") || boardTitle.equals("")) return PLAY;
		
		return NONE;
	}
}
