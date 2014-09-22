package us.mcpvpmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ServerData {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * @return If we're on a server.
	 */
	public static boolean isMultiplayer() {
		return (!mc.isSingleplayer() && mc.func_147104_D() != null);
	}
	
	/**
	 * @return The last server IP connected to.
	 */ 
	public static String serverIP() {
		if (!isMultiplayer()) return "none";
		return mc.func_147104_D().serverIP;
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

}
