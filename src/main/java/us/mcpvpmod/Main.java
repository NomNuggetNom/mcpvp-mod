package us.mcpvpmod;

import java.util.Timer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.config.all.ConfigMisc;
import us.mcpvpmod.data.AnchorXML;
import us.mcpvpmod.data.BoxXML;
import us.mcpvpmod.data.Data;
import us.mcpvpmod.events.Events;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.DisplayAnchor;
import us.mcpvpmod.gui.InfoBox;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.screen.GuiSecondChat;
import us.mcpvpmod.json.ServerJSON;
import us.mcpvpmod.json.StreamJSON;
import us.mcpvpmod.json.VersionJSON;
import us.mcpvpmod.timers.PingTimer;
import us.mcpvpmod.timers.SimpleTimer;
import us.mcpvpmod.timers.UpdateTimer;
import us.mcpvpmod.util.Format;
import us.mcpvpmod.version.MCPVPVersion;

@Mod(modid = Main.MOD_ID, name = Main.MOD_NAME, version = Main.MOD_MC_VERSION + "-" + Main.MOD_VERSION, guiFactory = Main.GUI_FACTORY)
public class Main {
	
	/** The ID of the mod. Used in detecting configuration changes and for building the mod. */
	public static final String MOD_ID = "mcpvp";
	/** The name of the mod is displayed on the configuration screen. */
	public static final String MOD_NAME = "MCPVP Mod";
	/** The version of the mod. */
	public static final String MOD_VERSION = "2.0.5";
	/** The version of MC that the mod was compiled for. */
	public static final String MOD_MC_VERSION = "1.8";
	/** The version of Forge that the mod was compiled for. */
	public static final String MOD_FORGE_VERSION = "11.14.0.1274";
	/** A reference to the GuiFactory. Necessary for loading the configuration screen. */
	public static final String GUI_FACTORY = "us.mcpvpmod.config.GuiFactory";
	/** Whether or not this mod is a beta release. */
	public static final boolean IS_BETA = true;
	
	@Instance
	/** Used by Forge to recognize this as a Forge mod. */
	public static Main instance = new Main();
	/** A reference to Minecraft. */
	public static final Minecraft mc = Minecraft.getMinecraft();
	/** A reference to the ServerJSON so it can be called as needed. */
	public static ServerJSON serverJson = new ServerJSON();
	/** The MCPVPVersion used for version checking. Loaded via ServerJSON. */
	public static MCPVPVersion mcpvpVersion = new MCPVPVersion();
	/** The second chat box. TODO: Move somewhere else. */
	public static GuiSecondChat secondChat = new GuiSecondChat(mc);
	/** The key used to open the configuration screen. */
	public static KeyBinding openConfig;
	/** The key used to open the move blocks GUI. */
	public static KeyBinding moveBlocks;
	/** The key used to open the help screen, which is also shown once before. */
	public static KeyBinding showHelp;
	/** The key used to enable drop stacking for Macs. */
	public static KeyBinding cmdMac;
	/** A reference to the on-screen ArmorDisplay. */
	public static ArmorDisplay armorDisplay = new ArmorDisplay();
	/** A reference to the on-screen PotionDisplay. */
	public static PotionDisplay potionDisplay = new PotionDisplay();
	/** A reference to the font renderer, purely for ease of access. */
	public static FontRenderer fr = mc.fontRendererObj;
	
	@EventHandler
	public void preInit(@SuppressWarnings("unused") FMLPreInitializationEvent e) {	
		Main.l(Format.s("startup"));
    	
    	// Register all events in the Events class.
		// This allows all Events to be caught and processed later on.
		MinecraftForge.EVENT_BUS.register(new Events());
		//FMLCommonHandler.instance().bus().register(new Events());
    	
		// Create the directories and files for saving data.
		Data.make();
		BoxXML.make();
		AnchorXML.make();
		
    	// Sync all files and configurations.
		Sync.sync();
		
		// Load stored data.
		InfoBox.loadBoxes();
		DisplayAnchor.loadAnchors();
	}
    	
    @EventHandler
	public void init(@SuppressWarnings("unused") FMLInitializationEvent e) {   	
    	openConfig = new KeyBinding("key.openConfig", Keyboard.KEY_C, "MCPVP");
        ClientRegistry.registerKeyBinding(openConfig);
        moveBlocks = new KeyBinding("key.moveBlocks", Keyboard.KEY_X, "MCPVP");
        ClientRegistry.registerKeyBinding(moveBlocks);
        showHelp = new KeyBinding("key.showHelp", Keyboard.KEY_H, "MCPVP");
        ClientRegistry.registerKeyBinding(showHelp);
        cmdMac = new KeyBinding("key.cmdMac", 29, "MCPVP");
        ClientRegistry.registerKeyBinding(cmdMac);
        
        //DevCapes.getInstance().registerConfig("https://raw.githubusercontent.com/NomNuggetNom/mcpvp-mod/master/capes.json");
	}
    
	@EventHandler
	public void postInit(@SuppressWarnings("unused") FMLPostInitializationEvent e) {

		// Create timers.
		Timer timer = new Timer();
		// TODO: Should be as needed. No reason to have it repeating.
		timer.scheduleAtFixedRate(serverJson, 0, 5*1000L);
		timer.scheduleAtFixedRate(new StreamJSON(), 0, 30*1000L);
		timer.scheduleAtFixedRate(new VersionJSON(), 0, 60*60*1000L);
		timer.scheduleAtFixedRate(new UpdateTimer(), 15*1000, 5*60*1000L);
		timer.scheduleAtFixedRate(new SimpleTimer(), 0, 1*1000L);
		timer.scheduleAtFixedRate(new PingTimer(), 0, ConfigMisc.pingFreq*1000L);
		
		fr = mc.fontRendererObj;
	}
	
	public static void l(Object string, Object... data) {
		FMLLog.info("[MCPVP] " + string, data);
	}
	
	public static void w(Object string, Object... data) {
		FMLLog.warning("[MCPVP] " + string, data);
	}
    
	public static void start(String section) {
		Main.mc.mcProfiler.startSection(section);
	}
	
	public static void start(String... section) {
		for (String string : section) {
			start(string);
		}
	}
	
	public static void start() {
		start("mcpvp");
	}
	
	public static void end() {
		Main.mc.mcProfiler.endSection();
	}
	
	public static void end(int n) {
		for (int i = 0; i < n; i++) {
			end();
		}
	}
	
}
