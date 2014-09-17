package us.mcpvpmod.game.core;

import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreCTF {

	public static String reStole = "(\u00A7..*)\u00A7. stole (\u00A7..*)\u00A7.'s flag!.*";
	public static String reDropped = "(\u00A7..*)\u00A7. dropped (\u00A7..*)\u00A7.'s flag!.*";
	public static String rePickedUp = "(\u00A7..*)\u00A7. picked up (\u00A7..*)\u00A7.'s flag!.*";
	public static String reRecovered = "(\u00A7..*)\u00A7. recovered (\u00A7..*)\u00A7.'s flag!.*";
	public static String reStreak = "(\u00A7..*)\u00A7. ended \u00A7.(.*)'s \u00A7.([0-9]+) kill streak!.*";
	public static String reRestore = "(\u00A7..*)\u00A76's flag has been restored!.*";
	public static String reCompass = "\u00A7.Compass pointing at (\u00A7..*)\u00A77 flag.*";
	
	public static String reStats = ".*\u00A76Kills: \u00A7f([0-9]+)\\s+\\(([0-9]+) in a row\\)\\s+\u00A76Deaths: \u00A7f([0-9]+)\\s+\u00A76Steals:\\s+\u00A7f([0-9]+)\\s+\u00A76Captures: \u00A7f([0-9]+).*";
	public static String reBlueTeam = ".*\u00A79Captures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";
	public static String reRedTeam = ".*\u00A7cCaptures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*";

	public static String reMap = "\u00A7.Map: \u00A7r(.*)";
	public static String reClass = "\u00A7.\u00A7.You have selected the (\\w+) class\u00A7.";
	
	public static void setup() {
		
		new ChatTrigger(reStole, "flag.stolen", 
				new String[]{"$1", "player"}, 
				new String[]{"$2", "team"});
		
		new ChatTrigger(reDropped, "flag.dropped", 
				new String[]{"$1", "player"}, 
				new String[]{"$2", "team"});
		
		new ChatTrigger(rePickedUp, "flag.pickedup",
				new String[]{"$1", "player"}, 
				new String[]{"$2", "team"});
		
		new ChatTrigger(reRecovered, "flag.recovered",			
				new String[]{"$1", "player"}, 
				new String[]{"$2", "team"});
		
		new ChatTrigger(reRestore, "flag.restored",
				new String[]{"$1", "player"}, 
				new String[]{"$2", "team"});
		
		new ChatTrigger(reRestore, "flag.restored",
				new String[]{"$1", "player"}, 
				new String[]{"$2", "team"});
		
		new ChatTrigger(reStreak, "streak",
				new String[]{"$1", "killer"},
				new String[]{"$2", "killed"},
				new String[]{"$3", "streak"});
		
		new ChatTrigger(reCompass, "compass",
				new String[]{"$1", "team"});
		
		new ChatTrigger(reClass, "class",
				new String[]{"$1", "class"},
				new String[]{"$1", "kit"});
		
		new ChatTracker(reClass,
				new String[]{"$1", "class"},
				new String[]{"$1", "kit"});
		
		new ChatTracker(reRestore, 
				new String[]{"$1", "ctf:a.team"});

		new ChatTracker(reStats, 
				new String[]{"$1", "ctf:i.kills"}, 
				new String[]{"$2", "ctf:i.streak"}, 
				new String[]{"$3", "ctf:i.deaths"}, 
				new String[]{"$4", "ctf:i.steals"}, 
				new String[]{"$4", "ctf:i.caps"});
		
		new ChatTracker(reBlueTeam, 
				new String[]{"$1", "ctf:i.team.blue.caps"}, 
				new String[]{"$3", "ctf:i.team.blue.flag"}, 
				new String[]{"$4", "ctf:i.team.blue.players"});
		
		new ChatTracker(reRedTeam, 
				new String[]{"$1", "ctf:i.team.red.caps"}, 
				new String[]{"$3", "ctf:i.team.red.flag"}, 
				new String[]{"$4", "ctf:i.team.red.players"});
		
		new ChatTracker(reMap,
				new String[]{"$1", "ctf:i.map"});
		
		new BoardTracker("\u00A79Blue", "ctf:i.team.blue.wins");
		new BoardTracker("\u00A7cRed", "ctf:i.team.red.wins");
	}
	
}
