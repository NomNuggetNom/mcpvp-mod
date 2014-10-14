package us.mcpvpmod.triggers;

import java.util.ArrayList;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.vars.Vars;
import cpw.mods.fml.common.FMLLog;

public class ChatTrigger {

	/** An array of all registered triggers. */
	public static ArrayList<ChatTrigger> triggers = new ArrayList<ChatTrigger>();
	
	/** The pattern of the message to match. Checked every time a message comes in. */
	public String pattern;
	/** The ID of the alert to trigger. */
	public String alertID;
	/** The key that the value will be registered to. Used in replacing the custom alert constructs, e.g. {player}. */
	public String key;
	/** The value of the variable to use in the alert. Usually a position in the message ($1, $2, etc). " */
	public String value;
	/** The server to trigger this alert on. Used to prevent conflicts. */
	public Server server;
	
	/**
	 * An alert that is triggered when a message is received that results in the setting of a variable.
	 * @param message The message to match.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param server The server to trigger this alert on. Used to prevent conflicts.
	 * @param regex Can be any of the following:
	 * <ul>
	 * <li>Pair of a position in the regular expression to match, and the variable name to assign the value, e.g. {"killer", "$1"} assigns the variable "killer" to the value of "$1" in the message.
	 * <li>A custom value and the variable name, e.g. {"killer", "noob"} assigns the variable "killer" to the value of "noob."
	 * <li>OR a variable name and a new value in the alert,e.g. {"sab:winner", "winner"} assigns the value of "winner" to the variable "sab:winner"
	 * </ul>
	 */
	public ChatTrigger(String message, String alertID, Server server, String[] regex) {
		this.pattern = message;
		this.alertID = alertID;
		this.server  = server;
		if (regex == null) {
			this.value	= null;
			this.key	= null;
		} else {
			this.key	= regex[0];
			this.value	= regex[1];
		}
		triggers.add(this);
	}
	
	/**
	 * An alert that is triggered when a message is received that results in the setting of a variable.
	 * @param message The message to match.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param server The server to trigger this alert on. Used to prevent conflicts.
	 * @param regex Can be any of the following:
	 * <ul>
	 * <li>Pair of a position in the regular expression to match, and the variable name to assign the value, e.g. {"killer", "$1"} assigns the variable "killer" to the value of "$1" in the message.
	 * <li>A custom value and the variable name, e.g. {"killer", "noob"} assigns the variable "killer" to the value of "noob."
	 * <li>OR a variable name and a new value in the alert,e.g. {"sab:winner", "winner"} assigns the value of "winner" to the variable "sab:winner"
	 * </ul>
	 */
	public ChatTrigger(String message, String alertID, Server server, String[]... regex) {
		for (String[] string : regex) {
			new ChatTrigger(message, alertID, server, string);
		}
	}
	
	/**
	 * An alert that is triggered when a message is received, and does not set any special variables.
	 * @param message The message to match.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param server The server to trigger this alert on. Used to prevent conflicts.
	 */
	public ChatTrigger(String message, String alertID, Server server) {
		this.pattern = message;
		this.alertID = alertID;
		this.server  = server;
		this.value = null;
		this.key = null;
		triggers.add(this);
	}
	
	public String toString() {
		return "id:" + this.alertID + ", trigger: " + this.pattern + ", key: " + key + ", value: " + value;
	}
	
	/**
	 * Checks if the message fits the pattern. If so, update the value and show the alerts.
	 * @param message The message to check.
	 */
	public void check(String message) {
		if (Server.getServer() != this.server) return;
		
		if (message.matches(this.pattern)) {

			// Some ChatTriggers have no value or key, instead using a raw pre-determined value.
			// Rarely used, but avoids errors.
			if (value != null && key != null) {
				
				// Most ChatTriggers use a regex pattern and assign a value to the first index in the expression.
				if (value.startsWith("$")) {
					String val = message.replaceAll(pattern, value);
					Vars.put(key, val);
					
				// Some ChatTriggers reference a variable (e.g. "hg:feast.x") in order to relay information
				// from other messages or sources (such as ChatTrackers).
				} else if (value.matches("\\w+:.+")){
					Main.l("Unusual ChatTrigger: %s", this);
					Vars.put(key, Vars.get(value));
				}
			}
			
			// Trigger the specified CustomAlert, if it isn't null.
			if (CustomAlert.get(this.alertID) != null) {
				Main.l("Message \"%s\" resulted in CustomAlert \"%s\" via ChatTrigger \"%s\"", message, this.alertID, this);
				CustomAlert.get(this.alertID).show();
			}
			
			// Trigger the specified SoundAlert, if it isn't null.
			if (SoundAlert.get(this.alertID) != null) {
				Main.l("Message \"%s\" resulted in SoundAlert \"%s\" via ChatTrigger \"%s\"", message, this.alertID, this);
				SoundAlert.get(this.alertID).play();
			}
		}
	}
	

	/**
	 * Checks the message in every registered ChatTrigger.
	 * @param message The message to check.
	 */
	public static void checkAll(String message) {
		for (ChatTrigger trigger : triggers) {
			trigger.check(message);
		}
	}
	
}
