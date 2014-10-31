package us.mcpvpmod.events.join;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigSelect;
import us.mcpvpmod.events.chat.IgnoreResult;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.game.vars.VarsBuild;
import us.mcpvpmod.game.vars.VarsCTF;
import us.mcpvpmod.game.vars.VarsHG;
import us.mcpvpmod.game.vars.VarsKit;
import us.mcpvpmod.game.vars.VarsMaze;
import us.mcpvpmod.game.vars.VarsRaid;
import us.mcpvpmod.game.vars.VarsSab;
import us.mcpvpmod.gui.tutorial.Tutorial;
import us.mcpvpmod.util.Data;

public class AllJoin {

	public static long lastLogin = 0;
	
	public static void onJoin() {
		lastLogin = System.currentTimeMillis();
		Main.l("Logged in!");
		
		new IgnoreResult("/ip", "Server Address: (.*)");
		autoTag();
		
		Data.setDefaults();
		
		Vars.reset();
		AllVars.reset();
		VarsBuild.reset();
		VarsCTF.reset();
		VarsHG.reset();
		VarsKit.reset();
		VarsMaze.reset();
		VarsRaid.reset();
		VarsSab.reset();
		Main.secondChat.clearChatMessages();
		
		StateSab.state = StateSab.PRE;
	}
	
	
	public static void autoTag() {
		if (!ConfigSelect.autoTagB) {
			new IgnoreResult("/tag " + ConfigSelect.autoTag, ".*Success! You now look like.*");
			Main.l("Automatically tagged to \"%s\"", ConfigSelect.autoTag);
		}
		
		if (ConfigSelect.autoSilent) {
			new IgnoreResult("/silent", "\00A7r\00A7cAll alerts are now hidden for you.\00A7r");
		}
	}
	
	public static void showWelcome() {
		if (System.currentTimeMillis() - lastLogin >= 100
				&& Data.get("shownWelcome") == null
				&& Main.mc.currentScreen == null) {
			Tutorial.build();
			//Main.mc.displayGuiScreen(new TutorialScreen("Testing", "Content", null, null));
			//Main.mc.displayGuiScreen(new GuiWelcome(Main.mc.currentScreen));
			//Data.put("shownWelcome", "true");
		}
	}
	
}
