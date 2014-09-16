package us.mcpvpmod.game.info;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import us.mcpvpmod.Main;

/**
 * Information that is not dependent on the current server.
 * @author NomNuggetNom
 */
public class AllInfo {

	/**
	 * @return The IP address of the current server.
	 */
	public static String getIP() {
		return Main.mc.func_147104_D().serverIP;
	}
	
	/**
	 * @return The coordinates of the player.
	 */
	public static String getCoords() {
		return (int)Main.mc.thePlayer.posX + ", " + (int)Main.mc.thePlayer.posY + ", " + (int)Main.mc.thePlayer.posZ;
	}
	
	/**
	 * @return The X coordinate of the player.
	 */
	public static int getX() {
		return (int)Main.mc.thePlayer.posX;
	}
	
	/**
	 * @return The Y coordinate of the player.
	 */
	public static int getY() {
		return (int)Main.mc.thePlayer.posY;
	}
	
	/**
	 * @return The Z coordinate of the player.
	 */
	public static int getZ() {
		return (int)Main.mc.thePlayer.posZ;
	}
	
	/**
	 * @return The F direction that the player is facing.
	 */
	public static String getFacing() {
		int i4 = MathHelper.floor_double((double)(Main.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return Direction.directions[i4];
	}
	
	/**
	 * @return The number of players on the current server.
	 */
	public static int getPlayersOnline() {
		return Main.mc.thePlayer.sendQueue.playerInfoList.size();
	}
	
}
