package us.mcpvpmod.events.chat;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigChat;
import us.mcpvpmod.config.all.ConfigVersion;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.mgi.MGIEvent;
import us.mcpvpmod.triggers.ChatTrigger;

/**
 * Chat handling for all servers.
 */ 
public class AllChat {
	
	public static String msgLogged = "Now Logged in!";
	public static String reIP = "Server Address: (.*)";
	public static boolean getIP = false;
	public static boolean sentUpdateMessage = false;

	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		// Check for removal of chat.
		if (removeChat(message)) {
			event.setCanceled(true);
		} else {
			// Censor chat.
			event.message = new ChatComponentText(censorChat(event.message.getFormattedText().replaceAll("§", "\u00A7")));
		}
		
		//FMLLog.info("message: \"%s\"", message);
		if (message.equals(msgLogged)) {
			System.out.println("Logged in.");
			Main.mc.thePlayer.sendChatMessage("/ip");
			getIP = true;
			Main.secondChat.clearChatMessages();
		}
		
		if (message.matches(reIP) && getIP) {
			ServerHelper.currentIP = message.replaceAll(reIP, "$1");
			getIP = false;
			event.setCanceled(true);
		}

		if (Main.secondChat.shouldSplit(event) && !Server.getServer().equals(Server.CTF)) {
			Main.secondChat.printChatMessage(event.message);
			event.setCanceled(true);
		}
		
		if (!sentUpdateMessage && Main.mcpvpVersion.updateAvailable()) {
			String send = "";
			
			if (ConfigVersion.channel.equalsIgnoreCase("Main"))
				send = "#bold##underline# v" + Main.mcpvpVersion.main.mod + " is available for Minecraft " + Main.mcpvpVersion.main.mc + "! http://www.google.com/";
			 else if (ConfigVersion.channel.equalsIgnoreCase("Beta")) 
				send = "#bold##underline# v" + Main.mcpvpVersion.beta.mod + " is available for Minecraft " + Main.mcpvpVersion.beta.mc + "! http://www.google.com/";
			
			//new ClickEvent(ClickEvent.Action.OPEN_URL, "http://google.com/");
			Main.mc.thePlayer.addChatMessage(new ChatComponentText(Format.process(send)));
		}
	}
	
	/**
	 * @param message The message to consider moving.
	 * @return Whether the message should be removed in accordance with configuration settings.
	 */
	public static boolean removeChat(String message) {
		// Cycle through the config and check if our message contains any specified words.
		for (String string : ConfigChat.removeWords) {
			if (message.contains(string)) {
				return true;
			}
		}
		
		// This removes game tips. Might interfere un-intentionally with server messages that are blue.
		if (ConfigCTFChat.removeTips && message.startsWith("\u00A73")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Censors chat by replacing filterWords with asterisks.
	 * @param message The message to censor.
	 * @return The censored chat.
	 */
	public static String censorChat(String message) {
		String text = message;
		for (String string : ConfigChat.filterWords) {
			// Use (?i) to ignore case. A little bit hacky.
			text = text.replaceAll("(?i)" + string, "*****");
		}
		return text;
	}
	
}
