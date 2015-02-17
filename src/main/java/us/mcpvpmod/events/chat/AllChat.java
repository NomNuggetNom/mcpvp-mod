package us.mcpvpmod.events.chat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.event.ClickEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigChat;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.events.join.AllJoin;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.vars.AllVars;

/**
 * Chat handling for all servers.
 */ 
public class AllChat {
	
	/** The message received when any MCPVP server is joined. */
	public static final String MSG_LOGGED = "Now Logged in!";
	/** Used to parse results from /ip. */
	public static final String RE_IP = "Server Address: (.*)";
	/** Used to parse results from /pingtest. */
	public static final String RE_PING = "\u00A7aPing: (.*)ms";
	/** The regex for URLs. */
	public static final String RE_URL = "http://[^\\s]*";
	/** A compiled version of the regex for URLs. */
	public static final Pattern URL_PATTERN = Pattern.compile(RE_URL);

	public static boolean track = false;
	public static ArrayList<ClientChatReceivedEvent> tracked = new ArrayList<ClientChatReceivedEvent>();
	
	/**
	 * All chat for all MCPVP servers passes through here.
	 * @param event The chat event to check.
	 */
	public static void handleChat(ClientChatReceivedEvent event) {
		if (event.message == null || event.message.getUnformattedText() == null) return;
		String message = event.message.getUnformattedText();

		AllJoin.showWelcome();
		IgnoreResult.checkAll(event);

		// Track chat messages so the server
		// can catch up.
		if (track) tracked.add(event);

		// Catch ping messages.
		catchPing(message);
		
		//Main.l("message: \"%s\"", event.message.getFormattedText());
		if (message.equals(MSG_LOGGED)) {
			AllJoin.onJoin();
			track = true;
		}

		// Check for removal of chat.
		if (removeChat(message)) {
			event.setCanceled(true);
			Main.l("Message \"%s\" was removed due to chat settings.", message);
			
			// Returning might screw things up with alerts that need data from removed messages.
			return;
		}

		catchIP(message);
		catchYay(message);
		
		/*
		if (ConfigChat.fixLinks) 
			addLinks(event);
		
		// Censor chat. 
		ChatStyle old = event.message.getChatStyle();
		event.message = new ChatComponentText(censorChat(event.message.getFormattedText()));
		event.message.setChatStyle(old);
		*/
		
		checkSplit(event);
	}
	
	public static void catchPing(String message) {
		if (message.matches(RE_PING))
			AllVars.vars.put("ping", message.replaceAll(RE_PING, "$1"));
	}
	
	/**
	 * Used to catch IP messages from the server. Critical
	 * in detecting what server the client is connected to. Responsible
	 * for firing the {@link AllJoin#trueJoin(Server)} event.
	 * @param message The message to check for the IP.
	 */
	public static void catchIP(String message) {
		if (message.matches(RE_IP)) {
			Server joined = Server.getServer(message.replaceAll(RE_IP, "$1")); 
			Main.l("Received IP message! Current: %s, joined: %s", Server.getServer(), joined);
			if (Server.getServer() != joined) {
				ServerHelper.currentIP = message.replaceAll(RE_IP, "$1");
				AllJoin.trueJoin(joined);
			}
		}
	}
	
	public static void catchYay(String message) {
		String reYay = ".*\\[\u00A77TW\u00A7f\\].*NomNuggetNom.*>.*Yay! @(.*)";
		if (message.matches(reYay) && Server.getServer() != Server.CTF && Server.getServer() != Server.HS) {
			if (message.replaceAll(reYay, "$1").equals(Main.mc.thePlayer.getDisplayName())) {
				CustomAlert.get("yay").show();
			}
		}
	}
	
	/**
	 * Checks if the event should be sent to second chat. If so,
	 * print the message in the chat and cancel the event.
	 * @param event The event to check. Will be cancelled if
	 * it is sent to second chat.
	 */
	public static void checkSplit(ClientChatReceivedEvent event) {
		if (Main.secondChat.shouldSplit(event) 
				&& !Server.getServer().equals(Server.CTF)
				&& !Server.getServer().equals(Server.HS)) {
			Main.secondChat.printChatMessage(event.message);
			event.setCanceled(true);
		}
	}
	
	/**
	 * Re-adds link click events to any links in chat. Links
	 * must start with <code>http://</code> to be added properly.
	 * @param event The event to add links to.
	 */
	public static void addLinks(ClientChatReceivedEvent event) {
		if (!event.message.getUnformattedText().matches(".*" + RE_URL + ".*")) return;
		
		Matcher urlMatcher = URL_PATTERN.matcher(event.message.getUnformattedText());
		while (urlMatcher.find()) {
			event.message.getChatStyle().setChatClickEvent(
					new ClickEvent(ClickEvent.Action.OPEN_URL, urlMatcher.group()));
		}

	}
	
	/**
	 * @param message The message to consider moving.
	 * @return Whether the message should be removed in accordance with configuration settings.
	 */
	public static boolean removeChat(String message) {
		// Cycle through the config and check if our message contains any specified words.
		for (String string : ConfigChat.removeWords) {
			if (message.contains(string)) {
				return true;
			}
		}
		
		// This removes game tips. Might interfere un-intentionally with server messages that are blue.
		if (ConfigCTFChat.removeTips && message.startsWith("\u00A73")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Censors chat by replacing filterWords with asterisks.
	 * @param message The message to censor.
	 * @return The censored chat.
	 */
	public static String censorChat(String message) {
		String text = message;
		for (String string : ConfigChat.filterWords) {
			
			// <3
			if (string.equals("toot") && message.contains("redmantooth")) continue;
			
			// Use (?i) to ignore case. A little bit hacky.
			text = text.replaceAll("(?i)" + string, "*****");
		}
		return text;
	}
	
}
