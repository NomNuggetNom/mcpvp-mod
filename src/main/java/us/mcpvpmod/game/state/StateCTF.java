package us.mcpvpmod.game.state;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.alerts.ctf.TimeAlerts;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.util.BoardHelper;

public enum StateCTF implements State {
	WAIT, PRE, PLAY, POST, END, NONE;
	
	/**
	 * Used to detect the state of the CTF game.
	 * @return The GameState.
	 */
	public static StateCTF getState() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle == null) return NONE;
		
		if (boardTitle.contains("Waiting for players")) {
			return WAIT;
		} else if (boardTitle.contains("Starts in")) {
			return PRE;
		} else if (boardTitle.contains("Ends in")) {
			return PLAY;
		} else if (boardTitle.contains("Next game in")) {
			return POST;
		} else if (boardTitle.contains("Match Over")) {
			return END;
		}
		
		return NONE;
	}
	
	/**
	 * Regular Expressions for pulling info from the scoreboard
	 */
	public String regex() {
		switch(this) {
		case WAIT:
			return "\\[([0-9]+)\\/[0-9]+] Waiting for players...";
		case PRE:
			return "\\[([0-9]+)\\/[0-9]+] Starts in ([0-9]+:[0-9]+)";
		case PLAY: 
			return "\\[([0-9]+)\\/[0-9]+] Ends in ([0-9]+:[0-9]+)";
		case POST:
			return "\\[([0-9]+)\\/[0-9]+] Next game in ([0-9]+:[0-9]+)";
		case END:
			return "\\[([0-9]+)\\/[0-9]+] Match Over \\| ([0-9]+:[0-9]+)";
		default:
			return null;
		}
	}
	
	public void render() {
		if (Main.mc.gameSettings.showDebugInfo || StateCTF.getState().equals(StateCTF.NONE)) return;
		Server.CTF.varProvider.putVars();
		FriendsList.clearList();
		
		switch(this) {
		case WAIT:		
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.CTF, PRE)) {
				block.display();
			}
			
			StatsCTF.reset();
			
			break;

		case PRE:
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.CTF, PRE)) {
				block.display();
			}
			
			StatsCTF.reset();
			
			break;
			
		case PLAY:
			// Trigger our time alerts.
			TimeAlerts.updateTime();
			
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.CTF, PLAY)) {
				block.display();
			}
			
			break;
		case POST:
			// Trigger our time alerts.
			TimeAlerts.updateTime();
			
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.CTF, POST)) {
				block.display();
			}
			
			break;
		case END:
			// Trigger our time alerts.
			TimeAlerts.updateTime();
			
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.CTF, POST)) {
				block.display();
			}
		default:
			break;
		}
	}
	
	public int chatLines() {
		switch(this) {
		case WAIT:
			return 3;
		case PRE:
			return 3;
		case PLAY: 
			return 4;
		case POST:
			return 4;
		case END:
			return 4;
		default:
			return 11;
		}
	}
}
