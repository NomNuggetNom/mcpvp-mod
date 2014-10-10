package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreHG {
	
	public static String msgWelcome = "§r§cWelcome to the Hardcore Games!§r";
	public static String msgChoose = "§r§cChoose your kit now!§r";
	public static String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	public static String msgBegin = "§r§cThe Tournament has begun!§r";
	public static String msgVulernable = "§r§cYou are no longer invincible.§r";
	public static String reMiniFeast = "§r§cA mini feast has appeared between \\( x: (.*) and x: (.*) \\) and \\( z: (.*) and z: (.*) \\)§r";
	public static String reFeast = "§r§cFeast will begin at \\((.*), (.*), (.*)\\).*";
	public static String msgFeastBegin = "§r§cThe Feast has begun!§r";
	public static String msgBonusFeast = "§r§cA §r§c§nbonus§r§c feast has been spawned! It can be anywhere on the whole map.§r";
	public static String reRemain = "§r§b(\\d*) players remaining.§r";
	public static String reParticipating = "§r§cThere are (.*) players participating.§r";
	public static String reWin = "§r§c(.*) wins!§r";

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
				new String[]{"x", "var:hg:feast.x"},
				new String[]{"y", "var:hg:feast.y"},
				new String[]{"z", "var:hg:feast.z"});
		
	}
}
