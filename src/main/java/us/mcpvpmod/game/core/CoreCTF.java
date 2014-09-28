package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreCTF {

	public static String reStole = "(\u00A7..*)\u00A7. stole (\u00A7..*)\u00A7.'s flag!.*";
	public static String reDropped = "(\u00A7..*)\u00A7. dropped (\u00A7..*)\u00A7.'s flag!.*";
	public static String rePickedUp = "(\u00A7..*)\u00A7. picked up (\u00A7..*)\u00A7.'s flag!.*";
	public static String reRecovered = "(\u00A7..*)\u00A7. recovered (\u00A7..*)\u00A7.'s flag!.*";
	public static String reStreak = "(\u00A7..*)\u00A7. ended (\u00A7..*)'s \u00A7.([0-9]+) kill streak!.*";
	public static String reRestore = "(\u00A7..*)\u00A76's flag has been restored!.*";
	public static String reCompass = "\u00A7.Compass pointing at (\u00A7..*)\u00A77 flag.*";
	public static String reCaptured = "(\u00A7..*)\u00A7. captured (\u00A7..*)\u00A7.'s flag!.*";
	
	public static String reStats = ".*\u00A76Kills: \u00A7f([0-9]+)\\s+\\(([0-9]+) in a row\\)\\s+\u00A76Deaths: \u00A7f([0-9]+)\\s+\u00A76Steals:\\s+\u00A7f([0-9]+)\\s+\u00A76Captures: \u00A7f([0-9]+).*";
	public static String reBlueTeam = ".*\u00A79Captures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";
	public static String reRedTeam = ".*\u00A7cCaptures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";

	public static String reMap = "\u00A7.Map: \u00A7r(.*)";
	public static String reClass = "\u00A7.\u00A7.You have selected the (\\w+) class\u00A7.";
	public static String reGameOver = "\u00A7r\u00A76Game over! Winner: \u00A7r\u00A79(.*).*";
	public static String reFreeDay = "\u00A7aIt's free-play day! Everyone can play all classes!\u00A7r";

	public static void setup() {
		
		new ChatTracker(reClass, Server.CTF,
				new String[]{"ctf:kit", "$1"},
				new String[]{"ctf:class", "$1"});
		
		new ChatTracker(reRestore, Server.CTF,
				new String[]{"ctf:team", "$1"});

		new ChatTracker(reStats, Server.CTF, 
				new String[]{"ctf:kills", "$1"}, 
				new String[]{"ctf:streak", "$2"}, 
				new String[]{"ctf:deaths", "$3"}, 
				new String[]{"ctf:steals", "$4"}, 
				new String[]{"ctf:caps", "$4"});
		
		new ChatTracker(reBlueTeam, Server.CTF, 
				new String[]{"ctf:team.blue.caps", "$1"}, 
				new String[]{"ctf:team.blue.flag", "$3"}, 
				new String[]{"ctf:team.blue.players", "$4"});
		
		new ChatTracker(reRedTeam, Server.CTF, 
				new String[]{"ctf:team.red.caps", "$1"}, 
				new String[]{"ctf:team.red.flag", "$3"}, 
				new String[]{"ctf:team.red.players", "$4"});
		
		new ChatTracker(reMap, Server.CTF,
				new String[]{"ctf:map", "$1"});
		
		new ChatTracker(reGameOver, Server.CTF,
				new String[]{"ctf:winner", "$1"});
		
		new ChatTrigger(reStole, "flag.stolen", Server.CTF, 
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"stole", "action"});
		
		new ChatTrigger(reDropped, "flag.dropped", Server.CTF,  
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"dropped", "action"});
		
		new ChatTrigger(rePickedUp, "flag.pickedup", Server.CTF, 
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"pickedup", "action"});
		
		new ChatTrigger(reRecovered, "flag.recovered", Server.CTF, 			
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"recovered", "action"});
		
		new ChatTrigger(reRestore, "flag.restored", Server.CTF, 
				new String[]{"team", "$1"},
				new String[]{"restored", "action"});
		
		new ChatTrigger(reCaptured, "flag.captured", Server.CTF, 
				new String[]{"player", "$1"},
				new String[]{"team", "$2"},
				new String[]{"captured", "action"});
		
		new ChatTrigger(reStreak, "streak", Server.CTF, 
				new String[]{"killer", "$1"},
				new String[]{"killed", "$2"},
				new String[]{"streak", "$3"});
		
		new ChatTrigger(reClass, "class", Server.CTF, 
				new String[]{"kit", "$1"},
				new String[]{"class", "$1"});
		
		new ChatTrigger(reGameOver, "game.end", Server.CTF, 
				new String[]{"winner", "$1"});
		
		new BoardTracker("ctf:team.blue.wins", "\u00A79Blue");
		new BoardTracker("ctf:team.red.wins", "\u00A7cRed");
	}
	
}
