package us.mcpvpmod.config.sab;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigSabAlerts extends DummyModContainer {

    public static String fileName = "mcpvp_sab_alerts.cfg";
    
    private static Configuration config;

    public ConfigSabAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertStart", Format.s("sab.config.alerts.start.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("sab.config.alerts.start");
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertDeath", Format.s("sab.config.alerts.death.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("sab.config.alerts.death");
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.death", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertChest", Format.s("sab.config.alerts.chest.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("sab.config.alerts.chest");
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.chest", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertEnd", Format.s("sab.config.alerts.end.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("sab.config.alerts.end");
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.end", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
