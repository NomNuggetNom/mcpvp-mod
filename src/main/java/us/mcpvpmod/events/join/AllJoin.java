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
	
	/**
	 * Fired when an MCPVP server is joined.
	 * Relies on {@link AllChat} catching the "Now logged in!" message.
	 */
	public static void onJoin() {
		lastLogin = System.currentTimeMillis();
		Main.l("Logged in!");
		
		// Clear the IgnoreResults list to keep it's size in check.
		IgnoreResult.ignoreResults.clear();
		
		// Get the IP of the server.
		new IgnoreResult("/ip", "Server Address: (.*)");
		
		// Select tag.
		autoTag();
		
		// Set defaults of display.
		// Only does anything once.
		Data.setDefaults();

		// Clear the second chat.
		Main.secondChat.clearChatMessages();
		
		// Sab has trouble detecting states, so it is set
		// to PRE when joined.
		StateSab.state = StateSab.PRE;
	}
	
	/**
	 * Resets stored variables to make sure they are accurate.
	 */
	public static void resetVars() {
		Vars.reset();
		AllVars.reset();
		VarsBuild.reset();
		VarsCTF.reset();
		VarsHG.reset();
		VarsKit.reset();
		VarsMaze.reset();
		VarsRaid.reset();
		VarsSab.reset();
	}
	
	/**
	 * Implements the auto-tagging system.
	 */
	public static void autoTag() {
		
		// Automatically tag.
		if (ConfigSelect.autoTagB) {
			new IgnoreResult("/tag " + ConfigSelect.autoTag, ".*Success! You now look like.*");
			Main.l("Automatically tagged to \"%s\"", ConfigSelect.autoTag);
		}
		
		// Automatically silent based on config.
		if (ConfigSelect.autoSilent) {
			new IgnoreResult("/silent", "\00A7r\00A7cAll alerts are now hidden for you.\00A7r");
		}
	}
	
	/**
	 * Shows the tutorial screen.
	 */
	public static void showWelcome() {
		if (System.currentTimeMillis() - lastLogin >= 100
				&& Data.get("shownWelcome") == null
				&& Main.mc.currentScreen == null) {
			Tutorial.build();
		}
	}
	
}
