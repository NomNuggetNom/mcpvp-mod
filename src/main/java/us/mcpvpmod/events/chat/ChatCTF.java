package us.mcpvpmod.events.chat;

import java.util.ArrayList;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.kits.KitsCTF;
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
	public static String reClass = "\u00A7.\u00A7.You have selected the (.*) class\u00A7.";
	public static String reGameOver = "\u00A7r\u00A76Game over! Winner: \u00A7r\u00A79(.*).*";
	public static String reWho = "\u00A7.\\[\u00A77.TW\u00A7.\\] \u00A7.NomNuggetNom\u00A7.> \u00A7.\u00A7.\u00A7.\\/a \u00A7.\\?\u00A7.";
	public static String lastAlert = "";
	public static String lastClass = "";
	public static String lastOnline = "";
	
	public static ArrayList<String> oldChat = new ArrayList<String>();
	public static ArrayList<String> chatBlock = new ArrayList<String>();
	
	public static void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();	

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
	
	public static void handleMessages(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		ChatTracker.checkAll(message);
		ChatTrigger.checkAll(message);
		
		// Game over.
		// TODO: Replace with ChatTacker.
		//if (message.matches(reGameOver)) {
			//InfoCTF.gameWinner = message.replaceAll(reGameOver, "$1");
		//}
		
		/*
		if (message.matches(reMap)) {
			InfoCTF.currentMap = event.message.getUnformattedText().replaceAll(reMap, "$1");
		}
		*/
		
		if (message.matches(reFreeDay)) {
			InfoCTF.freeDay = true;
		}
		
		if (message.matches(reHeadshot)) {
			StatsCTF.headshots++;
			Medal.add("headshot");
		}
		
		// Changed class.
		//TODO fix
		 if (message.matches(reClass)) {
			 /*
			 String classChosen = message.replaceAll(reClass, "$1");
			 Vars.put("class", classChosen);
			 Vars.put("kit", classChosen);
			 CustomAlert.get("class").show();
			 InfoCTF.chosenClass = classChosen;
			 */
		}
		 
		// Medic calling.
		if (message.matches(reMedic)) {		
			String needMedic = message.replaceAll(reMedic, "$1");
			if (StateCTF.getState() == StateCTF.PLAY) {
				for (String string : ConfigCTFChat.medicClasses) {
					if (KitsCTF.getClass(Main.mc.thePlayer).toString().toLowerCase().equals(string.toLowerCase()) || InfoCTF.chosenClass.toLowerCase().equals(string.toLowerCase())) {
						String formatted = event.message.getUnformattedText().replaceAll("\u00A7(.)", "\u00A7$1\u00A7l");
						event.message = new ChatComponentText(formatted);
					}
				}
			}
		}
	}
	
	public static void filterAlerts(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		// Sometimes messages are repeated: this prevents the repetition of alerts.
		if (message.equals(lastAlert)) return;
			
		ChatTrigger.checkAll(message);
		ChatTracker.checkAll(message);
		
		// Recovered flags.
		if (message.matches(reAction) && message.replaceAll(reAction, "$2").equals("recovered")) {			
			if (message.replaceAll(reAction, "$1").equals(Main.mc.thePlayer.getDisplayName())) {
				StatsCTF.recovers++;
			}
			
		// Captured flag.
		} else if (message.matches(reAction) && message.replaceAll(reAction, "$2").equals("captured")) {
			// Game check to avoid messy notifications on game winning cap.
			if (StateCTF.getState() == StateCTF.PLAY) {
				CustomAlert.get("flag.captured").reset(message).show();
				SoundAlert.get("flag.captured").play();
				
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
}
