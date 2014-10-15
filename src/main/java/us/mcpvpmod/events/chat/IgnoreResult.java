package us.mcpvpmod.events.chat;

import java.util.ArrayList;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;

public class IgnoreResult {

	public static ArrayList<String> toIgnore = new ArrayList<String>();
	
	public String messageToSend;
	public String resultToIgnore;
	
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
		this.resultToIgnore = resultToIgnore;
	}
	
	/**
	 * Sends the messageToSend and adds the resultToIgnore to the list of messages to be ignored.
	 */
	public void send() {
		Main.l("Sending IgnoreResult \"%s\"", messageToSend);
		Main.mc.thePlayer.sendChatMessage(messageToSend);
		toIgnore.add(resultToIgnore);
	}
	
	/**
	 * Consults the list of messages to ignore and sets the event to cancelled if it finds a match.
	 * @param event The event to check.
	 */
	public static void check(ClientChatReceivedEvent event) {
		for (String string : toIgnore) {
			if (event.message.getUnformattedText().equals(string) || event.message.getUnformattedText().matches(string)) {
				event.setCanceled(true);
				toIgnore.remove(string);
				return;
			}
		}
	}
	
}
