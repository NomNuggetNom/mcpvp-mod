package us.mcpvpmod;

import java.util.Timer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.MinecraftForge;
import us.mcpvpmod.events.Events;
import us.mcpvpmod.util.ServerJSON;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "mcpvp", name = "MCPVP Mod", version="1.7.10-2.0", guiFactory="us.mcpvpmod.config.GuiFactory")
public class Main {

	@Instance
	public static Main instance = new Main();
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ServerJSON serverJson = new ServerJSON();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		FMLLog.info("[MCPVP] Mod starting up!");
    	
    	// Register all events in the Events class
		MinecraftForge.EVENT_BUS.register(new Events());
		FMLCommonHandler.instance().bus().register(new Events());
    	
    	// Sync all files.
		Sync.sync();
		
		// Create timers.
		Timer timer = new Timer();
		// Update servers every 7 seconds.
		timer.scheduleAtFixedRate(serverJson, 0, 5*1000L);
		
    }
    	
    @EventHandler
	public void init(FMLInitializationEvent e) {
	}
    
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
    
	public static void connectToServer(String ip, GuiScreen gui, Minecraft mc) {
		mc.setServer(ip, 25565);
		mc.setServerData(new ServerData("MCPVP", ip));
		mc.displayGuiScreen(new GuiConnecting(gui, mc, ip, 25565));
	}
	
}
