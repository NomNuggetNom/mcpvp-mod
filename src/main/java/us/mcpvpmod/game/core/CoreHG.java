package us.mcpvpmod.game.core;

import us.mcpvpmod.Server;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class CoreHG {
	
	/** Appears on joining HG. Used to detect state. */
	public static final String msgWelcome = "\u00A7r\u00A7cWelcome to the Hardcore Games!\u00A7r";
	/** Indicates a kit has been chosen. <li>$1 = kit chosen. */
	public static final String reKit = "\u00A7.\u00A7.You are now a (.*)\u00A7.";
	/** Appears when the game begins. Used to detect state. */
	public static final String msgBegin = "\u00A7r\u00A7cThe Tournament has begun!\u00A7r";
	/** Appears when invincibility ends. Used to send an alert. */
	public static final String msgVulernable = "\u00A7r\u00A7cYou are no longer invincible.\u00A7r";
	/** Indicates a mini-feast has appeared. <li>$1 = min x <li>$2 = max x <li>$3 = min y <li>$3 = max y */
	public static final String reMiniFeast = "\u00A7r\u00A7cA mini feast has appeared between \\( x: (.*) and x: (.*) \\) and \\( z: (.*) and z: (.*) \\)\u00A7r";
	/** Indicates a feast is going to begin. <li>$1 = x <li>$2 = y <li>$3 = z */
	public static final String reFeast = "\u00A7r\u00A7cFeast will begin at \\((.*), (.*), (.*)\\).*";
	/** Appears when a feast has begun. */
	public static final String msgFeastBegin = "\u00A7r\u00A7cThe Feast has begun!\u00A7r";
	/** Appears when a bonus feast spawns. */
	public static final String msgBonusFeast = "\u00A7r\u00A7cA \u00A7r\u00A7c\u00A7nbonus\u00A7r\u00A7c feast has been spawned! It can be anywhere on the whole map.\u00A7r";
	/** <li>$1 = number of players remaining*/
	public static final String reRemain = "\u00A7r\u00A7b(\\d*) players remaining.\u00A7r";
	/** <li>$1 = number of players participating */
	public static final String reParticipating = "\u00A7r\u00A7cThere are (.*) players participating.\u00A7r";
	/** <li>$1 = the name of the player who won */
	public static final String reWin = "\u00A7r\u00A7c(.*) wins!\u00A7r";
	/** <li>$1 = the player the compass is pointing to */
	public static final String reCompass = "\u00A7r\u00A7eCompass pointing at (.*)\u00A7r";

	public static final void setup() {
		
		new ChatTracker(reKit, Server.HG,
				new String[]{"hg:kit", "$1"});
		
		new ChatTracker(reFeast, Server.HG,
				new String[]{"hg:feast.x", "$1"},
				new String[]{"hg:feast.y", "$2"},
				new String[]{"hg:feast.z", "$3"},
				new String[]{"hg:feast", "$1, $2, $3"});
		
		new ChatTracker(reMiniFeast, Server.HG,
				new String[]{"hg:feast.mini", "$1, $2, to $3, $4"});
		
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
