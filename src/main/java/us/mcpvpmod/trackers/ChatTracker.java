package us.mcpvpmod.trackers;

import java.util.ArrayList;

import scala.reflect.internal.Mode;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.triggers.ChatTrigger;
import cpw.mods.fml.common.FMLLog;

public class ChatTracker {

	public static ArrayList<ChatTracker> chatTrackers = new ArrayList<ChatTracker>();
	
	public String pattern;
	public String value;
	public String key;
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
	
	/**
	 * Checks if the message fits the pattern. If so, update the value.
	 * @param message The message to check.
	 */
	public void check(String message) {
		if (Server.getServer() != this.server) return;
		
		if (message.matches(this.pattern)) {
			if (value.startsWith("$")) {
				String val = message.replaceAll(pattern, value);
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
