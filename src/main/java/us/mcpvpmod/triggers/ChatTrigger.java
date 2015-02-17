package us.mcpvpmod.triggers;

import java.util.ArrayList;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.vars.Vars;

public class ChatTrigger {

	/** An array of all registered triggers. */
	public static ArrayList<ChatTrigger> triggers = new ArrayList<ChatTrigger>();
	
	/** The pattern of the message to match. Checked every time a message comes in. */
	public String pattern;
	/** The ID of the alerts to trigger. */
	public String alertID;
	/** An array of each given key and value pair. */
	public ArrayList<String[]> pairs = new ArrayList<String[]>();
	/** The server to trigger this alert on. Used to prevent conflicts. */
	public Server server;
	
	/**
	 * An alert that is triggered when a message is received. Triggers both a 
	 * {@link CustomAlert} and a {@link SoundAlert} with the given {@link #alertID}.
	 * @param message The message to match. When this message is received (determined
	 * by calling {@link #check}, the trigger process is started and the alerts are usually shown.
	 * @param alertID The ID of the alerts to trigger, specified in the relevant config class.
	 * @param server The server to trigger this alert on. Used to prevent conflicts.
	 * @param regex Can be any of the following:
	 * <ul>
	 * <li>Pair of a position in the regular expression to match, and the variable name to assign the value, 
	 * e.g. {"killer", "$1"} assigns the variable "killer" to the value of "$1" in the message. Because
	 * <code>replace</code> is called, a valid second argument could also be <code>Hello,$1world!</code>
	 * or similar.
	 * <li>A custom value and the variable name, e.g. {"killer", "noob"} assigns the variable "killer" 
	 * to the value of "noob."
	 * <li>A variable name and a new value in the alert,e.g. {"sab:winner", "winner"} assigns 
	 * the value of "winner" to the variable "sab:winner"
	 * </ul>
	 */
	public ChatTrigger(String message, String alertID, Server server, String[]... regex) {
		this.pattern = message;
		this.alertID = alertID;
		this.server  = server;
		for (String[] re : regex)
			this.pairs.add(re);
		triggers.add(this);
	}

	@Override
	public String toString() {
		return "ChatTrigger [pattern=" + pattern + ", alertID=" + alertID
				+ ", pairs=" + pairs + ", server=" + server + "]";
	}

	/**
	 * Checks if the message fits the pattern. If so, update the value and show the alerts.
	 * @param message The message to check.
	 */
	public void check(String message) {
		if (Server.getServer() != this.server) return;
		
		if (message.matches(this.pattern)) {
			prep(message);
			
			// Trigger the specified CustomAlert, if it isn't null.
			if (CustomAlert.get(this.alertID) != null) {
				Main.l("Message \"%s\" resulted in CustomAlert \"%s\" via ChatTrigger \"%s\"", 
						message, this.alertID, this);
				CustomAlert.get(this.alertID).show();
			}
			
			// Trigger the specified SoundAlert, if it isn't null.
			if (SoundAlert.get(this.alertID) != null) {
				Main.l("Message \"%s\" resulted in SoundAlert \"%s\" via ChatTrigger \"%s\"", 
						message, this.alertID, this);
				SoundAlert.get(this.alertID).play();
			}
		}
	}
	
	/**
	 * Prepares for sending the alerts: cycles through all the
	 * given keys and pairs and evaluates their value in the message that
	 * triggered the alert.
	 * @param message The message to evaluate for variable values.
	 */
	public void prep(String message) {
		
		// Cycle through each regex entry.
		for (String[] regex : this.pairs) {
			String key = regex[0];
			String value = regex[1];
			
			// Some ChatTriggers have no value or key, instead using a raw pre-determined value.
			// Rarely used, but avoids errors.
			if (value != null && key != null) {
				
				// Most ChatTriggers use a regex position string.
				if (value.contains("$")) {
					String val = message.replaceAll(pattern, value);
					Vars.put(key, val);
					
				// Some ChatTriggers reference a variable (e.g. "hg:feast.x") in order to relay information
				// from other messages or sources (such as ChatTrackers).
				} else if (value.matches("\\w+:.+")){
					Vars.put(key, Vars.get(value));
				}
			}
		}
		
	}
	

	/**
	 * Checks the message in every registered ChatTrigger.
	 * @param message The message to check.
	 */
	public static void checkAll(String message) {
		for (ChatTrigger trigger : triggers)
			trigger.check(message);
	}
	
}
