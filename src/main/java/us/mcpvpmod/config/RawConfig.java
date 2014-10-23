package us.mcpvpmod.config;

import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.util.Data;

public class RawConfig {

	/**
	 * Checks some variables from the Data file and applies the relevant settings.
	 * Used for saving deletion of Armor & Potion display.
	 */
	public static void load() {
		
		if (Data.get("showArmor") != null)
			ConfigHUD.showArmor = Boolean.valueOf(Data.get("showArmor"));
		
		if (Data.get("showPotion") != null)
			ConfigHUD.showPotion = Boolean.valueOf(Data.get("showPotion"));
	}
	
}
