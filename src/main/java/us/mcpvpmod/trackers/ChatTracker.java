package us.mcpvpmod.trackers;

import java.util.ArrayList;

import scala.reflect.internal.Mode;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.triggers.ChatTrigger;
import cpw.mods.fml.common.FMLLog;

public class ChatTracker {

	public static ArrayList<ChatTracker> chatTrackers = new ArrayList<ChatTracker>();
	
	public String pattern = "";
	public String replace = "";
	public String varName = "";
	
	/**
	 * Constructor for a single tracker.
	 * @param pattern The message to match.
	 * @param data The regular expression position and variable to set.
	 */
	public ChatTracker(String pattern, String[] data) {
		this.pattern = pattern;
		this.replace = data[0];
		this.varName = data[1];
		chatTrackers.add(this);
	}
	
	/**
	 * Constructor for building multiple trackers at once.
	 * @param pattern The message to match.
	 * @param data The regular expression position and variable to set.
	 */
	public ChatTracker(String pattern, String[]... data) {
		for (String[] string : data) {
			new ChatTracker(pattern, string);
		}
	}
	
	public void check(String message) {
		if (message.matches(this.pattern)) {

			String val = message.replaceAll(pattern, replace);
			FMLLog.info("Message \"%s\" triggered. Val: %s", message, val);
			Vars.put(varName, val);
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
