package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.BoardTracker;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreCTF {

	public static final String 
		RE_ACTION = "\u00A7.(.*)\u00A7. (stole|dropped|picked up|recovered|captured) (\u00A7..*)\u00A7.'s flag!.*",
		RE_STOLE = "(\u00A7..*)\u00A7. stole (\u00A7..*)\u00A7.'s flag!.*",
		RE_DROPPED = "(\u00A7..*)\u00A7. dropped (\u00A7..*)\u00A7.'s flag!.*",
		RE_PICKEDUP = "(\u00A7..*)\u00A7. picked up (\u00A7..*)\u00A7.'s flag!.*",
		RE_RECOVERED = "(\u00A7..*)\u00A7. recovered (\u00A7..*)\u00A7.'s flag!.*",
		RE_STREAK = "(\u00A7..*)\u00A7. ended (\u00A7..*)'s \u00A7.([0-9]+) kill streak!.*",
		RE_RESTORE = "(\u00A7..*)\u00A76's flag has been restored!.*",
		RE_COMPASS = "\u00A7.Compass pointing at (\u00A7..*)\u00A77 flag.*",
		RE_CAPTURED = "(\u00A7..*)\u00A7. captured (\u00A7..*)\u00A7.'s flag!.*",
		RE_STATS = ".*\u00A76Kills: \u00A7f([0-9]+)\\s+\\(([0-9]+) in a row\\)\\s+\u00A76Deaths: \u00A7f([0-9]+)\\s+\u00A76Steals:\\s+\u00A7f([0-9]+)\\s+\u00A76Captures: \u00A7f([0-9]+).*",
		RE_BLUE_TEAM = ".*\u00A79Captures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*",
		RE_RED_TEAM = ".*\u00A7cCaptures: \u00A7r([0-9])/([0-9])\\s+\u00A7.Flag: \u00A7r\\[(.+)]\u00A7.\\s+Players: \u00A7r([0-9]+).*",
		RE_MAP = "\u00A7.Map: \u00A7r(.*)",
		RE_CLASS = "\u00A7.\u00A7.You have selected the (\\w+) class\u00A7.",
		RE_GAME_OVER = "\u00A7r\u00A76Game over! Winner: \u00A7r\u00A79(.*).*",
		MSG_FREE_DAY = "\u00A7aIt's free-play day! Everyone can play all classes!\u00A7r";

	public static void setup() {
		
		new ChatTracker(RE_CLASS, Server.CTF,
				new String[]{"ctf:class", "$1"});
		
		new ChatTracker(RE_RESTORE, Server.CTF,
				new String[]{"ctf:team", "$1"});

		new ChatTracker(RE_STATS, Server.CTF, 
				new String[]{"ctf:kills", "$1"}, 
				new String[]{"ctf:streak", "$2"}, 
				new String[]{"ctf:deaths", "$3"}, 
				new String[]{"ctf:steals", "$4"}, 
				new String[]{"ctf:caps", "$4"});
		
		new ChatTracker(RE_BLUE_TEAM, Server.CTF, 
				new String[]{"ctf:team.blue.caps", "$1"}, 
				new String[]{"ctf:totalcaps", "$2"},
				new String[]{"ctf:team.blue.flag", "$3"}, 
				new String[]{"ctf:team.blue.players", "$4"});
		
		new ChatTracker(RE_RED_TEAM, Server.CTF, 
				new String[]{"ctf:team.red.caps", "$1"}, 
				new String[]{"ctf:totalcaps", "$2"},
				new String[]{"ctf:team.red.flag", "$3"}, 
				new String[]{"ctf:team.red.players", "$4"});
		
		new ChatTracker(RE_MAP, Server.CTF,
				new String[]{"ctf:map", "$1"});
		
		new ChatTracker(RE_GAME_OVER, Server.CTF,
				new String[]{"ctf:winner", "$1"});
		
		new ChatTracker(RE_ACTION, Server.CTF,
				new String[]{"player", "$1"},
				new String[]{"action", "$2"},
				new String[]{"team", "$3"});
		
		new ChatTrigger(RE_STOLE, "flag.stolen", Server.CTF, 
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"stole", "action"});
		
		new ChatTrigger(RE_DROPPED, "flag.dropped", Server.CTF,  
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"dropped", "action"});
		
		new ChatTrigger(RE_PICKEDUP, "flag.pickedup", Server.CTF, 
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"pickedup", "action"});
		
		new ChatTrigger(RE_RECOVERED, "flag.recovered", Server.CTF, 			
				new String[]{"player", "$1"}, 
				new String[]{"team", "$2"},
				new String[]{"recovered", "action"});
		
		new ChatTrigger(RE_RESTORE, "flag.restored", Server.CTF, 
				new String[]{"team", "$1"},
				new String[]{"restored", "action"});
		
		new ChatTrigger(RE_CAPTURED, "flag.captured", Server.CTF, 
				new String[]{"player", "$1"},
				new String[]{"team", "$2"},
				new String[]{"captured", "action"});
		
		new ChatTrigger(RE_STREAK, "streak", Server.CTF, 
				new String[]{"killer", "$1"},
				new String[]{"killed", "$2"},
				new String[]{"streak", "$3"});
		
		new ChatTrigger(RE_CLASS, "class", Server.CTF, 
				new String[]{"kit", "$1"},
				new String[]{"class", "$1"});
		
		new ChatTrigger(RE_GAME_OVER, "game.end", Server.CTF, 
				new String[]{"winner", "$1"});
		
		new ChatTrigger(RE_COMPASS, "compass", Server.CTF, 
				new String[]{"team", "$1"});
		
		new BoardTracker("ctf:team.blue.wins", "\u00A79Blue");
		new BoardTracker("ctf:team.red.wins", "\u00A7cRed");
	}
	
}
