package us.mcpvpmod.game.state;

import us.mcpvpmod.Server;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.util.BoardHelper;

public enum StateHS implements State {
	WAIT, PRE, PLAY, POST, END, NONE;
	
	public static StateHS getState() {
		return PLAY;
		/*
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle == "") return NONE;
		
		if (boardTitle.contains("Ends in")) {
			return PLAY;
		}
		return NONE;
		*/
	}
	
	public void render() {
		switch (this) {
		case PLAY:
			for (InfoBlock block : InfoBlock.get(Server.HS, StateHS.PLAY)) {
				block.display();
			}
		}
	}
}
