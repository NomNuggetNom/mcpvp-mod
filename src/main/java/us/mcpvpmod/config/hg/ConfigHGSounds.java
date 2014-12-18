package us.mcpvpmod.config.hg;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.SoundAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigHGSounds extends DummyModContainer {

    public static String soundStart;
    public static String soundDeath;
    public static String soundEnd;
    
    public static String fileName = "mcpvp_hg_sounds.cfg";
    
    private static Configuration config;

    public ConfigHGSounds() {
        config = null;
        File cfgFile = new File(Loader.instance().getConfigDir(), fileName);
        config = new Configuration(cfgFile);

        syncConfig();
    }
    
    public static Configuration getConfig() {
        if (config == null) {
            File cfgFile = new File(Loader.instance().getConfigDir(), fileName);
            config = new Configuration(cfgFile);
        }

        syncConfig();
        return config;
    }
    
    
    public static void syncConfig() {
        if (config == null) {
            File cfgFile = new File(Loader.instance().getConfigDir(), fileName);
            config = new Configuration(cfgFile);
        }
    	
        List<String> propOrder = new ArrayList<String>();
        
        Property prop;
        
    	prop = config.get(CATEGORY_GENERAL, "soundStart", "fireworks.twinkle");
        prop.setLanguageKey("hg.config.sounds.start");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("hg.start", prop.getString(), Server.HG);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundDeath", "mob.skeleton.death");
    	prop.setLanguageKey("hg.config.sounds.death");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("hg.death", prop.getString(), Server.HG);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundEnd", "mob.enderdragon.end");
    	prop.setLanguageKey("hg.config.sounds.end");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("hg.end", prop.getString(), Server.HG);
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
