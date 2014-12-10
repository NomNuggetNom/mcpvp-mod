package us.mcpvpmod.events.tick;

import us.mcpvpmod.game.state.StateSmash;
import us.mcpvpmod.game.vars.VarsSmash;
import us.mcpvpmod.util.BoardHelper;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickSmash {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {		
		VarsSmash.putVars();
		
		if (StateSmash.getState() != StateSmash.PRE  
				&& BoardHelper.getBoardTitle().contains("Classes"))
			StateSmash.state = StateSmash.PRE;
		
		if (StateSmash.getState() != StateSmash.PLAY 
				&& StateSmash.getState() != StateSmash.POST
				&& !BoardHelper.getBoardTitle().contains("Classes"))
			StateSmash.state = StateSmash.PLAY;
		
	}
	
}
