package us.mcpvpmod.events;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.Sync;

public class Events {

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Main.start();
		Server.getServer().onChat(event);
		Main.end();
	}
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if (Main.mc.isSingleplayer()) return;
		Main.start();
		Server.getServer().onRender(event);
		Main.end();
	}
	
	@SubscribeEvent
	public void onTick(TickEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Server.getServer().onTick(event);
	}
	
	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (Main.mc.isSingleplayer()) return;
		HandleJoin.onJoin(event);
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Server.getServer().onAttack(event);
	}
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Server.getServer().onDeath(event);
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
	    if(event.modID.equals("mcpvp")) {
	    	Sync.sync();
	    }
	}
	
	@SubscribeEvent
	public void onConnect(@SuppressWarnings("unused") ClientConnectedToServerEvent event) {
		ServerHelper.currentIP = "";
	}
	
	@SubscribeEvent
	public void onDisconnect(ClientDisconnectionFromServerEvent event) {
		HandleDisconnect.onDisconnect(event);
	}
	
	@SubscribeEvent
	public void onOpen(GuiOpenEvent event) {
		HandleGui.onOpen(event);
	}
	
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
    	HandleKey.onKey(event);
    }
}
