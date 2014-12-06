package us.mcpvpmod.events;

import us.mcpvpmod.ServerHelper;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class HandleDisconnect {
	
	public static void onDisconnect(@SuppressWarnings("unused") ClientDisconnectionFromServerEvent event) {
		ServerHelper.currentIP = "";
	}

}
