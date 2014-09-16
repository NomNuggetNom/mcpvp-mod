package us.mcpvpmod.game.alerts.ctf;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.gui.Medal;
import us.mcpvpmod.trackers.ChatTracker;
import us.mcpvpmod.triggers.ChatTrigger;

public class MessageAlerts {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static String reStole = "\u00A7.(.*)\u00A7. stole \u00A7.(.*)\u00A7.'s flag!.*";
	public static String reDropped = "\u00A7.(.*)\u00A7. dropped \u00A7.(.*)\u00A7.'s flag!.*";
	public static String rePickedUp = "\u00A7.(.*)\u00A7. picked up \u00A7.(.*)\u00A7.'s flag!.*";
	public static String reStreak = "\u00A7.(.*)\u00A7. ended \u00A7.(.*)'s \u00A7.([0-9]+) kill streak!.*";
	public static String reRestore = "\u00A7.(.*)\u00A76's flag has been restored!.*";
	public static String reCompass = "\u00A7.Compass pointing at \u00A7.(.*)\u00A77 flag.*";
	public static String reRecovered = "\u00A7.(.*)\u00A7. recovered \u00A7.(.*)\u00A7.'s flag!.*";
	public static String reCaptured = "\u00A7.(.*)\u00A7. captured \u00A7.(.*)\u00A7.'s flag!.*";
	public static String reGameOver = "Game over! Winner: (.*)";
	public static String reClass = "\u00A7.\u00A7.You have selected the (\\w+) class\u00A7.";
	public static String reWho = "\u00A7.\\[\u00A77.TW\u00A7.\\] \u00A7.NomNuggetNom\u00A7.> \u00A7.\u00A7.\u00A7.\\/a \u00A7.\\?\u00A7.";
	public static String lastAlert = "";
	public static String lastClass = "";
	public static String lastOnline = "";
	
	public static ItemStack blueWool = new ItemStack(Blocks.wool, 1, 11);
	public static ItemStack redWool = new ItemStack(Blocks.wool, 1, 14);
	
	public static ArrayList<String> alertConfig = new ArrayList<String>();
	
	public static void filterAlerts(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		if (message.equals(lastAlert)) {
			event.setCanceled(true); 
			return;
		}
			
		for (ChatTrigger trigger : ChatTrigger.triggers) {
			trigger.check(message);
		}
		
		for (ChatTracker checker : ChatTracker.chatTrackers) {
			checker.check(message);
		}
		
		/*
		// Stolen flags.
		if (message.matches(reStole)) {			
			CustomAlert.get("flag.stolen").setSource(message).show();
			SoundAlert.get("flag.stole").play();
			
		// Dropped flags.
		} else if (message.matches(reDropped)) {
			CustomAlert.get("flag.dropped").setSource(message).show();
			SoundAlert.get("flag.dropped").play();
			
		// Picked up.
		} else if (message.matches(rePickedUp)) {
			CustomAlert.get("flag.pickedup").setSource(message).show();
			SoundAlert.get("flag.pickedup").play();
			*/	
		// Recovered flags.
		if (message.matches(reRecovered)) {			
			if (message.replaceAll(reRecovered, "$1").equals(mc.thePlayer.getDisplayName())) {
				StatsCTF.recovers++;
			}
			
		// Captured flag.
		} else if (message.matches(reCaptured)) {
			// Game check to avoid messy notifications on game winning cap.
			if (StateCTF.getState() == StateCTF.PLAY) {
				CustomAlert.get("flag.captured").reset(message).show();
				SoundAlert.get("flag.captured").play();
				
				if (message.replaceAll(reCaptured, "$1").equals(mc.thePlayer.getDisplayName())) {
					Medal.add(new Medal("flagcap"));
				}
			}
		}
		
		if (message.matches(reStole) 
				|| message.matches(reDropped)
				|| message.matches(rePickedUp)
				|| message.matches(reRecovered)				
				|| message.matches(reCaptured)
				|| message.matches(reRestore)
				|| message.matches(reGameOver)
				|| message.matches(reStreak)
				|| message.matches(reCompass)) {
				lastAlert = message;
				//event.setCanceled(true);
		}
	}
}
