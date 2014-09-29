package us.mcpvpmod.triggers;

import java.util.ArrayList;

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
	 * An alert that is triggered when a message is received.
	 * @param message The message to look for.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param server The server to trigger this alert on. Used to prevent conflicts.
	 * @param regex Pair of a position in the regular expression to match, and the variable name to assign the value 
	 * OR a custom value and the variable name 
	 * OR a variable name and a new value in the alert,e.g. {"var:sab:winner", "winner"} sets the value of "winner" to the variable "sab:winner"
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
	 * An alert that is triggered when a message is received.
	 * @param message The message to look for.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param server The server to trigger this alert on. Used to prevent conflicts.
	 * @param regex Pair of a position in the regular expression to match, and the variable name to assign the value 
	 * OR a custom value and the variable name 
	 * OR a variable name and a new value in the alert,e.g. {"var:sab:winner", "winner"} sets the value of "winner" to the variable "sab:winner"
	 */
	public ChatTrigger(String message, String alertID, Server server, String[]... regex) {
		for (String[] string : regex) {
			new ChatTrigger(message, alertID, server, string);
		}
	}
	
	/**
	 * An alert that is triggered when a message is received.
	 * @param message The message to look for.
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
	
	/**
	 * Checks if the message fits the pattern. If so, update the value and show the alerts.
	 * @param message The message to check.
	 */
	public void check(String message) {
		if (Server.getServer() != this.server) return;
		
		if (message.matches(this.pattern)) {

			if (value != null && key != null) {
	
				if (value.startsWith("$")) {
					String val = message.replaceAll(pattern, value);
					Vars.put(key, val);
				} else if (value.startsWith("var:") || value.matches("\\w+:.+")){
					FMLLog.info("Unusual ChatTrigger deteceted. Key: %s --- Val: %s", key, value);
					Vars.put(key, Vars.get(value.split("var:")[1]));
				}
			}
			
			if (CustomAlert.get(this.alertID) != null) {
				CustomAlert.get(this.alertID).show();
			}
			
			if (SoundAlert.get(this.alertID) != null) {
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
