package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreHG {
	
	public static String msgWelcome = "\u00A7r\u00A7cWelcome to the Hardcore Games!\u00A7r";
	public static String msgChoose = "\u00A7r\u00A7cChoose your kit now!\u00A7r";
	public static String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	public static String msgBegin = "\u00A7r\u00A7cThe Tournament has begun!\u00A7r";
	public static String msgVulernable = "\u00A7r\u00A7cYou are no longer invincible.\u00A7r";
	public static String reMiniFeast = "\u00A7r\u00A7cA mini feast has appeared between \\( x: (.*) and x: (.*) \\) and \\( z: (.*) and z: (.*) \\)\u00A7r";
	public static String reFeast = "\u00A7r\u00A7cFeast will begin at \\((.*), (.*), (.*)\\).*";
	public static String msgFeastBegin = "\u00A7r\u00A7cThe Feast has begun!\u00A7r";
	public static String msgBonusFeast = "\u00A7r\u00A7cA \u00A7r\u00A7c\u00A7nbonus\u00A7r\u00A7c feast has been spawned! It can be anywhere on the whole map.\u00A7r";
	public static String reRemain = "\u00A7r\u00A7b(\\d*) players remaining.\u00A7r";
	public static String reParticipating = "\u00A7r\u00A7cThere are (.*) players participating.\u00A7r";
	public static String reWin = "\u00A7r\u00A7c(.*) wins!\u00A7r";
	public static String reCompass = "\u00A7r\u00A7eCompass pointing at (.*)\u00A7r";

	public static void setup() {
		
		new ChatTracker(reKit, Server.HG,
				new String[]{"hg:kit", "$1"});
		
		new ChatTracker(reFeast, Server.HG,
				new String[]{"hg:feast.x", "$1"},
				new String[]{"hg:feast.y", "$2"},
				new String[]{"hg:feast.z", "$3"});
		
		new ChatTracker(reRemain, Server.HG,
				new String[]{"hg:remain", "$1"});
		
		new ChatTracker(reParticipating, Server.HG,
				new String[]{"hg:remain", "$1"});
		
		new ChatTracker(reCompass, Server.HG,
				new String[]{"hg:tracking", "$1"});
		
		new ChatTrigger(reKit, "hg.kit", Server.HG,  
				new String[]{"kit", "$1"});
		
		new ChatTrigger(msgBegin, "hg.start", Server.HG);
		
		new ChatTrigger(msgVulernable, "hg.vulnerable", Server.HG);

		new ChatTrigger(reMiniFeast, "hg.feast.mini", Server.HG, 
				new String[]{"x1", "$1"},
				new String[]{"x2", "$2"},
				new String[]{"z1", "$3"},
				new String[]{"z2", "$4"});
		
		new ChatTrigger(msgFeastBegin, "hg.feast", Server.HG, 
				new String[]{"x", "hg:feast.x"},
				new String[]{"y", "hg:feast.y"},
				new String[]{"z", "hg:feast.z"});
		
	}
}
