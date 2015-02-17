package us.mcpvpmod.game.state;

import us.mcpvpmod.Server;
import us.mcpvpmod.gui.InfoBlock;

public enum StateSmash implements State {
	PRE, PLAY, POST;
	
	public static StateSmash state = PRE;
	
	public static StateSmash getState() {
		return state;
	}
	
	public void onRender() {
		for (InfoBlock block : InfoBlock.get(Server.SMASH, this))
			block.display();
	}

}
