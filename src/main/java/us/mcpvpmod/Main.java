package us.mcpvpmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.events.Events;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.screen.GuiSecondChat;
import us.mcpvpmod.json.ServerJSON;
import us.mcpvpmod.json.StreamJSON;
import us.mcpvpmod.json.TeamsJSON;
import us.mcpvpmod.json.VersionJSON;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;
import us.mcpvpmod.version.MCPVPVersion;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.modID, name = Main.name, version = Main.mcVersion + "-" + Main.modVersion, guiFactory = Main.guiFactory)
public class Main {
	
	public static final String modID = "mcpvp";
	public static final String name = "MCPVP Mod";
	public static final String mcVersion = "1.7.10";
	public static final String modVersion = "2.0";
	public static final String forgeVersion = "10.13.0.1180";
	public static final String guiFactory = "us.mcpvpmod.config.GuiFactory";
	
	@Instance
	public static Main instance = new Main();
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ServerJSON serverJson = new ServerJSON();
	public static GuiSecondChat secondChat = new GuiSecondChat(mc);
	public static MCPVPVersion mcpvpVersion = new MCPVPVersion();
	public static KeyBinding openConfig;
	public static KeyBinding moveBlocks;
	public static KeyBinding showHelp;
	public static ArmorDisplay armorDisplay = new ArmorDisplay();
	public static PotionDisplay potionDisplay = new PotionDisplay();
	public static InfoBlock friendsList = null;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		FMLLog.info(Format.s("startup"));
    	
    	// Register all events in the Events class
		MinecraftForge.EVENT_BUS.register(new Events());
		FMLCommonHandler.instance().bus().register(new Events());
    	
		Data.make();
		
    	// Sync all files.
		Sync.sync();
		
		// Create timers.
		Timer timer = new Timer();
		// TODO: Should be as needed. No reason to have it repeating.
		timer.scheduleAtFixedRate(serverJson, 0, 5*1000L);
		timer.scheduleAtFixedRate(new StreamJSON(), 0, 30*1000L);
		timer.scheduleAtFixedRate(new VersionJSON(), 0, 60*60*1000L);
    }
    	
    @EventHandler
	public void init(FMLInitializationEvent e) {   	
    	openConfig = new KeyBinding("key.openConfig", Keyboard.KEY_C, "MCPVP");
        ClientRegistry.registerKeyBinding(openConfig);
        moveBlocks = new KeyBinding("key.moveBlocks", Keyboard.KEY_X, "MCPVP");
        ClientRegistry.registerKeyBinding(moveBlocks);
        showHelp = new KeyBinding("key.showHelp", Keyboard.KEY_H, "MCPVP");
        ClientRegistry.registerKeyBinding(showHelp);
	}
    
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		TeamsJSON.run();
	}
    
}
