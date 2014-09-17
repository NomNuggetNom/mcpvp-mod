package us.mcpvpmod.events.chat.ctf;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.game.alerts.ctf.MessageAlerts;
import us.mcpvpmod.game.state.StateCTF;

public class ChatHistory {

	public static ArrayList<String> messages = new ArrayList<String>(Arrays.asList("", "", "", "", "", "", "", "", "", "", "", ""));
	public static ArrayList<String> oldChat = new ArrayList<String>();
	public static ArrayList<String> chatBlock = new ArrayList<String>();
	
	public static String reLine = "\u00A7c\u00A7m.*\u00A7r";
	public static String reFreeDay = "\u00A7aIt's free-play day! Everyone can play all classes!\u00A7r";
	public static String reTip = "\u00A73.*\u00A7r";
	public static String reTeams = ".*\u00A7(.)Captures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";
	public static String reStats = ".*\u00A76Kills: \u00A7f([0-9]+)\\s+\\(([0-9]+) in a row\\)\\s+\u00A76Deaths: \u00A7f([0-9]+)\\s+\u00A76Steals:\\s+\u00A7f([0-9]+)\\s+\u00A76Captures: \u00A7f([0-9]+).*";
	public static String reStreak = "\u00A7.(.*)\u00A7. ended \u00A7.(.*)'s \u00A7.([0-9]+) kill streak!.*";
	public static String reRestore = "\u00A7.(.*)\u00A76's flag has been restored!";
	public static String reAction = "\u00A7.(.*)\u00A7. (stole|dropped|picked up|recovered|captured) \u00A7.(.*)\u00A7.'s flag!.*";
	public static String reCompass = "\u00A7.Compass pointing at \u00A7.(.*)\u00A7. flag.*";
	//TODO fix compass!
	public static String reTeam = "\u00A77You are on team .*";
	public static String reVisit = "\u00A7rVisit \u00A7rmcpvp.com\u00A7r for more info!\u00A7r";
	
	public static ArrayList<String> stats = new ArrayList<String>(Arrays.asList(reLine, reFreeDay, reTip, reTeams, reStats));
	public static ArrayList<String> alerts = new ArrayList<String>(Arrays.asList(reRestore, reAction, reCompass, reTeam));
	
	/**
	 * The main chat handler. ALL TEXT passes through this.
	 * @param event
	 */
	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();	

		if (message.matches(reLine)) {
			event.setCanceled(ConfigCTFChat.chatHistory);
			
		} else if (message.matches(reFreeDay)
				|| message.matches(reTip)) {
			ChatMessages.handleChat(event);
			
		} else if (message.matches(reTeams)) {
			ChatStats.handleChat(event);
			event.setCanceled(ConfigCTFChat.chatHistory);
			
		} else if (message.matches(reStats)) {
			ChatStats.handleChat(event);
			event.setCanceled(ConfigCTFChat.chatHistory);
			
		} else if (message.matches(reLine) 
				|| message.matches(reTeams) 
				|| message.matches(reStats) 
				|| message.matches(reAction) 
				|| message.matches(reRestore)
				|| message.matches(reStreak)
				|| message.matches(reCompass)
				|| message.matches(reTeam)
				|| message.matches(reVisit)) {
			MessageAlerts.filterAlerts(event);
		} else {
			// The chat section has a number of lines depending on the state.
			// If we have more than that number, remove the first.
			if (chatBlock.size() == StateCTF.getState().chatLines()) {
				chatBlock.remove(0);
			}
			
			// Does our new chat equal the old?
			if (!oldChat.equals(chatBlock)) {
				// Does it contain our message?
				if (!oldChat.contains(message)) {
					// It's a new message!
					// Process the possible alerts and index it.
					ChatMessages.handleChat(event);
					oldChat.add(message);
				} else {
					event.setCanceled(ConfigCTFChat.chatHistory);
				}
			}
			// Index the chat message.
			chatBlock.add(message);
		}
	}
}
