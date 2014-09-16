package us.mcpvpmod;

import net.minecraft.client.Minecraft;

public class ServerData {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean isMultiplayer() {
		return (!mc.isSingleplayer() && mc.func_147104_D() != null);
	}
	
	public static String serverIP() {
		if (!isMultiplayer()) return "none";
		return mc.func_147104_D().serverIP;
	}

}
