package us.mcpvpmod.config.raid;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.SoundAlert;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class ConfigRaidSounds extends DummyModContainer {

    public static String fileName = "mcpvp_raid_sounds.cfg";
    
    private static Configuration config;

    public ConfigRaidSounds() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "soundDespoit", "mob.villager.yes");
        prop.setLanguageKey("raid.config.sounds.soundStole");
    	propOrder.add(prop.getName());
    	new SoundAlert("raid.balance", prop.getString(), Server.RAID);

    	prop = config.get(CATEGORY_GENERAL, "soundRaid", "mob.wolf.growl");
        prop.setLanguageKey("raid.config.sounds.soundStreak");
    	propOrder.add(prop.getName());
    	new SoundAlert("raid.raid", prop.getString(), Server.RAID);
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
