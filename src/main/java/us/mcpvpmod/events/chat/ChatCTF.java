package us.mcpvpmod.events.chat;

import java.util.ArrayList;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.kits.KitCTF;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatCTF {

	public static String reLine = "\u00A7c\u00A7m.*\u00A7r";
	public static String reFreeDay = "\u00A7aIt's free-play day! Everyone can play all classes!\u00A7r";
	public static String reTip = "\u00A73.*\u00A7r";
	public static String reTeams = ".*\u00A7(.)Captures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";
	public static String reStats = ".*\u00A76Kills: \u00A7f([0-9]+)\\s+\\(([0-9]+) in a row\\)\\s+\u00A76Deaths: \u00A7f([0-9]+)\\s+\u00A76Steals:\\s+\u00A7f([0-9]+)\\s+\u00A76Captures: \u00A7f([0-9]+).*";
	public static String reStreak = "\u00A7.(.*)\u00A7. ended \u00A7.(.*)'s \u00A7.([0-9]+) kill streak!.*";
	public static String reRestore = "\u00A7.(.*)\u00A76's flag has been restored!";
	public static String reAction = "\u00A7.(.*)\u00A7. (stole|dropped|picked up|recovered|captured) \u00A7.(.*)\u00A7.'s flag!.*";
	public static String reCompass = "\u00A7.Compass pointing at \u00A7.(.*)\u00A7. flag.*";
	public static String reTeam = "\u00A77You are on team .*";
	public static String reVisit = "\u00A7rVisit \u00A7rmcpvp.com\u00A7r for more info!\u00A7r";
	public static String reMedic = ".*\u00A7.(.+)\u00A7.> \u00A7f\u00A75/m \u00A7fMedic!.*";
	public static String reHeadshot = "\u00A7.\u00A7.You headshotted (.*)!\u00A7.";
	public static String reGameOver = "\u00A7r\u00A76Game over! Winner: \u00A7r\u00A79(.*).*";
	public static String reWho = "\u00A7.\\[\u00A77.TW\u00A7.\\] \u00A7.NomNuggetNom\u00A7.> \u00A7.\u00A7.\u00A7.\\/a \u00A7.\\?\u00A7.";
	public static String lastAlert = "";
	
	public static ArrayList<String> oldChat = new ArrayList<String>();
	public static ArrayList<String> chatBlock = new ArrayList<String>();
	
	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		handleAll(event);
		if (event.isCanceled()) return;

		if (message.matches(reLine)) {
			event.setCanceled(ConfigCTFChat.chatHistory);
			
		} else if (message.matches(reFreeDay)
				|| message.matches(reTip)) {
			handleMessages(event);
			
		} else if (message.matches(reTeams) || message.matches(reStats)) {
			ChatTracker.checkAll(message);
			
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
			filterAlerts(event);
			event.setCanceled(ConfigCTFChat.chatHistory);
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
					handleMessages(event);
					oldChat.add(message);
				} else {
					// Set it canceled based on config settings.
					event.setCanceled(ConfigCTFChat.chatHistory);
				}
			}
			// Index the chat message.
			chatBlock.add(message);
		}
	}
	
	/**
	 * Handles repeated messages, i.e. where players talk.
	 * @param event The chat event to handle.
	 */
	public static void handleMessages(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		// TODO: Move to Var system.
		if (message.matches(reFreeDay)) {
			InfoCTF.freeDay = true;
		}
		
		if (message.matches(reHeadshot)) {
			StatsCTF.headshots++;
			Medal.add("headshot");
		}
		
		if (Main.secondChat.shouldSplit(event)) {
			Main.secondChat.printChatMessage(event.message);
			event.setCanceled(true);
		}
		
		String reYay = "§f\\[§7TW§f\\] §6NomNuggetNom§.> §f§r§6\\/a §fYay! @(.*)";
		if (message.matches(reYay)) {
			if (message.replaceAll(reYay, "$1").equals(Main.mc.thePlayer.getDisplayName())) {
				CustomAlert.get("yay").show();
			}
		}
		
		// Medic calling.
		if (message.matches(reMedic)) {		
			if (StateCTF.getState() == StateCTF.PLAY) {
				for (String string : ConfigCTFChat.medicClasses) {
					if (KitCTF.getKit(Main.mc.thePlayer).toString().toLowerCase().equals(string.toLowerCase()) || InfoCTF.chosenClass.toLowerCase().equals(string.toLowerCase())) {
						String formatted = event.message.getUnformattedText().replaceAll("\u00A7(.)", "\u00A7$1\u00A7l");
						event.message = new ChatComponentText(formatted);
					}
				}
			}
		}
	}
	
	/**
	 * Handles in-game alerts, such as the flag being stolen.
	 * @param event The chat event to handle.
	 */
	public static void filterAlerts(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		// Sometimes messages are repeated: this prevents the repetition of alerts.
		if (message.equals(lastAlert)) return;
		
		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);

		// Recovered flags.
		if (message.matches(reAction) && message.replaceAll(reAction, "$2").equals("recovered")) {			
			if (message.replaceAll(reAction, "$1").equals(Main.mc.thePlayer.getDisplayName())) {
				StatsCTF.recovers++;
			}
			
		// Captured flag.
		} else if (message.matches(reAction) && message.replaceAll(reAction, "$2").equals("captured")) {
			// Game check to avoid messy notifications on game winning cap.
			if (StateCTF.getState() == StateCTF.PLAY) {

				if (message.replaceAll(reAction, "$1").equals(Main.mc.thePlayer.getDisplayName())) {
					Medal.add(new Medal("flagcap"));
				}
			}
		}
		
		if (message.matches(reAction) 
				|| message.matches(reGameOver)
				|| message.matches(reStreak)
				|| message.matches(reCompass)) {
				lastAlert = message;
				//event.setCanceled(true);
		}
	}
	
	public static void handleAll(ClientChatReceivedEvent event) {
		// Removal of messages that appear in split chat.
		for (Object chatLine : Main.secondChat.getMessages()) {
			
			// The chat message in the second chat.
			String oldMessage = ((ChatLine)chatLine).func_151461_a().getUnformattedText();
			if (oldMessage.equals(event.message.getUnformattedText())) {
				Main.l("Found a duplicate: \"%s\"", event.message.getUnformattedText());
				event.setCanceled(true);
			}
		}
	}
}
