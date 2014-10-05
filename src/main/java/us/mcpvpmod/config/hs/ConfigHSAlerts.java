package us.mcpvpmod.config.hs;

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

public class ConfigHSAlerts extends DummyModContainer {

    public static String fileName = "mcpvp_hs_alerts.cfg";
    
    private static Configuration config;

    public ConfigHSAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertStreak", Format.s("kit.config.alerts.streak.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.streak");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.streak.get", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStreakEnd", Format.s("kit.config.alerts.streakEnd.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.streakEnd");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.streak.end", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRestart", Format.s("kit.config.alerts.restart.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.restart");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.restart", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
