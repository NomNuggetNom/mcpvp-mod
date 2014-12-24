package us.mcpvpmod.events.join;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigSelect;
import us.mcpvpmod.data.Data;
import us.mcpvpmod.events.chat.AllChat;
import us.mcpvpmod.events.chat.IgnoreResult;
import us.mcpvpmod.events.render.AllRender;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.tutorial.Tutorial;

public class AllJoin {

	public static long lastLogin = 0;
	
	/**
	 * Fired when any MCPVP server is joined.
	 * Relies on {@link AllChat} catching the "Now logged in!" message.
	 * <br><br>
	 * {@link Server#getServer()} will still return {@link Server#NONE}.
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

		// Clear the second chat.
		Main.secondChat.clearChatMessages();
		
		//new HTMLStats("NomNuggetNom").establish(true);
		//MCPVPRadio.establish();
	}
	
	/**
	 * Fired when the IP message is picked up and
	 * {@link Server#getServer()} returns {@link Server#NONE},
	 * meaning a server has been joined and the server is identified.
	 * 
	 * @param server The server joined.
	 */
	public static void trueJoin(Server server) {
		if (AllChat.track) {
			// Set to false to avoid comodification
			AllChat.track = false;
			
			// Cycle through each tracked event and feed it
			// back to the server that was joined
			for (ClientChatReceivedEvent event : AllChat.tracked) 
				server.onChat(event);
			
			// Clear the tracked messages.
			AllChat.tracked.clear();
		}
		AllRender.removeSkins();
		
		server.onJoin();
	}
	
	/**
	 * Resets stored variables to make sure they are accurate.
	 */
	public static void resetVars() {
		Vars.reset();
		for (Server server : Server.values())
			if (server.varProvider != null)
				server.varProvider.reset();
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
