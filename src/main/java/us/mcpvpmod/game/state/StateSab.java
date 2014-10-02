package us.mcpvpmod.game.state;

import us.mcpvpmod.Server;
import us.mcpvpmod.game.stats.StatsSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.info.InfoBlock;

public enum StateSab implements State {
	WAIT, PRE, PLAY, DEAD, POST, NONE;
	
	public static StateSab state = NONE;
	
	public static StateSab getState() {
		return state;
	}
	
	public static void render() {
		switch (StateSab.getState()) {
		case WAIT: 
			
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.SAB, PRE)) {
				block.display();
			}
			
			StatsSab.reset();
			
			break;
		
		case PRE: 			
			
			// Render our InfoBlocks.
			for (InfoBlock block : InfoBlock.get(Server.SAB, PRE)) {
				block.display();
			}
			
			StatsSab.reset();
			
			break;
			
		case PLAY: 
			for (InfoBlock block : InfoBlock.get(Server.SAB, PLAY)) {
				block.display();
			}
			
			break;
		
		case DEAD: 
			for (InfoBlock block : InfoBlock.get(Server.SAB, DEAD)) {
				block.display();
			}
			
			break;
			
		case POST: 
			for (InfoBlock block : InfoBlock.get(Server.SAB, POST)) {
				block.display();
			}
			
			break;
			
		case NONE: 
			
			break;
		}
	}
}
