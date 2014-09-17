package us.mcpvpmod.events.chat.ctf;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.kits.KitsCTF;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class ChatMessages {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static ArrayList<String> chatHistory = new ArrayList<String>();
	
	public static String reStats = ".*\u00A76Kills: \u00A7f([0-9]+)\\s+\\(([0-9]+) in a row\\)\\s+\u00A76Deaths: \u00A7f([0-9]+)\\s+\u00A76Steals:\\s+\u00A7f([0-9]+)\\s+\u00A76Captures: \u00A7f([0-9]+).*";
	public static String reRecover = ".*\u00A7.(.*)\u00A7.\\s+recovered\\s+\u00A7.[A-Za-z]*\u00A7.'s\\s+flag!.*";
	public static String reMedic = ".*\u00A7.(.+)\u00A7.> \u00A7f\u00A75/m \u00A7fMedic!.*";
	public static String reTeams = ".*\u00A7(.)Captures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";
	public static String reMap = ".*\u00A7aMap: \u00A7r(.*)";
	public static String reFDay = "\u00A7aIt's free-play day! Everyone can play all classes!";
	public static String reHeadshot = "\u00A7.\u00A7.You headshotted (.*)!\u00A7.";
	public static String reLine = "\u00A7c\u00A7m\\s+";
	public static String reClass = "\u00A7.\u00A7.You have selected the (.*) class\u00A7.";
	public static String reGameOver = "\u00A7r\u00A76Game over! Winner: \u00A7r\u00A79(.*).*";
	
	/**
	 * Handles all "messages" that aren't a part of the scores and stats.
	 * @param event
	 */
	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();

		for (ChatTracker checker : ChatTracker.chatTrackers) {
			checker.check(message);
		}
		
		for (ChatTrigger trigger : ChatTrigger.triggers) {
			trigger.check(message);
		}
		
		// Game over.
		if (message.matches(reGameOver)) {
			System.out.println("Game over!");
			InfoCTF.gameWinner = message.replaceAll(reGameOver, "$1");
			System.out.println("Winner: " + InfoCTF.gameWinner);
		}
		
		/*
		if (message.matches(reMap)) {
			InfoCTF.currentMap = event.message.getUnformattedText().replaceAll(reMap, "$1");
		}
		*/
		
		if (message.matches(reFDay)) {
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
					if (KitsCTF.getClass(mc.thePlayer).toString().toLowerCase().equals(string.toLowerCase()) || InfoCTF.chosenClass.toLowerCase().equals(string.toLowerCase())) {
						String formatted = event.message.getUnformattedText().replaceAll("\u00A7(.)", "\u00A7$1\u00A7l");
						event.message = new ChatComponentText(formatted);
					}
				}
			}
		}
		
	}
	

}
