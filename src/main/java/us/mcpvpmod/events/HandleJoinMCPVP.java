package us.mcpvpmod.events;

import us.mcpvpmod.Main;
import us.mcpvpmod.events.chat.AllChat;
import us.mcpvpmod.gui.GuiWelcome;
import us.mcpvpmod.util.Data;

public class HandleJoinMCPVP {

	public static long lastLogin = 0;
	public static boolean shown = false;
	
	/**
	 * Fired when the "Now logged in!" message is received in chat.
	 */
	public static void onJoin() {
		lastLogin = System.currentTimeMillis();
		System.out.println("Logged in.");
		Main.mc.thePlayer.sendChatMessage("/ip");
		AllChat.getIP = true;

	}
	
	public static void check() {
		if (System.currentTimeMillis() - lastLogin >= 100 && !shown
				&& Data.get("shownWelcome") == null
				&& Main.mc.currentScreen == null) {
			Main.mc.displayGuiScreen(new GuiWelcome(Main.mc.currentScreen));
			shown = true;
		}
	}
	
}
