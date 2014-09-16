package us.mcpvpmod.game.stats;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import us.mcpvpmod.Main;
import us.mcpvpmod.util.MCPVPMath;

public class Stats {

	public static String getIP() {
		return Main.mc.func_147104_D().serverIP;
	}
	
	public static int getPing() {
		return (int) Main.mc.func_147104_D().pingToServer;
	}
	
	public static String getCoords() {
		return (int)Main.mc.thePlayer.posX + ", " + (int)Main.mc.thePlayer.posY + ", " + (int)Main.mc.thePlayer.posZ;
	}
	
	public static int getX() {
		return (int)Main.mc.thePlayer.posX;
	}
	
	public static int getY() {
		return (int)Main.mc.thePlayer.posY;
	}
	
	public static int getZ() {
		return (int)Main.mc.thePlayer.posZ;
	}
	
	public static String getFacing() {
		int i4 = MathHelper.floor_double((double)(Main.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return Direction.directions[i4];
	}
	

}
