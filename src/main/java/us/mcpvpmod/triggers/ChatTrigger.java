package us.mcpvpmod.triggers;

import java.util.ArrayList;

import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.vars.Vars;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatTrigger {

	public static ArrayList<ChatTrigger> triggers = new ArrayList<ChatTrigger>();
	
	public String pattern;
	public String alertID;
	public String replace;
	public String varName;
	
	@Override
	public String toString() {
		return this.pattern + ":" + this.alertID;
	}
	
	/**
	 * An alert that is triggered when a message is received.
	 * @param message The message to look for.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 */
	public ChatTrigger(String message, String alertID) {
		this.pattern = message;
		this.alertID = alertID;
		this.replace = null;
		this.varName = null;
		triggers.add(this);
	}
	
	/**
	 * An alert that is triggered when a message is received.
	 * @param message The message to look for.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param regex Pair of a position in the regular expression to match, and the variable name to assign the value.
	 */
	public ChatTrigger(String message, String alertID, String[] regex) {
		this.pattern = message;
		this.alertID = alertID;
		if (regex == null) {
			this.replace = null;
			this.varName = null;
		} else {
			this.replace = regex[0];
			this.varName = regex[1];
		}
		triggers.add(this);
	}
	
	/**
	 * An alert that is triggered when a message is received.
	 * @param message The message to look for.
	 * @param alertID The ID of the alert to trigger, specified in the relevant config class.
	 * @param regex Pair(s) of: a position in the regular expression to match and the variable name to assign the value.
	 */
	public ChatTrigger(String message, String alertID, String[]... regex) {
		for (String[] string : regex) {
			new ChatTrigger(message, alertID, string);
		}
	}
	
	public void check(String message) {
		if (message.matches(this.pattern)) {
			
			if (replace != null && varName != null) {
				String val = message.replaceAll(pattern, replace);
				Vars.put(varName, val);
			}
			
			if (CustomAlert.get(this.alertID) != null) {
				CustomAlert.get(this.alertID).show();
			}
			
			if (SoundAlert.get(this.alertID) != null) {
				SoundAlert.get(this.alertID).play();
			}
		}
	}
	
}