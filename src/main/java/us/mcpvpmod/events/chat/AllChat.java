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
import us.mcpvpmod.events.HandleJoinMCPVP;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.json.StreamJSON;
import us.mcpvpmod.mgi.MGIEvent;
import us.mcpvpmod.triggers.ChatTrigger;
import us.mcpvpmod.util.Format;

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
		HandleJoinMCPVP.check();
		
		// Check for removal of chat.
		if (removeChat(message)) {
			event.setCanceled(true);
		} else {
			// Censor chat.
			event.message = new ChatComponentText(censorChat(event.message.getFormattedText().replaceAll("§", "\u00A7")));
		}
		
		//FMLLog.info("message: \"%s\"", message);
		if (message.equals(msgLogged)) {
			HandleJoinMCPVP.onJoin();
			//Main.secondChat.clearChatMessages();
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
		
		if (!sentUpdateMessage && Main.mcpvpVersion.updateAvailable() && ConfigVersion.updateNotifications) {
			String msg = "";
			
			if (ConfigVersion.channel.equalsIgnoreCase("Main"))
				msg = Format.s("update-msg").replace("<VERSION>", Main.mcpvpVersion.main.mod).replace("<MCVERSION>", Main.mcpvpVersion.main.mc);
			 else if (ConfigVersion.channel.equalsIgnoreCase("Beta")) 
				msg = Format.s("update-msg").replace("<VERSION>", Main.mcpvpVersion.beta.mod).replace("<MCVERSION>", Main.mcpvpVersion.beta.mc);
			
			IChatComponent send = new ChatComponentText(Format.process(msg));
			send.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Format.s("update-url")));
			Main.mc.thePlayer.addChatMessage(send);
			sentUpdateMessage = true;
		}
		
		String reYay = "§f\\[§7TW§f\\] §6NomNuggetNom§.> §f§r§6\\/a §fYay! @(.*)";
		if (message.matches(reYay) && Server.getServer() != Server.CTF || Server.getServer() != Server.HS) {
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
