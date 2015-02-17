package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreHG {
	
	public static final String 
		MSG_WELCOME = "\u00A7r\u00A7cWelcome to the Hardcore Games!\u00A7r",
		RE_KIT = "\u00A7.\u00A7.You are now a (.*)\u00A7.",
		MSG_BEGIN = "\u00A7r\u00A7cThe Tournament has begun!\u00A7r",
		MSG_VULNERABLE = "\u00A7r\u00A7cYou are no longer invincible.\u00A7r",
		RE_MINIFEAST = "\u00A7r\u00A7cA mini feast has appeared between \\( x: (.*) and x: (.*) \\) and \\( z: (.*) and z: (.*) \\)\u00A7r",
		RE_FEAST = "\u00A7r\u00A7cFeast will begin at \\((.*), (.*), (.*)\\).*",
		MSG_FEAST_BEGIN = "\u00A7r\u00A7cThe Feast has begun!\u00A7r",
		MSG_BONUSFEAST = "\u00A7r\u00A7cA \u00A7r\u00A7c\u00A7nbonus\u00A7r\u00A7c feast has been spawned! It can be anywhere on the whole map.\u00A7r",
		RE_REMAIN = "\u00A7r\u00A7b(\\d*) players remaining.\u00A7r",
		RE_PARTICIPATING = "\u00A7r\u00A7cThere are (.*) players participating.\u00A7r",
		RE_WIN = "\u00A7r\u00A7c(.*) wins!\u00A7r",
		RE_COMPASS = "\u00A7r\u00A7eCompass pointing at (.*)\u00A7r";

	public static final void setup() {
		
		new ChatTracker(RE_KIT, Server.HG,
				new String[]{"hg:kit", "$1"});
		
		new ChatTracker(RE_FEAST, Server.HG,
				new String[]{"hg:feast.x", "$1"},
				new String[]{"hg:feast.y", "$2"},
				new String[]{"hg:feast.z", "$3"},
				new String[]{"hg:feast", "$1, $2, $3"});
		
		new ChatTracker(RE_MINIFEAST, Server.HG,
				new String[]{"hg:feast.mini", "$1, $2, to $3, $4"});
		
		new ChatTracker(RE_REMAIN, Server.HG,
				new String[]{"hg:remain", "$1"});
		
		new ChatTracker(RE_PARTICIPATING, Server.HG,
				new String[]{"hg:remain", "$1"});
		
		new ChatTracker(RE_COMPASS, Server.HG,
				new String[]{"hg:tracking", "$1"});
		
		new ChatTrigger(RE_KIT, "hg.kit", Server.HG,  
				new String[]{"kit", "$1"});
		
		new ChatTrigger(MSG_BEGIN, "hg.start", Server.HG);
		
		new ChatTrigger(MSG_VULNERABLE, "hg.vulnerable", Server.HG);

		new ChatTrigger(RE_MINIFEAST, "hg.feast.mini", Server.HG, 
				new String[]{"x1", "$1"},
				new String[]{"x2", "$2"},
				new String[]{"z1", "$3"},
				new String[]{"z2", "$4"});
		
		new ChatTrigger(MSG_FEAST_BEGIN, "hg.feast", Server.HG, 
				new String[]{"x", "hg:feast.x"},
				new String[]{"y", "hg:feast.y"},
				new String[]{"z", "hg:feast.z"});
		
	}
}
