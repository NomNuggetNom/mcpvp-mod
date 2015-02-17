package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreSmash {

	public static final String 
		RE_FREE = "\u00A7r\u00A77This week's free class is \u00A7r\u00A7.(.*).*!.*\u00A7r.*",
		RE_KIT = "\u00A7r\u00A76Selected the \u00A7r\u00A7a(.*)\u00A7r\u00A76 class!\u00A7r",
		RE_BEGIN = "\u00A7r\u00A793-Round Game will start in (.*) seconds*!\u00A7r",
		RE_MAP = "\u00A7r\u00A77You're playing on \u00A7r\u00A7b(.*)!.*",
		RE_ROUND_BEGIN = "\u00A7r\u00A76Round will begin in (.*) seconds*!\u00A7r",
		RE_ROUND_END = "\u00A7r\u00A7dRound ending in (.*) seconds*!\u00A7r",
		MSG_GAME_OVER = "\u00A7r\u00A76Game finished!\u00A7r",
		RE_ROUND_WINNER = "(?:\u00A7r\u00A7(.*) wins with a score of (.*)!\u00A7r|\u00A7r\u00A76MrRandom999 and LlamasATA_ draw with a score of 2!\u00A7r)",
		RE_ELO = "\u00A7r\u00A77Your Elo Rating: (.*)\u00A7r";
	
	public static void setup() {
		
		new ChatTracker(RE_FREE, Server.SMASH,
				new String[]{"smash:kit", "$1"});
		
		new ChatTracker(RE_ELO, Server.SMASH,
				new String[]{"smash:elo", "$1"});
		
		new ChatTracker(RE_KIT, Server.SMASH,
				new String[]{"smash:kit", "$1"});
		
		new ChatTracker(RE_MAP, Server.SMASH,
				new String[]{"smash:map.name", "$1"});
		
		new ChatTrigger(RE_KIT, "smash.kit", Server.SMASH, 
				new String[]{"kit", "$1"});

	}
	
}