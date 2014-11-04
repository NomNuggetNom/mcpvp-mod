package us.mcpvpmod;

import java.util.Timer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.config.all.ConfigMisc;
import us.mcpvpmod.events.Events;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.screen.GuiSecondChat;
import us.mcpvpmod.json.ServerJSON;
import us.mcpvpmod.json.StreamJSON;
import us.mcpvpmod.json.VersionJSON;
import us.mcpvpmod.timers.PingTimer;
import us.mcpvpmod.timers.SimpleTimer;
import us.mcpvpmod.timers.UpdateTimer;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;
import us.mcpvpmod.version.MCPVPVersion;

import com.jadarstudios.developercapes.DevCapes;

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
	
	/** The ID of the mod. Used in detecting configuration changes and for building the mod. */
	public static final String modID = "mcpvp";
	/** The name of the mod is displayed on the configuration screen. */
	public static final String name = "MCPVP Mod";
	/** The version of the mod. */
	public static final String modVersion = "2.0.1";
	/** The version of MC that the mod was compiled for. */
	public static final String mcVersion = "1.7.10";
	/** The version of Forge that the mod was compiled for. */
	public static final String forgeVersion = "10.13.0.1180";
	/** A reference to the GuiFactory. Necessary for loading the configuration screen. */
	public static final String guiFactory = "us.mcpvpmod.config.GuiFactory";
	/** Whether or not this mod is a beta release. */
	public static final boolean isBeta = false;
	
	@Instance
	/** Used by Forge to recognize this as a Forge mod. */
	public static Main instance = new Main();
	/** A reference to Minecraft. */
	public static Minecraft mc = Minecraft.getMinecraft();
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
	public static KeyBinding cmdMac;
	/** A reference to the on-screen ArmorDisplay. */
	public static ArmorDisplay armorDisplay = new ArmorDisplay();
	/** A reference to the on-screen PotionDisplay. */
	public static PotionDisplay potionDisplay = new PotionDisplay();
	/** A reference to the InfoBlock that will become the FriendsList during Sync. */
	public static InfoBlock friendsList = null;
	
	@EventHandler
	public void preInit(@SuppressWarnings("unused") FMLPreInitializationEvent e) {
		Main.l(Format.s("startup"));
    	
    	// Register all events in the Events class.
		// This allows all Events to be caught and processed later on.
		MinecraftForge.EVENT_BUS.register(new Events());
		FMLCommonHandler.instance().bus().register(new Events());
    	
		// Create the directories and files for saving data.
		Data.make();
		
    	// Sync all files and configurations.
		Sync.sync();
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
        
        DevCapes.getInstance().registerConfig("https://raw.githubusercontent.com/NomNuggetNom/mcpvp-mod/master/capes.json");
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
