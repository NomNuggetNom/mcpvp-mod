package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreSmash {

	public static String rePlayAs = "\u00A7r\u00A77This week's free class is \u00A7r\u00A7e(.*)\u00A7r\u00A77!\u00A7r";
	public static String reKit = "\u00A7r\u00A76You have selected the \u00A7r\u00A7a(.*)\u00A7r\u00A76 class\u00A7r";
	public static String reBegin = "\u00A7r\u00A793-Round Game will start in (.*) seconds*!\u00A7r";
	public static String reMap = "\u00A7r\u00A77You're playing on \u00A7r\u00A7b(.*) \\(#(.*)\\) by (.*)\u00A7r\u00A77!\u00A7r";
	public static String reRoundBegin = "\u00A7r\u00A76Round will begin in (.*) seconds*!\u00A7r";
	public static String reRoundEnd = "\u00A7r\u00A7dRound ending in (.*) seconds*!\u00A7r";
	public static String reRoundOver = "\u00A7r\u00A76Game finished!\u00A7r";
	public static String reRoundWinner = "(?:\u00A7r\u00A7(.*) wins with a score of (.*)!\u00A7r|\u00A7r\u00A76MrRandom999 and LlamasATA_ draw with a score of 2!\u00A7r)";
	public static String reElo = "\u00A7r\u00A77Your Elo Rating: (.*)\u00A7r";
	
	public static void setup() {
		
		new ChatTracker(rePlayAs, Server.SMASH,
				new String[]{"smash:kit", "$1"});
		
		new ChatTracker(reElo, Server.SMASH,
				new String[]{"smash:elo", "$1"});
		
		new ChatTracker(reKit, Server.SMASH,
				new String[]{"smash:kit", "$1"});
		
		new ChatTracker(reMap, Server.SMASH,
				new String[]{"smash:map.name", "$1"},
				new String[]{"smash:map.id", "$2"},
				new String[]{"smash:map.author", "$3"});
		
		new ChatTrigger(reKit, "smash.kit", Server.SMASH, 
				new String[]{"kit", "$1"});

	}
	
}