package us.mcpvpmod.events;

import us.mcpvpmod.ServerHelper;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class HandleDisconnect {
	
	public static void onDisconnect(@SuppressWarnings("unused") ClientDisconnectionFromServerEvent event) {
		ServerHelper.currentIP = "";
	}

}
