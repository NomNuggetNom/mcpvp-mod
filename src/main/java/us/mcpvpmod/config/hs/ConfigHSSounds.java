package us.mcpvpmod.config.hs;

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

public class ConfigHSSounds extends DummyModContainer {

	public static String soundStreakEnd;
	public static String soundStreak;
    
    public static String fileName = "mcpvp_kit_sounds.cfg";
    
    private static Configuration config;

    public ConfigHSSounds() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "soundStreak", "mob.enderdragon.growl");
        prop.setLanguageKey("kit.config.sounds.soundStole");
    	soundStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("streak.get", prop.getString());

    	prop = config.get(CATEGORY_GENERAL, "soundStreakEnd", "mob.skeleton.death");
        prop.setLanguageKey("kit.config.sounds.soundStreak");
    	soundStreakEnd = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("streak.end", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
