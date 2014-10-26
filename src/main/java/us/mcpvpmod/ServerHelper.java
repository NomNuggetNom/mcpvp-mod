package us.mcpvpmod;

import java.util.ArrayList;
import java.util.List;

import us.mcpvpmod.util.Data;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

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
		if (currentIP.equals("")) {
			return "none";
		} else {
			return currentIP;
		}
	}
	
	/**
	 * @return The last server IP connected to: relies on both serverIP() and saved Data.
	 */ 
	public static String lastIP() {
		if (ServerHelper.serverIP().equals("none")) {
			return Data.get("lastServer");
		} else {
			return ServerHelper.serverIP();
		}
	}
	
	public static String shortServerIP() {
		if (currentIP.equals("")) {
			return "none";
		} else {
			return currentIP.replaceAll("(\\w+\\d+\\.\\w{2}).*", "$1");
		}
	}
	
	public static boolean onMCPVP() {
		return !currentIP.equals("") && Server.getServer() != Server.NONE;
	}
	
	/**
	 * @return A list of all GuiPlayerInfo
	 */
	public static List<GuiPlayerInfo> getPlayersGui() {
		if (Main.mc.thePlayer == null) return new ArrayList<GuiPlayerInfo>();
		return Main.mc.thePlayer.sendQueue.playerInfoList;
	}
	
	/**
	 * @return A list of all GuiPlayerInfo
	 */
	public static List<EntityPlayer> getPlayers() {
		List<EntityPlayer> players = new ArrayList<EntityPlayer>();
		for (GuiPlayerInfo player : getPlayersGui()) {
			players.add(Main.mc.theWorld.getPlayerEntityByName(player.name));
		}
		return players;
	}
	
	public static List<EntityPlayer> getPlayersFromWorld() {
		ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
		for (Object player : Main.mc.theWorld.playerEntities) {
			if (player instanceof EntityClientPlayerMP) continue;
			players.add(Main.mc.theWorld.getPlayerEntityByName(((EntityOtherPlayerMP)player).getDisplayName().replaceAll("\u00A7.(.*)", "$1")));
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
