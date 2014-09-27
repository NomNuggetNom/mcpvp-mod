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
	public static String reFeast = "§r§cFeast will begin at \\((.*), (.*), (.*)\\) in 1 seconds.§r";
	public static String msgFeastBegin = "§r§cThe Feast has begun!§r";
	public static String msgBonusFeast = "§r§cA §r§c§nbonus§r§c feast has been spawned! It can be anywhere on the whole map.§r";
	public static String reWin = "§r§c(.*) wins!§r";
	
	public static void setup() {
		
		new ChatTracker(reKit, Server.HG,
				new String[]{"$1", "hg:kit"});
		
		new ChatTracker(reFeast, Server.HG,
				new String[]{"$1", "hg:feast.x"},
				new String[]{"$2", "hg:feast.y"},
				new String[]{"$3", "hg:feast.z"});
		
		new ChatTrigger(reKit, "hg.kit", Server.HG,  
				new String[]{"$1", "kit"});
		
		new ChatTrigger(msgBegin, "hg.start", Server.HG);
		
		new ChatTrigger(msgVulernable, "hg.vulnerable", Server.HG);

		new ChatTrigger(reMiniFeast, "hg.feast.mini", Server.HG, 
				new String[]{"$1", "x1"},
				new String[]{"$2", "x2"},
				new String[]{"$1", "z1"},
				new String[]{"$2", "z2"});
		
		new ChatTrigger(reFeast, "hg.feast", Server.HG, 
				new String[]{"hg:feast.x", "x"},
				new String[]{"hg:feast.y", "y"},
				new String[]{"hg:feast.z", "z"});
		
	}
}
