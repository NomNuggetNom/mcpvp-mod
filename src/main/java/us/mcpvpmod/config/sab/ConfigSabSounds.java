package us.mcpvpmod.config.sab;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.SoundAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigSabSounds extends DummyModContainer {

	public static String soundStreakEnd;
	public static String soundStreak;
    
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
        
        /*
    	prop = config.get(CATEGORY_GENERAL, "soundStreak", "mob.enderdragon.growl");
        prop.setLanguageKey("mcpvp.sab.config.Sounds.soundStole");
    	soundStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("streak.get", prop.getString());

    	prop = config.get(CATEGORY_GENERAL, "soundStreakEnd", "mob.skeleton.death");
        prop.setLanguageKey("mcpvp.sab.config.Sounds.soundStreak");
    	soundStreakEnd = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("streak.end", prop.getString());
    	*/
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
