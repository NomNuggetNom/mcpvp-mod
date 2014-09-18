package us.mcpvpmod.game.info;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerData;

/**
 * Information that is not dependent on the current server.
 * @author NomNuggetNom
 */
public class AllInfo {

	/**
	 * @return The IP address of the current server.
	 */
	public static String getIP() {
		if (Main.mc.func_147104_D() == null) return "";
		return Main.mc.func_147104_D().serverIP;
	}
	
	/**
	 * @return The coordinates of the player.
	 */
	public static String getCoords() {
		if (Main.mc.thePlayer == null) return "";
		return (int)Main.mc.thePlayer.posX + ", " + (int)Main.mc.thePlayer.posY + ", " + (int)Main.mc.thePlayer.posZ;
	}
	
	/**
	 * @return The X coordinate of the player.
	 */
	public static int getX() {
		if (Main.mc.thePlayer == null) return -1;
		return (int)Main.mc.thePlayer.posX;
	}
	
	/**
	 * @return The Y coordinate of the player.
	 */
	public static int getY() {
		if (Main.mc.thePlayer == null) return -1;
		return (int)Main.mc.thePlayer.posY;
	}
	
	/**
	 * @return The Z coordinate of the player.
	 */
	public static int getZ() {
		if (Main.mc.thePlayer == null) return -1;
		return (int)Main.mc.thePlayer.posZ;
	}
	
	/**
	 * @return The F direction that the player is facing.
	 */
	public static String getFacing() {
		if (Main.mc.thePlayer == null) return "";
		int i4 = MathHelper.floor_double((double)(Main.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return Direction.directions[i4];
	}
	
	/**
	 * @return The number of players on the current server.
	 */
	public static int getPlayersOnline() {
		if (!ServerData.isMultiplayer()) return -1;
		if (Main.mc.thePlayer == null) return -1;
		return Main.mc.thePlayer.sendQueue.playerInfoList.size();
	}
	
}
