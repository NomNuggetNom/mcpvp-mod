package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreKit {

	public static String 
		RE_STREAK = "\u00A7.\u00A7.Woah, player \u00A7.\u00A7.(\\w+)\u00A7.\u00A7. just got a killstreak of (\\d+)\u00A7.",
		RE_STREAK_END = "\u00A7.\u00A7.Player \u00A7.\u00A7.(\\w+)\u00A7.\u00A7. just ended the (\\d+) killstreak of (\\w+)\u00A7.";
	
	public static String reKit = "§r§aYou are now a (.*)§r";

	public static void setup() {
		
		
		/*
		 * V2
		 */
		
		new ChatTracker(reKit, Server.KIT,
				new String[]{"kit:kit", "$1"});
		
		new ChatTrigger(reKit, "kit.kit", Server.KIT,
				new String[]{"kit", "$1"});
		
		new BoardTracker("kit:elo", "1V1 ELO:");
		new BoardTracker("kit:coins", "Coins:");
		
		/*
		 * V1
		 */
		
		new ChatTrigger(RE_STREAK, "kit.streak.get", Server.KIT, 
				new String[]{"player", "$1"}, 
				new String[]{"streak", "$2"});
		
		new ChatTrigger(RE_STREAK_END, "kit.streak.end", Server.KIT,  
				new String[]{"killer", "$1"}, 
				new String[]{"streak", "$2"},
				new String[]{"killed", "$3"});
		
		new BoardTracker("kit:credits", "Credits:");
		new BoardTracker("kit:deaths", "Deaths:");
		new BoardTracker("kit:kills", "Kills:");
		new BoardTracker("kit:worth", "Your worth:");
		new BoardTracker("kit:ks", "Killstreak:");
	}
	
}