package us.mcpvpmod.events.join;

import us.mcpvpmod.config.hg.ConfigHGSelect;
import us.mcpvpmod.events.chat.IgnoreResult;
import us.mcpvpmod.util.Format;

public class JoinHG {

	public static void onJoin() {
		if (ConfigHGSelect.selectMode.equals(Format.s("hg.config.select.selectMode.m.join"))) {
			new IgnoreResult("/kit " + ConfigHGSelect.selectKit, 
					".*You are now a (?i)" + ConfigHGSelect.selectKit, 
					".*Use /kit to open up the kit gui again, or use /<kitname>.*");
		}
	}
	
}
