package us.mcpvpmod.events.tick;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.StateSmash;
import us.mcpvpmod.util.BoardHelper;

public class TickSmash {

	public static void onTick(@SuppressWarnings("unused") TickEvent event) {		
		Server.SMASH.varProvider.putVars();
		
		if (StateSmash.getState() != StateSmash.PRE  
				&& BoardHelper.getBoardTitle().contains("Classes"))
			StateSmash.state = StateSmash.PRE;
		
		if (StateSmash.getState() != StateSmash.PLAY 
				&& StateSmash.getState() != StateSmash.POST
				&& !BoardHelper.getBoardTitle().contains("Classes"))
			StateSmash.state = StateSmash.PLAY;
		
	}
	
}
