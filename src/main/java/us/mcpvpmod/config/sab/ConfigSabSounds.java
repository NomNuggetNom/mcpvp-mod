package us.mcpvpmod.config.sab;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigSabSounds extends DummyModContainer {

    public static String soundStart = "";
    public static String soundDeath = "";
    public static String soundEnd = "";
    
    public static String fileName = "mcpvp_sab_sounds.cfg";
    
    private static Configuration config;

    public ConfigSabSounds() {
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
        prop.setLanguageKey("mcpvp.sab.config.Sounds.start");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("sab.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundDeath", "mob.skeleton.death");
    	prop.setLanguageKey("mcpvp.sab.config.Sounds.death");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("sab.death", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundEnd", "mob.enderdragon.end");
    	prop.setLanguageKey("mcpvp.sab.config.Sounds.end");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("sab.end", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
