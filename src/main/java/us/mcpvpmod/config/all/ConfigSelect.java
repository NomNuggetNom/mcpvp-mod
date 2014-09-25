package us.mcpvpmod.config.all;

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

public class ConfigSelect extends DummyModContainer {

	public static boolean autoTagB;
	public static String autoTag;
    
    public static String fileName = "mcpvp_select.cfg";
    
    private static Configuration config;

    public ConfigSelect() {
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

    	prop = config.get(CATEGORY_GENERAL, "autoTagB", false);
        prop.setLanguageKey("mcpvp.config.Select.autoTagB");
    	autoTagB = prop.getBoolean();
    	propOrder.add(prop.getName());
        
    	prop = config.get(CATEGORY_GENERAL, "autoTag", "none", "This will automatically set your tag when you join.", new String[]{"none", "default", "normal", "vip", "mvp", "pro"});
        prop.setLanguageKey("mcpvp.config.Select.autoTag");
    	autoTag = prop.getString();
    	propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
