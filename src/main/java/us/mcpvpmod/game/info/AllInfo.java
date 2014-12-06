package us.mcpvpmod.game.info;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;

/**
 * Information that is not dependent on the current server.
 * @author NomNuggetNom
 */
public class AllInfo {

	/**
	 * @return The IP address of the current server.
	 */
	public static String getIP() {
		return ServerHelper.currentIP;
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
	 * @return The direction the player is facing, e.g. North, South, etc.
	 */
	public static String getDirection() {
		if (Main.mc.thePlayer == null) return "";
		int i4 = MathHelper.floor_double(Main.mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		return Direction.directions[i4];
	}
	
	/**
	 * @return A character based on the direction that the player is facing. N/S/E/W.
	 */
	public static char getDirectionChar() {
		if (Main.mc.thePlayer == null) return '-';
		return getDirection().charAt(0);
	}
	
	/**
	 * @return The F direction the player is facing.
	 */
	public static int getF() {
		if (Main.mc.thePlayer == null) return -1;
		return MathHelper.floor_double(Main.mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	}
	
	/**
	 * @return The F direction the player is facing to an accuracy of one decimal.
	 */
	public static String getFDecimal() {
		if (Main.mc.thePlayer == null) return "-1";
		int firstNum = getF();
		double fullNum = (Main.mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D);
		String newNum = ("" + fullNum).replaceAll(".*\\.", "");
		String newRoundedNum = "" + newNum.charAt(0);
		return firstNum + "." + newRoundedNum;
	}
	
	/**
	 * @return The number of players on the current server.
	 */
	public static int getPlayersOnline() {
		if (!ServerHelper.isMultiplayer()) return -1;
		if (Main.mc.thePlayer == null) return -1;
		return Main.mc.thePlayer.sendQueue.playerInfoList.size();
	}
	
}
