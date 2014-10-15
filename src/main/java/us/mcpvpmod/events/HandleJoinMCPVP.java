package us.mcpvpmod.events;

import us.mcpvpmod.Main;
import us.mcpvpmod.events.chat.AllChat;
import us.mcpvpmod.events.chat.IgnoreResult;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.game.vars.VarsBuild;
import us.mcpvpmod.game.vars.VarsCTF;
import us.mcpvpmod.game.vars.VarsHG;
import us.mcpvpmod.game.vars.VarsKit;
import us.mcpvpmod.game.vars.VarsMaze;
import us.mcpvpmod.game.vars.VarsRaid;
import us.mcpvpmod.game.vars.VarsSab;
import us.mcpvpmod.gui.screen.GuiWelcome;
import us.mcpvpmod.util.Data;

public class HandleJoinMCPVP {

	public static long lastLogin = 0;
	
	/**
	 * Fired when the "Now logged in!" message is received in chat.
	 */
	public static void onJoin() {
		lastLogin = System.currentTimeMillis();
		Main.l("Logged in!");
		new IgnoreResult("/ip", "Server Address: (.*)").send();
		//Main.mc.thePlayer.sendChatMessage("/ip");
		//AllChat.getIP = true;
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
	}
	
	public static void showWelcome() {
		if (System.currentTimeMillis() - lastLogin >= 100
				&& Data.get("shownWelcome") == null
				&& Main.mc.currentScreen == null) {
			Main.mc.displayGuiScreen(new GuiWelcome(Main.mc.currentScreen));
			Data.put("shownWelcome", "true");
		}
	}
	
}
