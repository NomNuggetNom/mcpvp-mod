package us.mcpvpmod.config.hg;

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

public class ConfigHGAlerts extends DummyModContainer {
	
    public static String fileName = "mcpvp_hg_alerts.cfg";
    
    private static Configuration config;

    public ConfigHGAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertKit", Format.s("hg.config.alerts.kit.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("hg.config.alerts.kit");
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.kit", prop.getString());
        
    	prop = config.get(CATEGORY_GENERAL, "alertStart", Format.s("hg.config.alerts.start.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("hg.config.alerts.start");
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertVulnerable", Format.s("hg.config.alerts.vulnerable.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("hg.config.alerts.vulnerable");
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.vulnerable", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertFeastMini", Format.s("hg.config.alerts.feast.mini.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("hg.config.alerts.feast.mini");
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.feast.mini", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertFeast", Format.s("hg.config.alerts.feast.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("hg.config.alerts.feast");
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.feast", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertFeastBonus", Format.s("hg.config.alerts.feast.bonus.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("hg.config.alerts.feast.bonus");
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.feast.bonus", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
