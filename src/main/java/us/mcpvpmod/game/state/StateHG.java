package us.mcpvpmod.game.state;

import us.mcpvpmod.Server;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.util.BoardHelper;

public enum StateHG implements State {
	WAIT, PRE, PLAY, NONE;
	
	public static StateHG state = NONE;
	
	public static StateHG getState() {
		String boardTitle = BoardHelper.getBoardTitle();

		if (boardTitle.contains("Starting In")) return PRE;
		if (boardTitle.contains("Invincible for")) return PLAY;
		
		return state;
	}
	
	public void render() {

		switch(this) {
		case WAIT:
			for (InfoBlock block : InfoBlock.get(Server.HG, PRE)) {
				block.display();
			}
			break;
			
		case PRE:
			for (InfoBlock block : InfoBlock.get(Server.HG, PRE)) {
				block.display();
			}
			break;
		case PLAY:
			for (InfoBlock block : InfoBlock.get(Server.HG, PLAY)) {
				block.display();
			}
			break;
		case NONE:	break;
		default:	break;
		}
	}
	
	/*
	public static void setState() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle.contains("Starting In")) {
			state = PRE;
		}

		if (boardTitle.contains("Invincible for") || boardTitle.equals("")) {
			state = PLAY;
		}
	}
	*/
}
