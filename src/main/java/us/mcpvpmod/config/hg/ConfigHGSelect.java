package us.mcpvpmod.config.hg;

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

public class ConfigHGSelect extends DummyModContainer {

	public static String selectMode;
	public static String selectKit;
    
    public static String fileName = "mcpvp_hg_select.cfg";
    
    private static Configuration config;

    public ConfigHGSelect() {
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

        prop = config.get(CATEGORY_GENERAL, "selectMode", "Select Before Start", "Comment", new String[]{"Select On Join", "Select Before Start", "Don't Select"});
        prop.setLanguageKey("hg.config.select.selectMode");
    	selectMode = prop.getString();
    	propOrder.add(prop.getName());
        
    	prop = config.get(CATEGORY_GENERAL, "selectKit", "backup");
        prop.setLanguageKey("hg.config.select.selectKit");
    	selectKit = prop.getString();
    	propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
