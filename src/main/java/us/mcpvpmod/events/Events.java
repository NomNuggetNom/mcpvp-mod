package us.mcpvpmod.events;

import java.lang.reflect.InvocationTargetException;

import com.google.common.base.Strings;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.Sync;
import us.mcpvpmod.gui.menu.GuiEvent;
import us.mcpvpmod.gui.menu.GuiMCPVP;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class Events {

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Server.getServer().onChat(event);
	}
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Server.getServer().onRender(event);
	}
	
	@SubscribeEvent
	public void onTick(TickEvent event) {
		if (Main.mc.isSingleplayer()) return;
		Server.getServer().onTick(event);
		Server.getServer().drawOnScreen();
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
	public void onConnect(ClientConnectedToServerEvent event) {
		//HandleConnect.onConnect(event);
	}
	
	@SubscribeEvent
	public void onOpen(GuiOpenEvent event) {
		GuiEvent.onOpen(event);
	}
	
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
    	HandleKey.onKey(event);
    }
}
