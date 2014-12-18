package us.mcpvpmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import us.mcpvpmod.data.Data;

public class ServerHelper {

	public static Minecraft mc = Minecraft.getMinecraft();
	public static String currentIP = "";
	
	/**
	 * @return If we're on a server.
	 */
	public static boolean isMultiplayer() {
		return (!mc.isSingleplayer() && mc.func_147104_D() != null);
	}
	
	/**
	 * @return The last server IP connected to: relies only on a variable, not saved Data.
	 */ 
	public static String serverIP() {
		if (currentIP.equals(""))
			return "none";
		return currentIP;
	}
	
	/**
	 * @return The last server IP connected to: relies on both serverIP() and saved Data.
	 */ 
	public static String lastIP() {
		if (ServerHelper.serverIP().equals("none"))
			return Data.get("lastServer");
		return ServerHelper.serverIP();
	}
	
	public static String shortServerIP() {
		if (currentIP.equals(""))
			return "none";
		return currentIP.replaceAll("(\\w+\\d+\\.\\w{2}).*", "$1");
	}
	
	public static boolean onMCPVP() {
		return !currentIP.equals("") && Server.getServer() != Server.NONE;
	}
	
	/**
	 * @return A list of all player's GuiInfo. The list of players
	 * is identical to what is showing in tab.
	 */
	public static List<GuiPlayerInfo> getPlayersGui() {
		if (Main.mc.thePlayer == null) return new ArrayList<GuiPlayerInfo>();
		return Main.mc.thePlayer.sendQueue.playerInfoList;
	}
	
	/**
	 * @return A list of all player's as EntityPlayers. The list of players
	 * is identical to what is showing in tab.
	 */
	public static List<EntityPlayer> getPlayers() {
		List<EntityPlayer> players = new ArrayList<EntityPlayer>();
		for (GuiPlayerInfo player : getPlayersGui()) {
			players.add(Main.mc.theWorld.getPlayerEntityByName(player.name));
		}
		return players;
	}
	
	/**
	 * @return A list of all player's as EntityPlayers. Relies on the
	 * list of playerEntities in the current world.
	 */
	public static List<EntityPlayer> getPlayersFromWorld() {
		ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
		for (Object player : Main.mc.theWorld.playerEntities) {
			if (player instanceof EntityClientPlayerMP) continue;
			players.add(Main.mc.theWorld.getPlayerEntityByName(((EntityOtherPlayerMP)player).getDisplayName().replaceAll("\u00A7.(.*)", "$1")));
		}
		return players;
	}
	
	public static List<String> getPlayerUsernames() {
		ArrayList<String> players = new ArrayList<String>();
		for (EntityPlayer player : getPlayersFromWorld()) {
			if (player == null || player.getDisplayName() == null) continue;
			players.add(player.getDisplayName().replaceAll("\u00A7.", ""));
		}
		return players;
	}
	
	public static String getColorPrefix(String playerName) {
		
		// Cycle through each online player.
		for (GuiPlayerInfo player : getPlayersGui()) {
			
			if (player.name.startsWith("\u00A7")) {
				String realName = player.name.replaceAll("\u00A7.(.*)", "$1");

				if (playerName.startsWith(realName))
					return player.name.replaceAll("\u00A7(.).*", "$1");
			}

			if (player.name.endsWith(playerName) && player.name.startsWith("\u00A7")) {
				return player.name.replaceAll("\u00A7(.).*", "$1");
			}
		}
		return " ";
	}

}
