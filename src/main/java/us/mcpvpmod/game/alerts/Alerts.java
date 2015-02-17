package us.mcpvpmod.game.alerts;

import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Alert;

/**
 * Contains the handle that all alerts use.
 */ 
public class Alerts {
	
	// ALL Alerts must reference this. 
	// It is updated every tick to display alerts.
	public static Alert alert = new Alert(Main.mc);
	
}
