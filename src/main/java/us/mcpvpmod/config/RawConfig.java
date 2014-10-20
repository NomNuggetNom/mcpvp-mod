package us.mcpvpmod.config;

import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.util.Data;

public class RawConfig {

	/**
	 * Checks some variables from the Data file and applies the relevant settings.
	 * Used for saving deletion of Armor & Potion display.
	 */
	public static void load() {
		if (Data.get("showArmor") != null && Data.get("showArmor").equals("false"))
			ConfigHUD.showArmor = false;
		
		if (Data.get("showPotion") != null && Data.get("showPotion").equals("false"))
			ConfigHUD.showPotion = false;
	}
	
}
