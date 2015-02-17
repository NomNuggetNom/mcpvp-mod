package us.mcpvpmod.events.chat;

import java.util.ArrayList;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;

public class IgnoreResult {

	/** An array of all IgnoreResults. **/
	public static ArrayList<IgnoreResult> ignoreResults = new ArrayList<IgnoreResult>();

	/** The message to send. */
	public String messageToSend;
	/** The message(s) to ignore. */
	ArrayList<String> ignore = null;
	
	/**
	 * A handy class for sending a message and automatically cancelling the result, e.g. "/ip" results in a message 
	 * "Server Adress: ip". Therefore a constructor IgnoreResult("/ip", "Server Address: .*")
	 * is used to send /ip and automatically cancel the message.
	 * @param messageToSend The chat message to send as the player, e.g. "/ip"
	 * @param resultToIgnore The chat message that is received as a result of messageToSend, e.g. "Server Address.*"
	 * Will be automatically cancelled one time. Can be a literal string or a regex pattern to match.
	 */
	public IgnoreResult(String messageToSend, String resultToIgnore) {
		this.messageToSend = messageToSend;
		this.ignore = new ArrayList<String>();
		this.ignore.add(resultToIgnore);
		this.send();
	}
	
	/**
	 * A handy class for sending a message and automatically cancelling the results, e.g. "/ip" results in a message 
	 * "Server Adress: ip". Therefore a constructor IgnoreResult("/ip", "Server Address: .*")
	 * is used to send /ip and automatically cancel the message.
	 * @param messageToSend The chat message to send as the player, e.g. "/ip"
	 * @param resultToIgnore The chat message that is received as a result of messageToSend, e.g. "Server Address.*"
	 * Will be automatically cancelled one time. Can be a literal string or a regex pattern to match.
	 */
	public IgnoreResult(String messageToSend, String... resultToIgnore) {
		this.messageToSend = messageToSend;
		this.ignore = new ArrayList<String>();
		for (String string : resultToIgnore) {
			this.ignore.add(string);
		}
		this.send();
	}
	
	/**
	 * Sends the messageToSend and adds the resultToIgnore to the list of messages to be ignored.
	 */
	public void send() {
		ignoreResults.add(this);
		Main.l("Sending IgnoreResult \"%s\". Will ignore: %s", messageToSend, this.ignore);
		Main.mc.thePlayer.sendChatMessage(messageToSend);
	}
	
	/**
	 * Consults the list of messages to ignore and sets the event to cancelled if it finds a match.
	 * @param event The event to check.
	 */
	public void check(ClientChatReceivedEvent event) {
		for (String string : this.ignore) {
			if (event.message.getUnformattedText().equals(string) 
					|| event.message.getUnformattedText().matches(string)) {
				event.setCanceled(true);
				Main.l("Auto-removed \"%s\" via IgnoreResult \"%s\"", event.message.getUnformattedText(), this);
				this.ignore.remove(string);
				return;
			}
		}
	}
	
	@Override
	public String toString() {
		return "IgnoreResult [messageToSend=" + messageToSend + ", ignore="
				+ ignore + "]";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof IgnoreResult && ((IgnoreResult)o).ignore.equals(this.ignore);
	}
	
	/**
	 * Cycles through every IgnoreResult and runs check(event).
	 * @param event The event to check.
	 */
	public static void checkAll(ClientChatReceivedEvent event) {
		Main.start("ignore");
		for (int i = ignoreResults.size()-1; i > 0; i--) {
			ignoreResults.get(i).check(event);
			
			// Remove empty additions to prevent lag.
			if (ignoreResults.get(i).ignore.size() == 0)
				ignoreResults.remove(i);
		}
		Main.end();
	}
	
}
