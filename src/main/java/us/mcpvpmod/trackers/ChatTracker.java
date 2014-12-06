package us.mcpvpmod.trackers;

import java.util.ArrayList;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.vars.Vars;
import cpw.mods.fml.common.FMLLog;

public class ChatTracker {

	/** An array of all registered ChatTrackers. */
	public static ArrayList<ChatTracker> chatTrackers = new ArrayList<ChatTracker>();
	
	/** The pattern of the message to match. Checked every time a message comes in. */
	public String pattern;
	/** The key that the value will be registered to. Used in replacing the custom alert constructs, e.g. {player}. */
	public String key;
	/** The value of the variable to assign in Vars. Usually a position in the message ($1, $2, etc), which will be evaluated. */
	public String value;
	/** The server to track on. Used to prevent conflicts. */
	public Server server;
	
	/**
	 * Constructor for a single tracker.
	 * @param pattern The message to match.
	 * @param data In pairs, the variable name to assign the information to and the value to set. 
	 * The value can be a position in a regular expression, 
	 * e.g. {"hg:kit", "$1"} hg:kit is assigned the value of the message's first regex return.
	 */
	public ChatTracker(String pattern, Server server, String[] data) {
		this.pattern = pattern;
		this.server  = server;
		this.key 	 = data[0];
		this.value 	 = data[1];
		chatTrackers.add(this);
	}
	
	/**
	 * Constructor for building multiple trackers at once.
	 * @param pattern The message to match.
	 * The value can be a position in a regular expression, 
	 * e.g. {"hg:kit", "$1"} hg:kit is assigned the value of the message's first regex return.
	 */
	public ChatTracker(String pattern, Server server, String[]... data) {
		for (String[] string : data) {
			new ChatTracker(pattern, server, string);
		}
	}
	
	public String toString() {
		return "pattern: " + this.pattern + ", key: " + key + ", value: " + value;
	}
	
	/**
	 * Checks if the message fits the pattern. If so, update the value.
	 * @param message The message to check.
	 */
	public void check(String message) {
		if (Server.getServer() != this.server) return;
		
		if (message.matches(this.pattern)) {
			
			// Most ChatTrackers use a regex pattern and assign a value to the first index in the expression.
			if (value.startsWith("$")) {
				String val = message.replaceAll(pattern, value);
				Main.l("Message \"%s\" resulted in the variable \"%s\" being stored with a value of \"%s\"", message, this.key, val);
				Vars.put(key, val);
			} else {
				FMLLog.info("Unusual tracker. varName: %s - replace: %s", key, value);
				Vars.put(key, value);
			}
		}
	}
	
	/**
	 * Checks the message in every registered ChatTracker.
	 * @param message The message to check.
	 */
	public static void checkAll(String message) {
		for (ChatTracker tracker : chatTrackers) {
			tracker.check(message);
		}
	}
	
}
