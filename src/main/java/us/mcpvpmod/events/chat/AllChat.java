package us.mcpvpmod.events.chat;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigChat;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.events.join.AllJoin;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.vars.AllVars;

/**
 * Chat handling for all servers.
 */ 
public class AllChat {
	
	public static String msgLogged = "Now Logged in!";
	public static String reIP = "Server Address: (.*)";
	public static String rePing = "\u00A7aPing: (.*)ms";
	public static boolean getIP = false;
	public static boolean sentUpdateMessage = false;

	public static void handleChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		
		AllJoin.showWelcome();
		IgnoreResult.checkAll(event);
		
		if (message.matches(rePing)) {
			AllVars.vars.put("ping", message.replaceAll(rePing, "$1"));
		}
		
		//FMLLog.info("message: \"%s\"", message);
		if (message.equals(msgLogged)) {
			Server.onJoin(Main.mc.func_147104_D().serverIP);
			//HandleJoinMCPVP.onJoin();
		}
		
		// Check for removal of chat.
		if (removeChat(message)) {
			event.setCanceled(true);
			Main.l("Message \"%s\" was removed due to chat settings.", message);
			// Returning might screw things up with alerts that need data from removed messages.
			return;
		} else {
			// Censor chat.
			event.message = new ChatComponentText(censorChat(event.message.getFormattedText().replaceAll("§", "\u00A7")));
		}
		
		if (message.matches(reIP)) {
			ServerHelper.currentIP = message.replaceAll(reIP, "$1");
		}

		if (Main.secondChat.shouldSplit(event) 
				&& !Server.getServer().equals(Server.CTF)
				&& !Server.getServer().equals(Server.HS)) {
			Main.secondChat.printChatMessage(event.message);
			event.setCanceled(true);
		}
		
		String reYay = "\u00A7f\\[\u00A77TW\u00A7f\\].*NomNuggetNom.*>.*Yay! @(.*)";
		if (message.matches(reYay) && Server.getServer() != Server.CTF && Server.getServer() != Server.HS) {
			if (message.replaceAll(reYay, "$1").equals(Main.mc.thePlayer.getDisplayName())) {
				CustomAlert.get("yay").show();
			}
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
